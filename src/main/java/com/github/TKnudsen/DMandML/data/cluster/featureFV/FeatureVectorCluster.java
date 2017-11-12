package com.github.TKnudsen.DMandML.data.cluster.featureFV;

import java.util.Collection;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;

/**
 * <p>
 * Title: FeatureVectorCluster
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
 */
public abstract class FeatureVectorCluster<FV extends AbstractFeatureVector<?, ?>> extends Cluster<FV> {

	public FeatureVectorCluster(Collection<? extends FV> elements, IDistanceMeasure<FV> distanceMeasure) {
		super(elements, distanceMeasure);
	}

	public FeatureVectorCluster(Collection<? extends FV> elements, IDistanceMeasure<FV> distanceMeasure, String name, String description) {
		super(elements, distanceMeasure, name, description);
	}

}