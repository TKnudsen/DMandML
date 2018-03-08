package com.github.TKnudsen.DMandML.data.cluster;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: ClusterTools
 * </p>
 * 
 * <p>
 * Description: some utility to ease the use of clusters.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public class ClusterTools {

	/**
	 * converts the set of elements into a list. Just for convenient reasons.
	 * 
	 * @param cluster
	 * @return
	 */
	public static <T> List<T> getElementList(Cluster<T> cluster) {
		return new ArrayList<>(cluster.getElements());
	}

	/**
	 * retrieves all elements in a list of clusters.
	 * 
	 * @param clusterResult
	 * @return
	 */
	public static <T, C extends Cluster<T>> List<T> getElementList(List<Cluster<T>> clusters) {
		List<T> elements = new ArrayList<>();

		for (Cluster<T> c : clusters)
			elements.addAll(c.getElements());

		return elements;
	}

	/**
	 * retrieves all centroids for a list of clusters
	 * 
	 * @param clusters
	 * @return
	 */
	public static <T, C extends Cluster<T>> List<Centroid<T>> getCentroids(List<Cluster<T>> clusters) {
		List<Centroid<T>> centroids = new ArrayList<>();

		for (Cluster<T> c : clusters)
			centroids.add(c.getCentroid());

		return centroids;
	}
}
