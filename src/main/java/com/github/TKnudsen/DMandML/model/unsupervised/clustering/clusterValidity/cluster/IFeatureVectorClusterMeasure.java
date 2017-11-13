package com.github.TKnudsen.DMandML.model.unsupervised.clustering.clusterValidity.cluster;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;

/**
 * <p>
 * Title: IFeatureVectorClusterMeasure
 * </p>
 * 
 * <p>
 * Description: Interface for the series of measures that can be applied on a
 * single cluster. Compactness is a prominent example.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IFeatureVectorClusterMeasure<FV extends AbstractFeatureVector<?, ?>> extends IClusterMeasure<FV> {

}
