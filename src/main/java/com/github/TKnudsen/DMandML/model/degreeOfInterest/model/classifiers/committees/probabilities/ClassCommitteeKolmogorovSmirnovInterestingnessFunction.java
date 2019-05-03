package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.probabilities;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.Double.probabilities.KolmogorovSmirnovDistance;

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
 * Calculates interestingness scores of elements with respect to the uncertainty
 * of pre-given ClusteringResults. Uses the Kolmogorov Smirnov test to assess
 * the similarities of probability distributions.
 * </p>
 * 
 * @version 1.03
 */
public class ClassCommitteeKolmogorovSmirnovInterestingnessFunction<FV>
		extends ClassCommitteeProbabilitiesInterestingnessFunction<FV> {

	public ClassCommitteeKolmogorovSmirnovInterestingnessFunction(
			List<IClassificationApplicationFunction<FV>> classificationResults) {
		super(classificationResults, new KolmogorovSmirnovDistance());
	}

	@Override
	public String getName() {
		return "Class Committee Kolmogorov Smirnov Test";
	}

}
