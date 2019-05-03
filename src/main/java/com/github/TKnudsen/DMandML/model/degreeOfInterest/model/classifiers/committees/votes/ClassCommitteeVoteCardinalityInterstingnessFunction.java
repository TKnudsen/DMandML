package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.votes;

import java.util.List;

import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2019 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Queries controversial instances/regions in the input space. Compares the
 * label distributions of every candidate for a given set of models. The winning
 * candidate poses those label distributions where the committee disagrees most.
 * 
 * Measure: Vote cardinality. Ratio of different votes divided by the number of
 * votes. Measure is discrete as the nr. of committee members limits the number
 * of different result nevieaus.
 * </p>
 * 
 * @version 1.04
 */
public class ClassCommitteeVoteCardinalityInterstingnessFunction<FV> extends ClassCommitteeVotesInterestingnessFunction<FV> {

	public ClassCommitteeVoteCardinalityInterstingnessFunction(List<IClassificationApplicationFunction<FV>> classificationResults) {
		super(classificationResults);
	}

	@Override
	protected double computeClassCommitteeVoteInterestingness(FV fv, List<Double> votesDistribution) {
		double nrOfWinners = 0.0;
		for (Double d : votesDistribution)
			if (d > 0)
				nrOfWinners++;

		double ratio = (nrOfWinners - 1) / (double) votesDistribution.size();

		return ratio;
	}

	@Override
	public String getName() {
		return "Class Committee Vote Cardinality";
	}

}