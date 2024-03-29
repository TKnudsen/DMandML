package com.github.TKnudsen.DMandML.data.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrixParallel;
import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.IDistanceMatrix;
import com.github.TKnudsen.ComplexDataObject.data.features.mixedData.MixedDataFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.tools.StatisticsSupport;
import com.github.TKnudsen.DMandML.data.cluster.featureVector.FeatureVectorCluster;
import com.github.TKnudsen.DMandML.data.cluster.general.GeneralCluster;

/**
 * <p>
 * Utility to ease the use of clusters. Replaces ClusterTools and
 * ClusterFactory.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2020 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 */
public class Clusters {

	@SuppressWarnings("unchecked")
	public static <T> Cluster<T> create(List<? extends T> elements, IDistanceMeasure<T> distanceMeasure, String name,
			String description) {

		if (elements == null || elements.size() == 0)
			return null;

		T element = elements.get(0);

		Cluster<T> cluster = null;
		if (element instanceof NumericalFeatureVector) {
			cluster = (Cluster<T>) new FeatureVectorCluster<NumericalFeatureVector>(
					(List<NumericalFeatureVector>) elements, (IDistanceMeasure<NumericalFeatureVector>) distanceMeasure,
					name, description);
		} else if (element instanceof MixedDataFeatureVector) {
			cluster = (Cluster<T>) new FeatureVectorCluster<MixedDataFeatureVector>(
					(List<MixedDataFeatureVector>) elements, (IDistanceMeasure<MixedDataFeatureVector>) distanceMeasure,
					name, description);
		} else {
			cluster = (Cluster<T>) new GeneralCluster<T>(elements, (IDistanceMeasure<T>) distanceMeasure, name,
					description);
		}

		return cluster;
	}

	/**
	 * converts the set of elements into a list. Just for convenient reasons.
	 * 
	 * @param <T>     the elements
	 * @param <C>     the clusters
	 * @param cluster the cluster
	 * @return list of elements
	 */
	public static <T, C extends ICluster<T>> List<T> getElementList(ICluster<T> cluster) {
		return new ArrayList<>(cluster.getElements());
	}

	/**
	 * 
	 * @param <T>      elements
	 * @param <C>      clusters
	 * @param clusters list of clusters
	 * @return all elements in a list of clusters
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
	 * @param <T>      the objects
	 * @param <C>      the clusters
	 * @param clusters clusters
	 * @return centroids
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
	 * @param <T>     the objects
	 * @param <C>     the clusters
	 * @param cluster target cluster
	 * @return clone
	 */
	public static <T, C extends ICluster<T>> ICluster<T> clone(C cluster) {
		if (cluster instanceof Cluster) {
			Cluster<T> c = (Cluster<T>) cluster;
			return c.clone();
		} else
			throw new IllegalArgumentException("ClusterTools: unknown implementation of ICluster interface");
	}

	/**
	 * 
	 * @param <T>             the objects
	 * @param <C>             the clusters
	 * @param cluster         target cluster
	 * @param element         query object
	 * @param distanceMeasure distance measure
	 * @return distance of an element to the cluster centroid
	 */
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
	 * @param <T>             the objects
	 * @param <C>             the clusters
	 * @param cluster         cluster
	 * @param distanceMeasure distance measure
	 * @return the centroid
	 */
	public static <T, C extends ICluster<T>> Centroid<T> calculateCentroidLikeElement(C cluster,
			IDistanceMeasure<T> distanceMeasure) {

		IDistanceMatrix<T> dm = new DistanceMatrixParallel<>(new ArrayList<>(cluster.getElements()), distanceMeasure);

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

	/**
	 * 
	 * @param <T>     the objects
	 * @param <C>     the clusters
	 * @param cluster target cluster
	 * @return distances
	 */
	public static <T, C extends ICluster<T>> Collection<Double> getCentroidDistances(C cluster) {

		List<Double> values = new ArrayList<>();
		for (T t : cluster)
			values.add(cluster.getCentroidDistance(t));

		return values;
	}

	/**
	 * 
	 * @param <T>     the objects
	 * @param <C>     the clusters
	 * @param t       query object
	 * @param cluster target cluster
	 * @return distances
	 */
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
	 * @param <T>     the objects
	 * @param <C>     the clusters
	 * @param cluster target cluster
	 * @return pairwise distances
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
	 * distance matrix storing the pairwise distances of instances.
	 * 
	 * @param <T>     the objects
	 * @param <C>     the clusters
	 * @param cluster target cluster
	 * @return distance matrix
	 */
	public static <T, C extends ICluster<T>> IDistanceMatrix<T> getDistanceMatrix(C cluster) {

		return new DistanceMatrixParallel<T>(Clusters.getElementList(cluster), cluster.getDistanceMeasure(), true,
				true);
	}

	/**
	 * calculates the diameter of a cluster, i.e., calculates the maximum of the
	 * pairwise distances using any two elements within the cluster. O(n²)
	 * 
	 * @param <T>     the objects
	 * @param <C>     the clusters
	 * @param cluster target cluster
	 * @return diameter
	 */
	public static <T, C extends ICluster<T>> double getDiameter(C cluster) {

		return new StatisticsSupport(getPairwiseInstanceDistances(cluster)).getMax();
	}

	/**
	 * calculates the median of the pairwise distances using any two elements within
	 * the cluster. O(n²)
	 * 
	 * @param <T>     the objects
	 * @param <C>     the clusters
	 * @param cluster the input cluster
	 * @return the median pairwise distance
	 */
	public static <T, C extends ICluster<T>> double getMedianPairwiseDistance(C cluster) {

		return new StatisticsSupport(getPairwiseInstanceDistances(cluster)).getMedian();
	}
}