package com.github.TKnudsen.DMandML.data.cluster.featureVector;

/**
 * @version 1.01
 * @since 2016
 */
public class FeatureVectorClusteringResultSupplier<FVCR extends FeatureVectorClusteringResult<?>> implements IFeatureVectorClusteringResultSupplier<FVCR> {

	FVCR featureVectorClusteringResult;

	public FeatureVectorClusteringResultSupplier(FVCR featureVectorClusteringResult) {
		this.featureVectorClusteringResult = featureVectorClusteringResult;
	}

	@Override
	public FVCR get() {
		return featureVectorClusteringResult;
	}

}
