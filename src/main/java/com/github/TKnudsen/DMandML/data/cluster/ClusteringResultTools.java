package com.github.TKnudsen.DMandML.data.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.data.probability.ProbabilityDistribution;
import com.github.TKnudsen.ComplexDataObject.model.tools.StatisticsSupport;
import com.github.TKnudsen.DMandML.model.distanceMeasure.cluster.ClusterDistanceMeasure;

/**
 * <p>
 * Title: ClusteringResultTools
 * </p>
 * 
 * <p>
 * Description: convenient features for cluster results
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 */
public class ClusteringResultTools {

	// void foor() {
	// NumericalFeatureVectorClusterResult x = null;
	// FeatureVectorCluster<NumericalFeatureVector> largestCluster =
	// getLargestCluster(x);
	// }

	/**
	 * Retrieves the largest cluster from a clustering result.
	 * 
	 * @param <T>           the objects
	 * @param <C>           the clusters
	 * @param clusterResult the target clustering result
	 * @return largest cluster from a clustering result
	 */
	public static <T, C extends ICluster<T>> C getLargestCluster(IClusteringResult<T, C> clusterResult) {
		if (clusterResult == null)
			return null;

		if (clusterResult.size() == 0)
			return null;

		C ret = clusterResult.getClusters().get(0);
		for (int i = 1; i < clusterResult.getClusters().size(); i++)
			if (ret == null || ret.size() < clusterResult.getClusters().get(i).size())
				ret = clusterResult.getClusters().get(i);
		return ret;
	}

	/**
	 * retrieves all elements of all clusters in a clustering result.
	 * 
	 * @param clusteringResult target clustering result
	 * @return all elements of all clusters in a clustering result
	 */
	public static List<IDObject> getClusteredElements(
			IClusteringResult<IDObject, ICluster<IDObject>> clusteringResult) {
		List<IDObject> features = new ArrayList<>();

		if (clusteringResult == null)
			return features;

		if (clusteringResult.getClusters() == null)
			return features;

		for (int i = 0; i < clusteringResult.getClusters().size(); i++)
			features.addAll(clusteringResult.getClusters().get(i).getElements());
		return features;
	}

	/**
	 * retrieves all elements of all clusters in a clustering result.
	 * 
	 * @deprecated use ClusteringResults.getElements instead
	 * @param <T>           the objects
	 * @param <C>           the clusters
	 * @param clusterResult target clustering result
	 * @return elements of all clusters in a clustering result
	 */
	public static <T, C extends ICluster<T>> List<T> getElements(
			IClusteringResult<T, ? extends ICluster<T>> clusterResult) {
		List<T> elements = new ArrayList<>();

		for (ICluster<T> c : clusterResult.getClusters())
			elements.addAll(c.getElements());

		return elements;
	}

	/**
	 * Retrieves the cluster for a given object. The cluster name is used to
	 * retrieve a particular cluster.
	 * 
	 * @param <T>           the objects
	 * @param <C>           the clusters
	 * @param clusterResult target clustering result
	 * @param clusterName   the name of the target cluster
	 * @return the cluster
	 */
	public static <T, C extends ICluster<T>> C getCluster(IClusteringResult<T, C> clusterResult, String clusterName) {
		if (clusterResult == null)
			return null;

		if (clusterName == null)
			throw new IllegalArgumentException("ClusteringResultTools.getCluster: clusterName string was null");

		for (C cluster : clusterResult.getClusters())
			if (cluster.getName() != null && cluster.getName().equals(clusterName))
				return cluster;

		return null;
	}

	/**
	 * retrieves the relative distribution of distances of a given object to the
	 * clusters of a ClusteringResult.
	 * 
	 * @param <T>                      the objects
	 * @param <C>                      the clusters
	 * @param clusteringResult         target clustering result
	 * @param fv                       query object
	 * @param normalizeToProbabilities normalize to probabilities?
	 * @return relative distribution of distances of a given object to the clusters
	 */
	public static <T, C extends ICluster<T>> ClusterDistanceDistribution<T, C> getClusterDistances(
			IClusteringResult<T, C> clusteringResult, T fv, boolean normalizeToProbabilities) {
		if (clusteringResult == null)
			return null;

		Map<C, Double> distanceDistribution = new HashMap<>();

		double distanceSum = 0.0;

		for (C c : clusteringResult) {
			double centroidDistance = c.getCentroidDistance(fv);
			distanceDistribution.put(c, centroidDistance);
			distanceSum += centroidDistance;
		}

		if (!normalizeToProbabilities)
			return new ClusterDistanceDistribution<T, C>(distanceDistribution);

		Map<C, Double> returnDistribution = new HashMap<>();

		double sumOfMultiples = 0.0;
		for (C c : clusteringResult)
			sumOfMultiples += distanceSum / distanceDistribution.get(c);

		for (C c : clusteringResult)
			returnDistribution.put(c, (distanceSum / distanceDistribution.get(c)) / sumOfMultiples);

		return new ClusterDistanceDistribution<T, C>(returnDistribution);
	}

	/**
	 * retrieves the pairwise distances clusters in a clustering result.
	 * 
	 * @param <T>                    the objects
	 * @param <C>                    the clusters
	 * @param clusterResult          target clustering result
	 * @param clusterDistanceMeasure cluster distance measure
	 * @return pairwise distances of clusters in a clustering result
	 */
	public static <T, C extends ICluster<T>> List<Double> getClusterDistanceDistribution(
			IClusteringResult<T, C> clusterResult, ClusterDistanceMeasure<T> clusterDistanceMeasure) {
		if (clusterResult == null)
			return null;

		List<Double> clusterDistances = new ArrayList<>();

		for (int i = 0; i < clusterResult.size() - 1; i++)
			for (int j = i + 1; j < clusterResult.size(); j++) {
				C c1 = clusterResult.getClusters().get(i);
				C c2 = clusterResult.getClusters().get(j);
				clusterDistances.add(clusterDistanceMeasure.getDistance(c1, c2));
			}

		return clusterDistances;
	}

	/**
	 * retrieves the distance of a given instance to the nearest cluster (centroid)
	 * of a {@link IClusteringResult}
	 * 
	 * @param <T>                                         the objects
	 * @param <C>                                         the clusters
	 * @param clusteringResult                            target clustering result
	 * @param fv                                          query feature vector
	 *                                                    object
	 * @param retrieveNearestClusterForUnassignedElements nearest cluster
	 * @return distance
	 */
	public static <T, C extends ICluster<T>> double getDistanceToNearestClusterCentroid(
			IClusteringResult<T, C> clusteringResult, T fv, boolean retrieveNearestClusterForUnassignedElements) {
		if (clusteringResult == null)
			return Double.NaN;

		ICluster<T> cluster = clusteringResult.getCluster(fv);

		if (cluster == null)
			if (retrieveNearestClusterForUnassignedElements)
				cluster = clusteringResult.retrieveCluster(fv);

		double centroidDistance = Double.NaN;
		if (cluster == null)
			System.err.println("ClusteringResults.getDistanceToNearestClusterCentroid: cluster assigned to element ("
					+ fv + ") is null");
		else
			centroidDistance = cluster.getCentroidDistance(fv);

		return centroidDistance;
	}

	/**
	 * creates a probability distribution using distances to clusters. These
	 * distances are, e.g., provided with instances in the environment of the
	 * clustering result.
	 * 
	 * @param <T>                  the objects
	 * @param <C>                  the clusters
	 * @param distanceDistribution distance distribution
	 * @return probability distribution using distances to clusters
	 */
	public static <T, C extends ICluster<T>> ProbabilityDistribution<C> getProbabilityDistributionBasedOnDistanceDistribution(
			ClusterDistanceDistribution<T, C> distanceDistribution) {

		StatisticsSupport statistics = new StatisticsSupport(distanceDistribution.getClusterDistances().values());

		// uses mean cluster distance as threshold
		double maxOrbitDistance = statistics.getMean();

		Map<C, Double> probabilityMap = new HashMap<>();
		double sum = 0;
		for (C c : distanceDistribution.keySet()) {
			double dist = distanceDistribution.get(c);
			if (dist > maxOrbitDistance)
				probabilityMap.put(c, 0.0);
			else
				probabilityMap.put(c, maxOrbitDistance / dist);

			sum += probabilityMap.get(c);
		}

		// normalization to reveal probabilities adding up to 1.0
		if (sum > 0)
			for (C c : probabilityMap.keySet())
				probabilityMap.put(c, probabilityMap.get(c) / sum);
		else
			return null;

		ProbabilityDistribution<C> probabilityDistribution = new ProbabilityDistribution<>(probabilityMap);
		return probabilityDistribution;
	}

	/**
	 * 
	 * @param <T>                  objects
	 * @param <C>                  clusters
	 * @param distanceDistribution distance distribution
	 * @return probability distribution using distances to clusters
	 */
	public static <T, C extends ICluster<T>> ProbabilityDistribution<String> getProbabilityLabelDistributionBasedOnDistanceDistribution(
			ClusterDistanceDistribution<T, C> distanceDistribution) {
		ProbabilityDistribution<C> probabilities = getProbabilityDistributionBasedOnDistanceDistribution(
				distanceDistribution);

		// convert cluster probability distribution...
		// into a (String) label probability distribution
		Map<String, Double> probabilityMap = null;
		ProbabilityDistribution<String> probabilityDistribution = null;
		if (probabilities != null) {
			probabilityMap = new HashMap<>();

			for (C c : probabilities.keySet())
				probabilityMap.put(c.getName(), probabilities.getProbability(c));

			probabilityDistribution = new ProbabilityDistribution<>(probabilityMap);
		}

		return probabilityDistribution;
	}

}
