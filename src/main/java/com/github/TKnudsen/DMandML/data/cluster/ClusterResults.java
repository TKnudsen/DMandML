package com.github.TKnudsen.DMandML.data.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClusterResults {

	/**
	 * Retrieves the distances of a cluster centroid to the other cluster centroids.
	 * 
	 * @param cluster
	 * @param clusterResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Double> getClusterCentroidToOtherCentroidsDistances(C cluster,
			Collection<? extends C> clusterResult) {
		if (cluster == null || clusterResult == null)
			return null;

		List<Double> clusterDistances = new ArrayList<>();

		for (C other : clusterResult)
			if (other != cluster)
				clusterDistances.add(cluster.getDistanceMeasure().getDistance(other.getCentroid().getData(),
						cluster.getCentroid().getData()));

		return clusterDistances;
	}

	/**
	 * Retrieves the distances of a cluster centroid to the other cluster centroids.
	 * 
	 * @param cluster
	 * @param clusterResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Double> getClusterCentroidToOtherCentroidsDistances(C cluster,
			IClusteringResult<T, ICluster<T>> clusterResult) {
		return getClusterCentroidToOtherCentroidsDistances(cluster, clusterResult.getClusters());
	}

	/**
	 * Retrieves the distances of an instance to the centroids of other clusters.
	 * Typically used to assess the separation of clustering results.
	 * 
	 * Presumes that the instance is contained in any of the clusters. If not the
	 * distances to all clusters are returned, instead of c-1.
	 * 
	 * @param instance
	 * @param clusterResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Double> getInstanceToOtherCentroidsDistances(T instance,
			Collection<? extends C> clusterResult) {
		if (instance == null || clusterResult == null)
			return null;

		List<Double> distances = new ArrayList<>();

		for (C other : clusterResult)
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
	 * @param clusterResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Double> getInstanceToOtherCentroidsDistances(T instance,
			IClusteringResult<T, ICluster<T>> clusterResult) {
		return getInstanceToOtherCentroidsDistances(instance, clusterResult.getClusters());
	}

	/**
	 * Retrieves the distances of an instance to all instances of all other
	 * clusters. Typically used to assess the separation of clustering results.
	 * 
	 * Presumes that the instance is contained in any of the clusters. If not the
	 * distances of instances in all clusters are returned, instead of c-1 clusters.
	 * 
	 * @param instance
	 * @param clusterResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Double> getInstanceToOtherClustersInstancesDistances(T instance,
			Collection<? extends C> clusterResult) {
		if (instance == null || clusterResult == null)
			return null;

		List<Double> distances = new ArrayList<>();

		for (C other : clusterResult)
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
	 * @param clusterResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Double> getInstanceToOtherClustersInstancesDistances(T instance,
			IClusteringResult<T, ICluster<T>> clusterResult) {
		return getInstanceToOtherClustersInstancesDistances(instance, clusterResult.getClusters());
	}
}
