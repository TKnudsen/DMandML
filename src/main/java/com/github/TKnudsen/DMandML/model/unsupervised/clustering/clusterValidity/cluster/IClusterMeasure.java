package com.github.TKnudsen.DMandML.model.unsupervised.clustering.clusterValidity.cluster;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;

/**
 * <p>
 * Interface for the series of measures that can be applied on a single
 * cluster. Compactness is a prominent example.
 * </p>
 *
 * @version 1.01
 * @since 2016
 */
public interface IClusterMeasure<O extends IDObject> {

	public double getMeasure(Cluster<O> cluster);
}
