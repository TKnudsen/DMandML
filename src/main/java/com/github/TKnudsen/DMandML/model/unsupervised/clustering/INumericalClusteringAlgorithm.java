package com.github.TKnudsen.DMandML.model.unsupervised.clustering;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;

/**
 * <p>
 * Title: INumericalClusteringAlgorithm
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public interface INumericalClusteringAlgorithm extends IClusteringAlgorithm<NumericalFeatureVector> {

	// TODO changing Cluster<NumericalFeatureVector> to NumericalFVC causes
	// problems when using the classes. Unfortunately.
	public IClusteringResult<NumericalFeatureVector, Cluster<NumericalFeatureVector>> getClusteringResult();
}