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
 * Copyright: (c) 2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 * 
 * TODO_GENERICS Parameter "O" is not used any more
 */
public interface IProbabilisticClassifier<O, X> extends IClassifier<O, X> {

	public Map<String, Double> getLabelDistribution(X featureVector);

	public IProbabilisticClassificationResult<X> createClassificationResult(List<X> featureVectors);
}
