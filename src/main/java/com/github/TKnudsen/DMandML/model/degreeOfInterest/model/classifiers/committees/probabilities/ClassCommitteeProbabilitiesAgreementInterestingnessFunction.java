package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.probabilities;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.Double.EuclideanDistanceMeasure;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores of elements with respect to the uncertainty
 * of pre-given ClusteringResults. Uses the Jensen Shannon divergence to assess
 * the similarities of probability distributions.
 * </p>
 * 
 * Measure: Jensen Shannon Divergence.
 * </p>
 * 
 * Reference: Melville, S.M. Yang, M. Saar-Tsechansky, and R. Mooney. Active
 * learning for probability estimation using Jensen-Shannon divergence. In
 * Proceedings of the European Conference on Machine Learning (ECML), pages
 * 268–279. Springer, 2005.
 * </p>
 * 
 * @version 1.02
 */
public class ClassCommitteeProbabilitiesAgreementInterestingnessFunction<FV>
		extends ClassCommitteeProbabilitiesInterestingnessFunction<FV> {

	public ClassCommitteeProbabilitiesAgreementInterestingnessFunction(
			List<IClassificationApplicationFunction<FV>> classificationResults) {
		super(classificationResults, new EuclideanDistanceMeasure(), true);
	}

	@Override
	public String getName() {
		return "Class Committee Probabilities Agreement";
	}

}
