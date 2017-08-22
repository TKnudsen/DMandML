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

	void foor()
	{
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

	public List<IDObject> getClusteredElements(IClusteringResult<IDObject, Cluster<IDObject>> clusteringResult) {
		List<IDObject> features = new ArrayList<>();

		if (clusteringResult == null)
			return features;

		if (clusteringResult.getClusters() == null)
			return features;

		for (int i = 0; i < clusteringResult.getClusters().size(); i++)
			features.addAll(clusteringResult.getClusters().get(i).getElements());
		return features;
	}

}
