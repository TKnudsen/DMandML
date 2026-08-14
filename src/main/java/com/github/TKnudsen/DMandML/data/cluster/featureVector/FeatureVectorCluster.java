package com.github.TKnudsen.DMandML.data.cluster.featureVector;

import java.util.Collection;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;

/**
 * <p>
 * Super class for Featurevectors. Is not abstract any more to engage general
 * use of feature vectors in a more abstract way.
 * </p>
 *
 * @version 1.03
 * @since 2016
 */
public class FeatureVectorCluster<FV> extends Cluster<FV> {

	public FeatureVectorCluster(Collection<? extends FV> elements, IDistanceMeasure<FV> distanceMeasure) {
		this(elements, distanceMeasure, "", "");
	}

	public FeatureVectorCluster(Collection<? extends FV> elements, IDistanceMeasure<FV> distanceMeasure, String name,
			String description) {
		super(elements, distanceMeasure, name, description);
	}

	@Override
	public Cluster<FV> clone() {
		return new FeatureVectorCluster<>(getElements(), distanceMeasure);
	}

}