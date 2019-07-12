package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.votes;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.model.statistics.Entropy;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2019 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Queries instances with high agreement of committee votes. Uses the entropy of
 * the vote distribution and inverts it.
 * </p>
 * 
 * @version 1.01
 */
public class ClassCommitteeVoteAgreementInterestingnessFunction<FV>
		extends ClassCommitteeVotesInterestingnessFunction<FV> {

	public ClassCommitteeVoteAgreementInterestingnessFunction(
			List<IClassificationApplicationFunction<FV>> classificationResults) {
		super(classificationResults);
	}

	@Override
	protected double computeClassCommitteeVoteInterestingness(FV fv, List<Double> votesDistribution) {
		double entropy = Entropy.calculateEntropy(votesDistribution);

		// the less the entropy the higher the agreement
		return -entropy;
	}

	@Override
	public String getName() {
		return "Class Committee Vote Agreement";
	}

}