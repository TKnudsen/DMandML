package com.github.TKnudsen.DMandML.data.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClusteringResults {

	/**
	 * retrieves all elements of all clusters in a ClusterResult.
	 * 
	 * @param clusterResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<T> getElements(
			IClusteringResult<T, ? extends ICluster<T>> clusterResult) {
		List<T> elements = new ArrayList<>();

		for (ICluster<T> c : clusterResult.getClusters())
			elements.addAll(c.getElements());

		return elements;
	}

	public static <T, C extends ICluster<T>> ICluster<T> retrieveCluster(T instance,
			IClusteringResult<T, ? extends ICluster<T>> clusteringResult) {
		if (instance == null)
			return null;

		if (clusteringResult == null || clusteringResult.size() == 0)
			return null;

		ICluster<T> c = clusteringResult.getCluster(instance);

		if (c != null)
			return c;

		double dist = Double.POSITIVE_INFINITY - 1;
		for (ICluster<T> cluster : clusteringResult.getClusters()) {
			double d = cluster.getCentroidDistance(instance);
			if (d < dist) {
				c = cluster;
				dist = d;
			}
		}

		return c;
	}

	public static <T, C extends ICluster<T>> ICluster<T> retrieveCluster(T instance,
			Collection<? extends ICluster<T>> clusteringResult) {
		if (instance == null)
			return null;

		if (clusteringResult == null || clusteringResult.isEmpty())
			return null;

		for (ICluster<T> cluster : clusteringResult)
			if (cluster.contains(instance))
				return cluster;

		double dist = Double.POSITIVE_INFINITY - 1;
		ICluster<T> c = null;
		for (ICluster<T> cluster : clusteringResult) {
			double d = cluster.getCentroidDistance(instance);
			if (d < dist) {
				c = cluster;
				dist = d;
			}
		}

		return c;
	}

	/**
	 * Retrieves the distances of a cluster centroid to the other cluster centroids.
	 * 
	 * @param cluster
	 * @param clusteringResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Double> getClusterCentroidToOtherCentroidsDistances(C cluster,
			Collection<? extends C> clusteringResult) {
		if (cluster == null || clusteringResult == null)
			return null;

		List<Double> clusterDistances = new ArrayList<>();

		for (C other : clusteringResult)
			if (other == null)
				clusterDistances.add(null);
			else if (other != cluster)
				clusterDistances.add(cluster.getDistanceMeasure().getDistance(other.getCentroid().getData(),
						cluster.getCentroid().getData()));

		return clusterDistances;
	}

	/**
	 * Retrieves the distances of a cluster centroid to the other cluster centroids.
	 * 
	 * @param cluster
	 * @param clusteringResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Double> getClusterCentroidToOtherCentroidsDistances(C cluster,
			IClusteringResult<T, ICluster<T>> clusteringResult) {
		return getClusterCentroidToOtherCentroidsDistances(cluster, clusteringResult.getClusters());
	}

	/**
	 * Retrieves the distances of an instance to the centroids of other clusters.
	 * Typically used to assess the separation of clustering results.
	 * 
	 * Presumes that the instance is contained in any of the clusters. If not the
	 * distances to all clusters are returned, instead of c-1.
	 * 
	 * @param instance
	 * @param clusteringResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Double> getInstanceToOtherCentroidsDistances(T instance,
			Collection<? extends C> clusteringResult) {
		if (instance == null || clusteringResult == null)
			return null;

		List<Double> distances = new ArrayList<>();

		for (C other : clusteringResult)
			if (!other.contains(instance))
				distances.add(other.getDistanceMeasure().getDistance(other.getCentroid().getData(), instance));

		return distances;
	}

	/**
	 * Retrieves the distances of an instance to the centroids of other clusters.
	 * Typically used to assess the separation of clustering results.
	 * 
	 * Presumes that the instance is contained in any of the clusters. If not the
	 * distances to all clusters are returned, instead of c-1.
	 * 
	 * @param instance
	 * @param clusteringResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Double> getInstanceToOtherCentroidsDistances(T instance,
			IClusteringResult<T, ICluster<T>> clusteringResult) {
		return getInstanceToOtherCentroidsDistances(instance, clusteringResult.getClusters());
	}

	/**
	 * Retrieves the distances of an instance to all instances of all other
	 * clusters. Typically used to assess the separation of clustering results.
	 * 
	 * Presumes that the instance is contained in any of the clusters. If not the
	 * distances of instances in all clusters are returned, instead of c-1 clusters.
	 * 
	 * @param instance
	 * @param clusteringResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Double> getInstanceToOtherClustersInstancesDistances(T instance,
			Collection<? extends C> clusteringResult) {
		if (instance == null || clusteringResult == null)
			return null;

		List<Double> distances = new ArrayList<>();

		for (C other : clusteringResult)
			if (!other.contains(instance))
				for (T i : other)
					distances.add(other.getDistanceMeasure().getDistance(i, instance));

		return distances;
	}

	/**
	 * Retrieves the distances of an instance to all instances of all other
	 * clusters. Typically used to assess the separation of clustering results.
	 * 
	 * Presumes that the instance is contained in any of the clusters. If not the
	 * distances of instances in all clusters are returned, instead of c-1 clusters.
	 * 
	 * @param instance
	 * @param clusteringResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Double> getInstanceToOtherClustersInstancesDistances(T instance,
			IClusteringResult<T, ICluster<T>> clusteringResult) {
		return getInstanceToOtherClustersInstancesDistances(instance, clusteringResult.getClusters());
	}

}
