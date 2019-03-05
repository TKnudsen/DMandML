package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.uncertainty;

import com.github.TKnudsen.ComplexDataObject.model.statistics.Entropy;
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
 * Measure: Shannon Entropy
 * </p>
 * 
 * Reference: C.E. Shannon. A mathematical theory of communication. Bell System
 * Technical Journal, 27:379–423,623–656, 1948.
 * </p>
 * 
 * Builds upon the Class Likelihood (CL) DOI/building block (probability
 * distribution per instance) published in: Juergen Bernard, Matthias
 * Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair: Towards
 * User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.02
 */
public class ClassUncertaintyEntropyBasedInterestingnessFunction<FV>
		extends ClassUncertaintyBasedInterestingnessFunction<FV> {

	public ClassUncertaintyEntropyBasedInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction) {
		this(probabilisticClassificationResultFunction, null);
	}

	public ClassUncertaintyEntropyBasedInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction, String classifierName) {
		super(probabilisticClassificationResultFunction, classifierName);
	}

	@Override
	protected double calculateUncertaintyScore(LabelDistribution labelDistribution) {
		return Entropy.calculateEntropy(labelDistribution.getProbabilityDistribution());
	}

	@Override
	public String getName() {
		if (getClassifierName() != null)
			return "Class Uncertainty Entropy [" + getClassifierName() + "]";
		else
			return "Class Uncertainty Entropy";
	}

}
