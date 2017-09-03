package com.github.TKnudsen.DMandML.data.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.DMandML.data.cluster.numerical.NumericalFeatureVectorCluster;
import com.github.TKnudsen.DMandML.data.cluster.numerical.NumericalFeatureVectorClusterResult;

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
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class ClusteringResultTools {

	void foor() {
		NumericalFeatureVectorClusterResult x = null;
		NumericalFeatureVectorCluster y = getLargestCluster(x);
	}

	/**
	 * Retrieves the largest cluster from a ClusterResult.
	 * 
	 * @param clusterResult
	 * @return
	 */
	public static <T extends IDObject, C extends Cluster<T>> C getLargestCluster(IClusteringResult<T, C> clusterResult) {
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
	 * retrieves all elements of all clusters in a ClusterResult.
	 * 
	 * @param clusterResult
	 * @return
	 */
	public static List<IDObject> getClusteredElements(IClusteringResult<IDObject, Cluster<IDObject>> clusteringResult) {
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
	 * retrieves all elements of all clusters in a ClusterResult.
	 * 
	 * @param clusterResult
	 * @return
	 */
	public static <T extends IDObject, C extends Cluster<T>> List<T> getElements(IClusteringResult<T, Cluster<T>> clusterResult) {
		List<T> elements = new ArrayList<>();

		for (Cluster<T> c : clusterResult.getClusters())
			elements.addAll(c.getElements());

		return elements;
	}

	/**
	 * Retrieves the cluster for a given object. If getClusterMapping is null
	 * (if object was not part of the clustering routine) AND
	 * retrievNearestWhenUnassigned is set true, the method retrieves the
	 * nearest cluster for the object.
	 * 
	 * @param fv
	 * @param retrieveNearestWhenUnassigned
	 *            if a cluster is retrieved in case no assignment is exists.
	 * @return
	 */
	public static <T extends IDObject, C extends Cluster<T>> C getCluster(IClusteringResult<T, C> clusterResult, T fv, boolean retrieveNearestWhenUnassigned) {
		if (fv == null)
			return null;

		C c = clusterResult.getCluster(fv);

		if (c != null)
			return c;

		if (!retrieveNearestWhenUnassigned)
			return null;

		double dist = Double.POSITIVE_INFINITY - 1;
		for (C cluster : clusterResult.getClusters()) {
			double d = cluster.getCentroidDistance(fv);
			if (d < dist) {
				c = cluster;
				dist = d;
			}
		}
		return c;
	}

	/**
	 * Retrieves the cluster for a given object. The cluster name is used to
	 * retrieve a particular cluster.
	 * 
	 * @param clusterResult
	 * @param fv
	 * @param clusterName
	 * @return
	 */
	public static <T extends IDObject, C extends Cluster<T>> C getCluster(IClusteringResult<T, C> clusterResult, String clusterName) {
		if (clusterResult == null)
			return null;

		if (clusterName == null)
			throw new IllegalArgumentException("ClusteringResultTools.getCluster: clusterName string was null");

		for (C cluster : clusterResult.getClusters())
			if (cluster.getName() != null && cluster.getName().equals(clusterResult))
				return cluster;

		return null;
	}

	/**
	 * retrieves the relative distribution of distances of a given object to the
	 * clusters of a ClusteringResult.
	 * 
	 * @param clusterResult
	 * @param fv
	 * @return
	 */
	public static <T extends IDObject, C extends Cluster<T>> Map<C, Double> getClusterDistances(IClusteringResult<T, C> clusterResult, T fv) {
		if (clusterResult == null)
			return null;

		Map<C, Double> distanceDistribution = new HashMap<>();

		double distanceSum = 0.0;

		for (C c : clusterResult) {
			double centroidDistance = c.getCentroidDistance(fv);
			distanceDistribution.put(c, centroidDistance);
			distanceSum += centroidDistance;
		}

		Map<C, Double> returnDistribution = new HashMap<>();
		for (C c : clusterResult)
			returnDistribution.put(c, distanceDistribution.get(c) / distanceSum);

		return returnDistribution;
	}

}
