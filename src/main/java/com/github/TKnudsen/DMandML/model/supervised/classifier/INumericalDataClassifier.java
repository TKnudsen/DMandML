package com.github.TKnudsen.DMandML.model.supervised.classifier;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;

/**
 * <p>
 * basic algorithmic model that learns label information for numerical
 * features. The labels are strings, thus, classifier-like models are trained.
 * </p>
 *
 * @version 1.03
 * @since 2016
 */
public interface INumericalDataClassifier extends IClassifier<NumericalFeatureVector> {

}
