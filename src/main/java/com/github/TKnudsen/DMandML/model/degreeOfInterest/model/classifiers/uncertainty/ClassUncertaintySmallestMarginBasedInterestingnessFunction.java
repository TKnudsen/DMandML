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
 * Measure: smallest margin.
 * </p>
 * 
 * Reference: T. Scheffer, C. Decomain, and S.Wrobel. Active hidden Markov
 * models for information extraction. In Proceedings of the International
 * Conference on Advances in Intelligent Data Analysis (CAIDA), pages 309–318.
 * Springer-Verlag, 2001.
 * </p>
 * 
 * Builds upon the Class Likelihood (CL) DOI/building block (probability
 * distribution per instance) published in: Juergen Bernard, Matthias
 * Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair: Towards
 * User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.04
 */
public class ClassUncertaintySmallestMarginBasedInterestingnessFunction<FV>
		extends ClassUncertaintyBasedInterestingnessFunction<FV> {

	public ClassUncertaintySmallestMarginBasedInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction) {
		this(probabilisticClassificationResultFunction, null);
	}

	public ClassUncertaintySmallestMarginBasedInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction, String classifierName) {
		super(probabilisticClassificationResultFunction, classifierName);
	}

	@Override
	/**
	 * calculates the difference between the best and the second hightes label
	 * probability.
	 * 
	 * @param labelDistribution
	 * @return
	 */
	protected double calculateUncertaintyScore(LabelDistribution labelDistribution) {
		if (labelDistribution == null)
			return 0;

		double max = Double.MIN_VALUE;
		double second = Double.MIN_VALUE;
		for (String label : labelDistribution.getLabelSet()) {
			double value = labelDistribution.getProbability(label);
			if (max <= value) {
				second = max;
				max = value;
			} else if (second <= value)
				second = value;
		}

		return -(max - second);
	}

	@Override
	public String getName() {
		if (getClassifierName() != null)
			return "Class Uncertainty Smallest Margin [" + getClassifierName() + "]";
		else
			return "Class Uncertainty Smallest Margin";
	}

}
