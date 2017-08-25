package main.java.com.github.TKnudsen.DMandML.data;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;

import main.java.com.github.TKnudsen.DMandML.data.features.numerical.NumericalFeatureVectorCluster;
import main.java.com.github.TKnudsen.DMandML.data.features.numerical.NumericalFeatureVectorClusterResult;

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
	public static <T extends IDObject, C extends Cluster<T>> List<T> getElements(ClusteringResult<T, Cluster<T>> clusterResult) {
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
	public static <T extends IDObject, C extends Cluster<T>> C getCluster(ClusteringResult<T, C> clusterResult, T fv, boolean retrieveNearestWhenUnassigned) {
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

}
