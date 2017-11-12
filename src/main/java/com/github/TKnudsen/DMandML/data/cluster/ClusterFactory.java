package com.github.TKnudsen.DMandML.data.cluster;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.mixedData.MixedDataFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.featureFV.mixed.MixedDataFeatureVectorCluster;
import com.github.TKnudsen.DMandML.data.cluster.featureFV.numerical.NumericalFeatureVectorCluster;
import com.github.TKnudsen.DMandML.data.cluster.general.GeneralCluster;

/**
 * <p>
 * Title: ClusterFactory
 * </p>
 * 
 * <p>
 * Description: can be used when instances of Cluster<Generic> have to be
 * created.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public class ClusterFactory {

	@SuppressWarnings("unchecked")
	public <T extends IDObject> Cluster<T> createCluster(List<T> elements, IDistanceMeasure<T> distanceMeasure, String name, String description) {

		if (elements == null || elements.size() == 0)
			return null;

		T element = elements.get(0);

		Cluster<T> cluster = null;
		if (element instanceof NumericalFeatureVector) {
			cluster = (Cluster<T>) new NumericalFeatureVectorCluster((List<NumericalFeatureVector>) elements, (IDistanceMeasure<NumericalFeatureVector>) distanceMeasure, name, description);
		} else if (element instanceof MixedDataFeatureVector) {
			cluster = (Cluster<T>) new MixedDataFeatureVectorCluster((List<MixedDataFeatureVector>) elements, (IDistanceMeasure<MixedDataFeatureVector>) distanceMeasure, name, description);
		} else {
			cluster = (Cluster<T>) new GeneralCluster(elements, (IDistanceMeasure<IDObject>) distanceMeasure, name, description);
		}

		return cluster;
	}
}
