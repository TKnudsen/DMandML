package com.github.TKnudsen.DMandML.model.unsupervised.clustering.clusterValidity.cluster;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;

/**
 * <p>
 * Interface for the series of measures that can be applied on a single
 * cluster. Compactness is a prominent example.
 * </p>
 *
 * @version 1.01 TODO_GENERICS This is now equivalent to IClusterMeasure
 * @since 2016
 */
public interface IFeatureVectorClusterMeasure<FV extends IDObject> extends IClusterMeasure<FV> {

}
