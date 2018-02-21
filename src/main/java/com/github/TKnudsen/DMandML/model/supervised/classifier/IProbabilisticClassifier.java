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
 * 
 * @version 1.03
 * 
 */
public interface IProbabilisticClassifier<X> extends IClassifier<X> {

	/**
	 * Returns a (possibly unmodifiable) map containing the mapping
	 * of all class labels to the probability that the given vector 
	 * belongs to the respective class.<br>
	 * <br>
	 * If the classifier has not been trained yet, this will be 
	 * an empty map.
	 *  
	 * @param featureVector The feature vector
	 * @return The label distribution
	 */
	Map<String, Double> getLabelDistribution(X featureVector);

	@Override
	IProbabilisticClassificationResult<X> createClassificationResult(List<X> featureVectors);
}
