package com.github.TKnudsen.DMandML.model.unsupervised.clustering.clusterValidity;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;
import com.github.TKnudsen.DMandML.data.cluster.featureVector.FeatureVectorClusteringResult;
import com.github.TKnudsen.DMandML.data.cluster.featureVector.IFeatureVectorClusteringResultSupplier;

/**
 * @version 1.03 TODO_GENERIC The "getClusterResultSet" method does not seem to be used, and makes this very specific
 * @since 2016
 */
public interface IClusterValidityMeasure<FV extends IFeatureVectorObject<?, ?>> extends ISelfDescription {

	public IFeatureVectorClusteringResultSupplier<FeatureVectorClusteringResult<FV>> getClusterResultSet();

	public void run();

	public double getClusterValidity();
}
