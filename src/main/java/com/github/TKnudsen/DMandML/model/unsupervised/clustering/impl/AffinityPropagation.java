package com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;
import com.github.TKnudsen.DMandML.data.cluster.ClusteringResult;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.data.cluster.featureVector.numerical.NumericalFeatureVectorCluster;
import com.github.TKnudsen.DMandML.model.tools.ELKITools;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.IClusteringAlgorithm;

import de.lmu.ifi.dbs.elki.algorithm.clustering.affinitypropagation.AffinityPropagationClusteringAlgorithm;
import de.lmu.ifi.dbs.elki.algorithm.clustering.affinitypropagation.AffinityPropagationInitialization;
import de.lmu.ifi.dbs.elki.algorithm.clustering.affinitypropagation.DistanceBasedInitializationWithMedian;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.MedoidModel;
import de.lmu.ifi.dbs.elki.data.type.TypeUtil;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.database.ids.DBIDIter;
import de.lmu.ifi.dbs.elki.database.ids.DBIDs;
import de.lmu.ifi.dbs.elki.database.relation.Relation;
import de.lmu.ifi.dbs.elki.distance.distancefunction.DistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.EuclideanDistanceFunction;

/**
 * <p>
 * Title: AffinityPropagation
 * </p>
 * 
 * <p>
 * Description: Implementation is based on ELKI.
 * 
 * http://weka.sourceforge.net/doc.dev/weka/clusterers/Canopy.html:
 * 
 * For more information see: Clustering by passing messages between data.
 * Brendan J. Frey and Delbert Dueck. Science, 2007.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class AffinityPropagation implements IClusteringAlgorithm<NumericalFeatureVector> {

	private AffinityPropagationClusteringAlgorithm<NumberVector> apAlgorithm;

	/**
	 * Quantile to use for the distance-based initialization of the
	 * AP-initialization. [0....1]
	 */
	double quantile = 0.5;

	/**
	 * Damping factor lambda.
	 */
	double lambda = 0.5;

	/**
	 * Terminate after 10 iterations with no changes.
	 */
	int convergence = 10;

	/**
	 * Maximum number of iterations.
	 */
	int maxiter = 1000;

	/**
	 * this is how ELKI handles feature vectors.
	 */
	private Database db;

	/**
	 * redundant data structure to store the input FVs for the clustering
	 */
	private List<NumericalFeatureVector> featureVectors;

	/**
	 * allows linking back to given feature vectors.
	 */
	private Map<NumberVector, NumericalFeatureVector> lookupTable;

	/**
	 * ELKI clustering result
	 */
	private Clustering<MedoidModel> clusteringResult;

	/**
	 * the clustering result mapped back to the feature vectors
	 */
	private IClusteringResult<NumericalFeatureVector, ? extends Cluster<NumericalFeatureVector>> clusteringResultFVs;

	public AffinityPropagation() {
		this(null, 0.5, 0.5, 10, 1000);
	}

	public AffinityPropagation(List<NumericalFeatureVector> featureVectors, double quantile, double lambda,
			int convergence, int maxiter) {
		setFeatureVectors(featureVectors);

		if (quantile < 0 || quantile > 1.0)
			throw new IllegalArgumentException(getName() + ": quantile has to be between [0 and 1]");

		this.quantile = quantile;
		this.lambda = lambda;
		this.convergence = convergence;
		this.maxiter = maxiter;
	}

	@Override
	public IDistanceMeasure<NumericalFeatureVector> getDistanceMeasure() {
		return new EuclideanDistanceMeasure();
	}

	@Override
	public List<NumericalFeatureVector> getFeatureVectors() {
		return new ArrayList<>(featureVectors);
	}

	@Override
	public void setFeatureVectors(List<NumericalFeatureVector> featureVectors) {
		this.featureVectors = new ArrayList<>(featureVectors);

		this.clusteringResult = null;
		this.clusteringResultFVs = null;

		db = null;
		if (featureVectors == null)
			return;

		db = ELKITools.createAndInitializeELKIDatabase(featureVectors);

		// create lookup table
		lookupTable = new HashMap<>();

		Relation<NumberVector> rel = db.getRelation(TypeUtil.NUMBER_VECTOR_FIELD);

		int i = 0;
		for (DBIDIter it = rel.getDBIDs().iter(); it.valid(); it.advance()) {
			NumberVector v = rel.get(it);

			// TODO this is dirty! it assumes that the order is always preserved.
			lookupTable.put(v, getFeatureVectors().get(i++));
		}
	}

	@Override
	public void calculateClustering() {
		this.clusteringResult = null;
		this.clusteringResultFVs = null;

		DistanceFunction<NumberVector> distanceFunction = new EuclideanDistanceFunction();

		AffinityPropagationInitialization<NumberVector> initialization = new DistanceBasedInitializationWithMedian<>(
				distanceFunction, quantile);

		apAlgorithm = new AffinityPropagationClusteringAlgorithm<>(initialization, lambda, convergence, maxiter);

		clusteringResult = apAlgorithm.run(db);
	}

	@Override
	public IClusteringResult<NumericalFeatureVector, ? extends Cluster<NumericalFeatureVector>> getClusteringResult() {
		if (clusteringResult == null)
			throw new NullPointerException(
					"AffinitiyPropagationInitialization: clustering result null. calculateClustering() first.");

		if (clusteringResultFVs != null)
			return clusteringResultFVs;

		Relation<NumberVector> rel = db.getRelation(TypeUtil.NUMBER_VECTOR_FIELD);

		List<Cluster<NumericalFeatureVector>> fvCLusters = new ArrayList<>();
		int c = 1;

		for (de.lmu.ifi.dbs.elki.data.Cluster<MedoidModel> elkiCluster : clusteringResult.getAllClusters()) {
			DBIDs iDs = elkiCluster.getIDs();

			List<NumericalFeatureVector> fvs = new ArrayList<>();
			for (DBIDIter it = iDs.iter(); it.valid(); it.advance()) {
				NumberVector v = rel.get(it);

				NumericalFeatureVector fv = lookupTable.get(v);

				if (fv == null)
					throw new NullPointerException(getName() + ".getClusteringResult(): feature vector lookup failed.");

				fvs.add(fv);
			}

			fvCLusters.add(new NumericalFeatureVectorCluster(fvs, getName() + " cluster " + c++));
		}

		clusteringResultFVs = new ClusteringResult<>(fvCLusters);

		return clusteringResultFVs;
	}

	@Override
	public String getName() {
		return "Affinity Propagation";
	}

}
