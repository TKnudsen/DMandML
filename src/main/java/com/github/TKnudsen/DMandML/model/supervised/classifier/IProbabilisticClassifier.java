package com.github.TKnudsen.DMandML.model.supervised.classifier;

import java.util.List;
import java.util.Map;

import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResult;

/**
 * <p>
 * Title: IProbabilisticClassifier
 * </p>
 * 
 * <p>
 * Description: characteristics of a probabilistic classifier
 * </p>
 * 
 * <p>
 * Copyright: (c) 2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 * 
 */
public interface IProbabilisticClassifier<X> extends IClassifier<X> {

	public Map<String, Double> getLabelDistribution(X featureVector);

	public IProbabilisticClassificationResult<X> createClassificationResult(List<X> featureVectors);
}
