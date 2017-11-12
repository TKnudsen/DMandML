package com.github.TKnudsen.DMandML.model.unsupervised.clustering.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.tools.WekaConversion;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;
import com.github.TKnudsen.DMandML.data.cluster.ClusteringResult;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.data.cluster.featureFV.numerical.NumericalFeatureVectorCluster;

import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * Title: WekaClusteringTools
 * </p>
 * 
 * <p>
 * Description: little helpers supporting the clustering with WEKA and other
 * WEKA-related capability.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.07
 */
public class WekaClusteringTools {
	public static IClusteringResult<NumericalFeatureVector, Cluster<NumericalFeatureVector>> getClusterResultFromWekaClusterer(weka.clusterers.AbstractClusterer clusterer, Instances data, List<NumericalFeatureVector> featureVectors,
			String clusteringName) {

		try {
			if (data == null && featureVectors != null) {
				data = WekaConversion.getInstances(featureVectors, false);
			}
			clusterer.buildClusterer(data);

			HashMap<Integer, List<NumericalFeatureVector>> fvs = new HashMap<Integer, List<NumericalFeatureVector>>();
			for (int i = 0; i < data.numInstances(); i++) {
				Instance inst = data.instance(i);
				int c;

				try {
					c = clusterer.clusterInstance(inst);

					if (!fvs.containsKey(c))
						fvs.put(c, new ArrayList<NumericalFeatureVector>());
					fvs.get(c).add((NumericalFeatureVector) featureVectors.get(i));
				} catch (Exception e) {
					System.out.println("");
				}
			}
			List<Cluster<NumericalFeatureVector>> clusters = new ArrayList<>();
			for (Integer i : fvs.keySet())
				clusters.add(new NumericalFeatureVectorCluster(fvs.get(i), clusteringName + " Cluster " + i));

			return new ClusteringResult<NumericalFeatureVector, Cluster<NumericalFeatureVector>>(clusters);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
