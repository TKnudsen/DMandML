package com.github.TKnudsen.DMandML.data.distanceMatrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrixParallel;
import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.IDistanceMatrix;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;
import com.github.TKnudsen.DMandML.data.cluster.Clusters;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.clustering.ClusterResultWithClusterLookupSupport;
import com.github.TKnudsen.DMandML.model.distanceMeasure.cluster.CentroidDistanceMeasure;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.IClusteringAlgorithm;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.KMeans;

/**
 * Distance matrix that uses aggregation to avoid space allocation problems. The
 * aggregation level can be steered by a parameter, but it is highly recommended
 * to let the {@link AggregationDistanceMatrix} implementation decide.
 * 
 * Important: aggregation reduces the precision of distance computation.
 * Depending on the aggregation level the distance results can be inaccurate by
 * several percent.
 * 
 * While earlier prototypes followed a lazy implementation, the class now
 * calculates the aggregation and downstream distance matrices on the fly.
 * Respective fields are set final.
 * 
 * <p>
 * Copyright: (c) 2017-2020 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.04
 */
public class AggregationDistanceMatrix<T> implements IDistanceMatrix<T> {

	private final List<? extends T> elements;
	private final boolean symmetric;
	private final boolean parallel;

	private final ToDoubleBiFunction<? super T, ? super T> distanceMeasure;

	private final IDistanceMatrix<T> distanceMatrixForSmallSizes;
	private final IDistanceMatrix<ICluster<T>> distanceMatrixForClusters;
	private final Map<ICluster<T>, IDistanceMatrix<T>> clusterDistanceMatrices = new HashMap<ICluster<T>, IDistanceMatrix<T>>();

	private final IClusteringAlgorithm<T> clusteringAlgorithm;

	private final ClusterResultWithClusterLookupSupport<T, Cluster<T>> clusteringResult;

	/**
	 * 
	 * @param elements
	 * @param distanceMeasure
	 * @param dimensionality           needed to guess an appropriate aggregation
	 *                                 level
	 * @param minObjectsForAggregation not recommended to use aggregation for less
	 *                                 than 2000 elements
	 */
	public AggregationDistanceMatrix(List<? extends T> elements,
			ToDoubleBiFunction<? super T, ? super T> distanceMeasure, int dimensionality,
			int minObjectsForAggregation) {
		this(elements, distanceMeasure, true, true, minObjectsForAggregation,
				guessClusterSizeAverage(elements.size(), dimensionality));
	}

	/**
	 * 
	 * @param elements
	 * @param distanceMeasure
	 * @param symmetric
	 * @param parallel
	 * @param minObjectsForAggregation      not recommended to use aggregation for
	 *                                      less than 2000 elements
	 * @param averageElementCountPerCluster not recommended to set this parameter
	 *                                      without knowledge. use
	 *                                      guessClusterSizeAverage() or use
	 *                                      different constructor. Value does not
	 *                                      need to be an integer.
	 */
	public AggregationDistanceMatrix(List<? extends T> elements,
			ToDoubleBiFunction<? super T, ? super T> distanceMeasure, boolean symmetric, boolean parallel,
			int minObjectsForAggregation, double averageElementCountPerCluster) {

		this.elements = elements;
		this.symmetric = symmetric;
		this.parallel = parallel;

		this.distanceMeasure = distanceMeasure;

		if (elements.size() < minObjectsForAggregation || averageElementCountPerCluster <= 1.0) {
			// standard distance matrix procedure
			this.distanceMatrixForSmallSizes = new DistanceMatrixParallel<T>(elements, distanceMeasure);
			this.clusteringAlgorithm = null;
			this.clusteringResult = null;
			this.distanceMatrixForClusters = null;
		} else {
			// aggregation
			this.distanceMatrixForSmallSizes = null;
			int clusterCount = (int) (elements.size() / averageElementCountPerCluster);
			this.clusteringAlgorithm = (IClusteringAlgorithm<T>) new KMeans(clusterCount);
			this.clusteringAlgorithm.setFeatureVectors(getElements());
			this.clusteringAlgorithm.calculateClustering();

			this.clusteringResult = new ClusterResultWithClusterLookupSupport<T, Cluster<T>>(
					this.clusteringAlgorithm.getClusteringResult());

			this.distanceMatrixForClusters = new DistanceMatrixParallel<ICluster<T>>(clusteringResult.getClusters(),
					new CentroidDistanceMeasure<T>(distanceMeasure), true, true);
		}
	}

	public IClusteringAlgorithm<?> getClusteringAlgorithm() {
		return clusteringAlgorithm;
	}

	@Override
	public double applyAsDouble(T t, T u) {
		if (distanceMatrixForSmallSizes != null)
			return distanceMatrixForSmallSizes.applyAsDouble(t, u);
		else {
			ICluster<T> cluster1 = clusteringResult.getCluster(t);
			ICluster<T> cluster2 = clusteringResult.getCluster(u);

			if (cluster1.equals(cluster2)) {
				IDistanceMatrix<T> cdm = getDistanceMatrixForCluster(cluster1);
				return cdm.getDistance(t, u);
			} else
				return distanceMatrixForClusters.applyAsDouble(cluster1, cluster2);
		}
	}

	public int getInternalMatrixSize() {
		if (distanceMatrixForSmallSizes != null)
			return elements.size() * elements.size();

		int size = 0;
		// per cluster (low-level)
		for (ICluster<T> cluster : clusteringResult)
			size += (cluster.size() * cluster.size());
		// dm for all clusters (high-level)
		size += (clusteringResult.size() * clusteringResult.size());

		return size;
	}

	private IDistanceMatrix<T> getDistanceMatrixForCluster(ICluster<T> c) {
		if (!clusterDistanceMatrices.containsKey(c))
			clusterDistanceMatrices.put(c, Clusters.getDistanceMatrix(c));

		return clusterDistanceMatrices.get(c);
	}

	public int getClusterCount() {
		return clusteringResult == null ? 0 : clusteringResult.size();
	}

	@Override
	public double getDistance(T o1, T o2) {
		return applyAsDouble(o1, o2);
	}

	@Override
	public String getName() {
		return AggregationDistanceMatrix.class.getSimpleName();
	}

	@Override
	public String getDescription() {
		return "Can be calculated in a parallel way to speedup computation time";
	}

	@Override
	public double[][] getDistanceMatrix() {
		throw new UnsupportedOperationException(getName() + " is not designed to return the entire matrix.");
	}

	@Override
	public List<? extends T> getElements() {
		return elements;
	}

	@Override
	public boolean isSymmetric() {
		return symmetric;
	}

	public boolean isParallel() {
		return parallel;
	}

	/**
	 * regression model consisting of the two input attributes elements and
	 * dimensions (plus two derived attributes).
	 * 
	 * @param elements
	 * @param dimensionality
	 * @return
	 */
	private static double guessClusterSizeAverage(int elements, int dimensionality) {
		double x1 = dimensionality;
		double x2 = 1 / (double) dimensionality;
		double x3 = elements;
		double x4 = Math.log(elements);

		// linear regression model
		double value = -2.23083467161831 + 0.00074 * x1 + 17.73895 * x2 + 0.0000853 * x3 + 0.320101 * x4;

		// target variable has quadratic behavior, this was considered when building the
		// regression model
		value *= value;

		return Math.max(1, value);
	}
}
