package com.github.TKnudsen.DMandML.model.unsupervised.clustering;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.data.cluster.featureVector.numerical.NumericalFeatureVectorClusterResult;

/**
 * @version 1.03
 * @since 2018
 */
public interface INumericalClusteringAlgorithm extends IClusteringAlgorithm<NumericalFeatureVector> {

	@Override
	public NumericalFeatureVectorClusterResult getClusteringResult();
}