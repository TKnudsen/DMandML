package com.github.TKnudsen.DMandML.model.supervised.classifier;

import com.github.TKnudsen.ComplexDataObject.data.features.mixedData.MixedDataFeatureVector;

/**
 * <p>
 * Title: IMixedDataClassifier
 * </p>
 * 
 * <p>
 * Description: basic classification model that learns categorical label
 * information for mixed data features.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public interface IMixedDataClassifier extends IProbabilisticClassifier<Object, MixedDataFeatureVector> {

}
