package com.github.TKnudsen.DMandML.data.cluster.featureVector.numerical;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.data.cluster.featureVector.FeatureVectorClusteringResult;

/**
 * @version 1.02
 * @since 2016
 */
public class NumericalFeatureVectorClusterResult extends FeatureVectorClusteringResult<NumericalFeatureVector> {

	public NumericalFeatureVectorClusterResult(List<NumericalFeatureVectorCluster> clusters) {
		this(clusters, null);
	}

	public NumericalFeatureVectorClusterResult(List<NumericalFeatureVectorCluster> clusters, String name) {
		super(clusters, name);
	}

}
