package com.github.TKnudsen.DMandML.data.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrix;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.tools.StatisticsSupport;

/**
 * <p>
 * Description: some utility to ease the use of clusters. Replaces ClusterTools
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2019 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class Clusters {

	/**
	 * converts the set of elements into a list. Just for convenient reasons.
	 * 
	 * @param cluster
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<T> getElementList(ICluster<T> cluster) {
		return new ArrayList<>(cluster.getElements());
	}

	/**
	 * retrieves all elements in a list of clusters.
	 * 
	 * @param clusterResult
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<T> getElementList(List<ICluster<T>> clusters) {
		List<T> elements = new ArrayList<>();

		for (ICluster<T> c : clusters)
			elements.addAll(c.getElements());

		return elements;
	}

	/**
	 * retrieves all centroids for a list of clusters
	 * 
	 * @param clusters
	 * @return
	 */
	public static <T, C extends ICluster<T>> List<Centroid<T>> getCentroids(List<ICluster<T>> clusters) {
		List<Centroid<T>> centroids = new ArrayList<>();

		for (ICluster<T> c : clusters)
			centroids.add(c.getCentroid());

		return centroids;
	}

	/**
	 * clones a cluster (which is not possible with the ICluster interface).
	 * 
	 * @param cluster
	 * @return
	 */
	public static <T, C extends ICluster<T>> ICluster<T> clone(C cluster) {
		if (cluster instanceof Cluster) {
			Cluster<T> c = (Cluster<T>) cluster;
			return c.clone();
		} else
			throw new IllegalArgumentException("ClusterTools: unknown implementation of ICluster interface");
	}

	public static <T, C extends ICluster<T>> double getCentroidDistance(C cluster, T element,
			IDistanceMeasure<T> distanceMeasure) {
		if (distanceMeasure == null)
			throw new NullPointerException("ClusterTools: given distanec measure was null");

		if (cluster == null || element == null)
			return Double.NaN;

		return distanceMeasure.getDistance(cluster.getCentroid().getData(), element);
	}

	/**
	 * calculates/determines a centroid for a given Cluster.
	 * 
	 * @param cluster
	 * @param distanceMeasure
	 * @return
	 */
	public static <T, C extends ICluster<T>> Centroid<T> calculateCentroidLikeElement(C cluster,
			IDistanceMeasure<T> distanceMeasure) {

		DistanceMatrix<T> dm = new DistanceMatrix<>(new ArrayList<>(cluster.getElements()), distanceMeasure);

		Map<T, Double> distances = new HashMap<>();

		for (T t : cluster.getElements())
			distances.put(t, 0.0);

		for (T s : cluster.getElements())
			for (T t : cluster.getElements())
				if (t == s)
					continue;
				else
					distances.put(s, distances.get(s) + dm.applyAsDouble(s, t));

		double min = Double.MAX_VALUE;
		T candidate = null;

		for (T t : distances.keySet())
			if (distances.get(t) < min) {
				min = distances.get(t);
				candidate = t;
			}

		return new Centroid<T>(cluster, candidate);
	}

	public static <T, C extends ICluster<T>> Collection<Double> getCentroidDistances(C cluster) {

		List<Double> values = new ArrayList<>();
		for (T t : cluster)
			values.add(cluster.getCentroidDistance(t));

		return values;
	}

	public static <T, C extends ICluster<T>> Collection<Double> getInstanceDistancesToOtherInstances(T t, C cluster) {

		IDistanceMeasure<T> distanceMeasure = cluster.getDistanceMeasure();

		List<Double> values = new ArrayList<>();
		for (T t1 : cluster)
			if (t1 != t)
				values.add(distanceMeasure.getDistance(t1, t));

		return values;
	}

	/**
	 * collects distances between all instances of a cluster. adds dist(A,B) AND
	 * dist(B,A)
	 * 
	 * @param cluster
	 * @return
	 */
	public static <T, C extends ICluster<T>> Collection<Double> getPairwiseInstanceDistances(C cluster) {

		IDistanceMeasure<T> distanceMeasure = cluster.getDistanceMeasure();

		List<Double> values = new ArrayList<>();
		for (T t1 : cluster)
			for (T t2 : cluster)
				if (t1 != t2)
					values.add(distanceMeasure.getDistance(t1, t2));

		return values;
	}

	/**
	 * calculates the diameter of a cluster, i.e., calculates the maximum of the
	 * pairwise distances using any two elements within the cluster. O(n²)
	 * 
	 * @param cluster
	 * @return
	 */
	public static <T, C extends ICluster<T>> double getDiameter(C cluster) {

		return new StatisticsSupport(getPairwiseInstanceDistances(cluster)).getMax();
	}

	/**
	 * calculates the median of the pairwise distances using any two elements within
	 * the cluster. O(n²)
	 * 
	 * @param cluster
	 * @return
	 */
	public static <T, C extends ICluster<T>> double getMedianPairwiseDistance(C cluster) {

		return new StatisticsSupport(getPairwiseInstanceDistances(cluster)).getMedian();
	}
}