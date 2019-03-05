package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.relevance;

import com.github.TKnudsen.DMandML.data.classification.LabelDistribution;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Assigns an interestingness score to each instance based on the most confident
 * element of the distribution. This is analog to self-training in
 * semi-supervised learning.
 * 
 * For reference see: D. Yarowski, Unsupervised word sense disambiguation
 * rivaling supervised methods, In Proceedings of the Association for
 * Computational Linguistics (ACL), 1995.
 * </p>
 * 
 * @version 1.02
 */
public class ClassRelevanceMostSignificantConfidenceInterestingnessFunction<FV>
		extends ClassRelevanceBasedInterestingnessFunction<FV> {

	public ClassRelevanceMostSignificantConfidenceInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction) {
		super(probabilisticClassificationResultFunction);
	}

	public ClassRelevanceMostSignificantConfidenceInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction, String classifierName) {
		super(probabilisticClassificationResultFunction, classifierName);
	}

	@Override
	protected double calculateRelevanceScore(LabelDistribution labelDistribution) {
		if (labelDistribution.getMostLikelyItem() == null)
			return 0;

		return labelDistribution.getProbability(labelDistribution.getMostLikelyItem());
	}

	@Override
	public String getName() {
		if (getClassifierName() != null)
			return "Class Relevance Most Significant Confidence [" + getClassifierName() + "]";
		else
			return "Class Relevance Most Significant Confidence";
	}

}