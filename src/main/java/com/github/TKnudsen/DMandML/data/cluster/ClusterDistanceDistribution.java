package com.github.TKnudsen.DMandML.data.cluster;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * baseline data structure for modeling centroids.
 * </p>
 *
 * @version 1.03
 * @since 2016
 */
public class ClusterDistanceDistribution<T, C extends ICluster<T>> {

	private Map<C, Double> clusterDistances;

	public ClusterDistanceDistribution(Map<C, Double> clusterDistances) {
		this.clusterDistances = clusterDistances;
	}

	public Map<C, Double> getClusterDistances() {
		return clusterDistances;
	}

	public Double get(C cluster) {
		return clusterDistances.get(cluster);
	}

	public Set<C> keySet() {
		return clusterDistances.keySet();
	}
}
