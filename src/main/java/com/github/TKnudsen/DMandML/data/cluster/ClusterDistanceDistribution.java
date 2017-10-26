package com.github.TKnudsen.DMandML.data.cluster;

import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;

/**
 * <p>
 * Title: ClusterDistanceDistribution
 * </p>
 * 
 * <p>
 * Description: baseline data structure for modeling centroids.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class ClusterDistanceDistribution<T extends IDObject, C extends Cluster<T>> {

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
}
