package com.github.TKnudsen.DMandML.data.cluster.numerical;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.tools.MathFunctions;
import com.github.TKnudsen.ComplexDataObject.model.tools.StatisticsSupport;
import com.github.TKnudsen.DMandML.data.cluster.Centroid;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;

/**
 * <p>
 * Title: NumericalFeatureVectorCluster
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class NumericalFeatureVectorCluster extends Cluster<NumericalFeatureVector> {

	private StatisticsSupport distancesToCentroidStatistics;
	private StatisticsSupport[] dataStatisticsPerDimension = null;

	private int dimensionality = -1;

	private ConcurrentHashMap<Long, Double> featureVectorCentroidDistancesLong = new ConcurrentHashMap<Long, Double>();
	private ConcurrentHashMap<NumericalFeatureVector, Double> featureVectorCentroidDistancesFV = new ConcurrentHashMap<>();
	private Map<Long, Float> featureVectorCentroidDistancesRelative = new HashMap<>();
	private TreeSet<EntryWithComparableKey<Double, NumericalFeatureVector>> featureVectorRankingTreeSet = null;

	/**
	 * Creates a new Cluster based on FeatureVectors.
	 * 
	 * @param FeatureVectors
	 * @param centroid
	 *            note: if null, a new centroid is calculated
	 * @param name
	 */
	public NumericalFeatureVectorCluster(Collection<? extends NumericalFeatureVector> featureVectors, String name) {
		super(featureVectors, new EuclideanDistanceMeasure(), name, "");

		this.ID = MathFunctions.randomLong();

		initialize();
	}

	public NumericalFeatureVectorCluster(Collection<? extends NumericalFeatureVector> featureVectors, IDistanceMeasure<NumericalFeatureVector> distanceMeasure, String name, String description) {
		super(featureVectors, distanceMeasure, name, description);

		this.ID = MathFunctions.randomLong();

		initialize();
	}

	/**
	 * I don't like this constructor. But FeatureVectorCluster and Cluster
	 * <FeatureVector> are very different things.
	 * 
	 * @param cluster
	 */
	public NumericalFeatureVectorCluster(Cluster<NumericalFeatureVector> cluster) {
		super(cluster.getElements(), cluster.getDistanceMeasure(), cluster.getName(), cluster.getDescription());

		this.ID = cluster.getID();

		initialize();
	}

	private void initialize() {
		resetCentroidDistances();

		this.featureVectorRankingTreeSet = null;
		this.featureVectorCentroidDistancesLong = new ConcurrentHashMap<>();
		this.featureVectorCentroidDistancesFV = new ConcurrentHashMap<>();

		// StatisticsSupport
		dataStatisticsPerDimension = null;

		for (NumericalFeatureVector fv : getElements())
			fv.add("ClusterIndex", ID);

		hash = -1;
	}

	@Override
	protected void calculateCentroid() {
		this.centroid = NumericalFeatureVectorClusterTools.calculateCentroid(this);
		resetCentroidDistances();
	}

	private void calculateDataStatisticsPerDimension() {

		dataStatisticsPerDimension = new StatisticsSupport[getDimensionality()];
		for (int dim = 0; dim < getDimensionality(); dim++) {
			double[] vector = new double[size()];
			int i = 0;
			for (NumericalFeatureVector fv : getElements())
				vector[i++] = fv.getVector()[dim];
			dataStatisticsPerDimension[dim] = new StatisticsSupport(vector);
		}
	}

	public void setCentroid(Centroid<NumericalFeatureVector> centroid) {
		this.centroid = centroid;
		hash = -1;
		resetCentroidDistances();
	}

	public Centroid<NumericalFeatureVector> getCentroid() {
		if (size() == 0)
			return null;
		if (this.centroid == null && this.centroid.getData().getVector() == null || this.centroid.getData().getVector().length == 0)
			calculateCentroid();
		return centroid;
	}

	/**
	 * Adds a data point. open question. should the classID of the FV also be
	 * set?!
	 * 
	 * @param feature
	 */
	public void addFeatureVector(NumericalFeatureVector feature) {
		feature.add("ClusterIndex", ID);

		super.add(feature);

		featureVectorRankingTreeSet = null;
		dataStatisticsPerDimension = null;
		distancesToCentroidStatistics = null;
	}

	public void removeFeatureVector(NumericalFeatureVector feature) {
		feature.removeAttribute("ClusterIndex");

		super.remove(feature);

		this.featureVectorRankingTreeSet = null;
		if (this.featureVectorCentroidDistancesLong != null && this.featureVectorCentroidDistancesLong.get(feature.getID()) != null)
			this.featureVectorCentroidDistancesLong.remove(feature.getID());
		if (this.featureVectorCentroidDistancesFV != null && this.featureVectorCentroidDistancesFV.get(feature) != null)
			this.featureVectorCentroidDistancesFV.remove(feature);

		dataStatisticsPerDimension = null;
		distancesToCentroidStatistics = null;
	}

	@Override
	public boolean remove(NumericalFeatureVector element) {
		element.removeAttribute("ClusterIndex");

		boolean modified = super.remove(element);

		if (modified) {
			this.centroid = null;

			resetCentroidDistances();
		}
		return modified;
	}

	/**
	 * calculates the distance of a given FeatureVector to the cluster centroid
	 * 
	 * @param fv
	 * @return
	 */
	public double getCentroidDistance(NumericalFeatureVector fv) {
		if (this.featureVectorCentroidDistancesLong == null)
			this.featureVectorCentroidDistancesLong = new ConcurrentHashMap<>();
		if (this.featureVectorCentroidDistancesFV == null)
			featureVectorCentroidDistancesFV = new ConcurrentHashMap<>();

		if (this.featureVectorCentroidDistancesLong.get(fv.getID()) == null || featureVectorCentroidDistancesFV.get(fv) == null) {
			double d = getDistanceMeasure().getDistance(fv, this.centroid.getData());
			this.featureVectorCentroidDistancesLong.put(fv.getID(), d);
			this.featureVectorCentroidDistancesFV.put(fv, d);
		}
		try {
			if (this.featureVectorCentroidDistancesLong == null || this.featureVectorCentroidDistancesLong.get(fv.getID()) == null)
				return getDistanceMeasure().getDistance(fv, this.centroid.getData());
			else
				return this.featureVectorCentroidDistancesLong.get(fv.getID());
		} catch (Exception e) {
			return Double.NaN;
		}
	}

	public void calcStats() {
		if (this.getElements() == null)
			return;

		if (size() == 0)
			return;

		this.featureVectorCentroidDistancesLong = new ConcurrentHashMap<>();
		this.featureVectorCentroidDistancesFV = new ConcurrentHashMap<>();

		distancesToCentroidStatistics = NumericalFeatureVectorClusterTools.getStatistics(this);
	}

	public void calculateFeatureVectorRankingTreeSet() {
		featureVectorRankingTreeSet = new TreeSet<>();

		if (this.getElements() == null)
			return;

		if (size() == 0)
			return;

		for (NumericalFeatureVector f : getElements())
			try {
				featureVectorRankingTreeSet.add(new EntryWithComparableKey<Double, NumericalFeatureVector>(getCentroidDistance(f), f));
			} catch (NullPointerException np) {
				np.printStackTrace();
				System.out.println("hmmh!");
				featureVectorRankingTreeSet.add(new EntryWithComparableKey<Double, NumericalFeatureVector>(getCentroidDistance(f), f));
			}
	}

	protected void resetCentroidDistances() {
		this.featureVectorRankingTreeSet = new TreeSet<>();
		this.featureVectorCentroidDistancesLong = new ConcurrentHashMap<>();
		this.featureVectorCentroidDistancesFV = new ConcurrentHashMap<>();
		this.featureVectorCentroidDistancesRelative = new ConcurrentHashMap<>();

		this.distancesToCentroidStatistics = null;
	}

	public double getSumSqr() {
		if (distancesToCentroidStatistics == null)
			calcStats();
		return this.distancesToCentroidStatistics.getSumsq();
	}

	public double getAverageSqr() {
		if (distancesToCentroidStatistics == null)
			calcStats();
		return this.distancesToCentroidStatistics.getMean();
	}

	@Override
	public double getVariance() {
		if (distancesToCentroidStatistics == null)
			calcStats();
		return distancesToCentroidStatistics.getVariance();
	}

	public NumericalFeatureVector asFeatureVector(String name) {
		if (centroid == null)
			return null;
		if (centroid.getData().getVector() == null)
			return null;

		// NumericalFeatureVector fv = new NumericalFeatureVector(null, name,
		// centroid.getData().getVector().clone());
		return centroid.getData();
	}

	/**
	 * The creation of the two HashMaps (featureVectorTransparency and
	 * featureVectorColoring) was re-arranged close to the fv-loop. due to some
	 * incident the setColor()-Method is called in the meantime which nulls
	 * featureVectorColoring.
	 */
	private void refreshFeatureVectorDistances() {

		calculateFeatureVectorRankingTreeSet();

		double minDist = Double.POSITIVE_INFINITY;
		double maxDist = Double.NEGATIVE_INFINITY;
		try {
			if (featureVectorRankingTreeSet != null && featureVectorRankingTreeSet.size() > 0 && featureVectorRankingTreeSet.first() != null && featureVectorRankingTreeSet.first().getKey() != null && featureVectorRankingTreeSet.last() != null
					&& featureVectorRankingTreeSet.last().getKey() != null) {
				minDist = featureVectorRankingTreeSet.first().getKey();
				maxDist = featureVectorRankingTreeSet.last().getKey();
			} else {
				for (NumericalFeatureVector fv : getElements()) {
					minDist = Math.min(minDist, getCentroidDistance(fv));
					maxDist = Math.max(maxDist, getCentroidDistance(fv));
				}
			}
		} catch (Exception e) {
			System.out.println("SFE");
			e.printStackTrace();
		}

		try {
			this.featureVectorCentroidDistancesRelative = new HashMap<>();
			for (NumericalFeatureVector fv : getElements()) {
				float alpha = 1.0f;
				if (minDist < maxDist)
					alpha = (float) ((maxDist - getCentroidDistance(fv)) / (maxDist - minDist));
				this.featureVectorCentroidDistancesRelative.put(fv.getID(), alpha);
			}
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		} catch (NoSuchElementException nsee) {
			nsee.printStackTrace();
		}
	}

	public TreeSet<EntryWithComparableKey<Double, NumericalFeatureVector>> getFeatureVectorRankingTreeSet() {
		try {
			if (featureVectorRankingTreeSet == null || featureVectorRankingTreeSet.size() != getElements().size())
				refreshFeatureVectorDistances();
			if (featureVectorRankingTreeSet == null)
				System.out.println("what?=!");
			return featureVectorRankingTreeSet;
		} catch (ConcurrentModificationException e) {
			System.out.println();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (featureVectorRankingTreeSet == null || featureVectorRankingTreeSet.size() != getElements().size())
				refreshFeatureVectorDistances();
			if (featureVectorRankingTreeSet == null)
				System.out.println("what?=!");
			return featureVectorRankingTreeSet;
		}
	}

	public StatisticsSupport[] getDataStatisticsPerDimension() {
		if (dataStatisticsPerDimension == null)
			calculateDataStatisticsPerDimension();
		return dataStatisticsPerDimension;
	}

	public boolean contains(NumericalFeatureVector fv) {
		return getElements().contains(fv);
	}

	@Override
	public String toString() {
		return "Cluster " + getName() + ". Centroid: " + centroid.toString();
	}

	@Override
	public String getDescription() {
		return "Cluster " + getName() + ". Centroid: " + centroid.toString();
	}

	public Map<Long, Double> getFeatureVectorCentroidDistances() {
		return featureVectorCentroidDistancesLong;
	}

	public Map<NumericalFeatureVector, Double> getFeatureVectorCentroidDistancesMap() {
		// stellt sicher, das alle fv in der map sind
		elements.stream().filter(fv -> !featureVectorCentroidDistancesFV.containsKey(fv)).forEach(fv -> getCentroidDistance(fv));
		return featureVectorCentroidDistancesFV;
	}

	public Map<Long, Float> getFeatureVectorCentroidDistancesRelative() {
		return featureVectorCentroidDistancesRelative;
	}

	@Override
	public NumericalFeatureVectorCluster clone() {
		return new NumericalFeatureVectorCluster(getElements(), getName());
	}

	public int getDimensionality() {
		if (dimensionality == -1 || dimensionality == 0) {
			for (Iterator<NumericalFeatureVector> iterator = getElements().iterator(); iterator.hasNext();) {
				NumericalFeatureVector featureVector = iterator.next();
				dimensionality = Math.max(dimensionality, featureVector.getSize());
			}
		}
		return dimensionality;
	}

}
