package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.uncertainty;

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
 * Calculates interestingness scores of instances with respect to the
 * uncertainty of predictions of a classifier.
 * </p>
 * 
 * Measure: least significant confidence.
 * </p>
 * 
 * Reference: A. Culotta and A. McCallum. Reducing labeling effort for
 * structured prediction tasks. In Conference on Artificial Intelligence (AAAI),
 * pp. 746–751. AAAI Press, 2005.
 * </p>
 * 
 * Builds upon the Class Likelihood (CL) DOI/building block (probability
 * distribution per instance) published in: Juergen Bernard, Matthias
 * Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair: Towards
 * User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.05
 */
public class ClassUncertaintyLeastSignificantConfidenceInterestingnessFunction<FV>
		extends ClassUncertaintyBasedInterestingnessFunction<FV> {

	public ClassUncertaintyLeastSignificantConfidenceInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction) {
		this(probabilisticClassificationResultFunction, null);
	}

	public ClassUncertaintyLeastSignificantConfidenceInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction, String classifierName) {
		super(probabilisticClassificationResultFunction, classifierName);
	}

	@Override
	protected double calculateUncertaintyScore(LabelDistribution labelDistribution) {
		return -labelDistribution.getProbability(labelDistribution.getMostLikelyItem());
	}

	@Override
	public String getName() {
		if (getClassifierName() != null)
			return "Class Uncertainty Least Significant Confidence [" + getClassifierName() + "]";
		else
			return "Class Uncertainty Least Significant Confidence";
	}
}
