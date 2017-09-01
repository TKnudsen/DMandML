package com.github.TKnudsen.DMandML.model.supervised.classifier;

import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

/**
 * <p>
 * Title: IProbablisticClassifier
 * </p>
 * 
 * <p>
 * Description: characteristics of a probablistic classifier
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public interface IProbablisticClassifier<O, X extends AbstractFeatureVector<O, ? extends Feature<O>>> extends IClassifier<O, X> {

	public Map<String, Double> getLabelDistribution(X featureVector);

	public double getLabelProbabilityMax(X featureVector);

	public double getLabelProbabilityMargin(X featureVector);
}
