package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.probabilities;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.Double.probabilities.KullbackLeiblerDivergenceDistance;

import java.util.List;

import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * calculates interestingness scores of elements with respect to the uncertainty
 * of pre-given ClusteringResults. Uses the Kullback Leibler divergence to
 * assess the similarities of probability distributions.
 * </p>
 * 
 * Measure: Kullback Leibler Divergence.
 * </p>
 * 
 * Reference: McCallum and K. Nigam. Employing EM in pool-based active learning
 * for text classification. In Proceedings of the International Conference on
 * Machine Learning (ICML), pages 359–367. Morgan Kaufmann, 1998.
 * </p>
 * 
 * @version 1.03
 */
public class ClassCommitteeKullbackLeiblerInterestingnessFunction<FV>
		extends ClassCommitteeProbabilitiesInterestingnessFunction<FV> {

	public ClassCommitteeKullbackLeiblerInterestingnessFunction(
			List<IClassificationApplicationFunction<FV>> classificationResults) {
		// true is mandatory. otherwise KL may produce infinity values
		super(classificationResults, new KullbackLeiblerDivergenceDistance(true));
	}

	@Override
	public String getName() {
		return "Class Committee Kullback Leibler Divergence";
	}

}
