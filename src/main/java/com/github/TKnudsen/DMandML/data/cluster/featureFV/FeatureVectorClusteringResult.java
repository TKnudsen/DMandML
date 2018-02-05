package com.github.TKnudsen.DMandML.data.cluster.featureFV;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.DMandML.data.cluster.ClusteringResult;

/**
 * <p>
 * Title: FeatureVectorClusteringResult
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 * 
 * TODO_GENERICS This may be unnecessarily specific, see IClusterValidityMeasure
 */
public class FeatureVectorClusteringResult<FV extends IFeatureVectorObject<?, ?>> extends ClusteringResult<FV, FeatureVectorCluster<FV>> {

	public FeatureVectorClusteringResult(List<? extends FeatureVectorCluster<FV>> clusters) {
		super(clusters);
	}

}
