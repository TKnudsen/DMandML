package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.votes;

import com.github.TKnudsen.ComplexDataObject.model.statistics.SimpsonsIndex;

import java.util.ArrayList;
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
 * </p>
 * 
 * Measure: Simpsons Diversity index is applied in the (full) distribution of
 * winning votes per class.
 * </p>
 * 
 * @version 1.04
 */
public class ClassCommitteeVoteDiversityInterestingnessFunction<FV>
		extends ClassCommitteeVotesInterestingnessFunction<FV> {

	public ClassCommitteeVoteDiversityInterestingnessFunction(
			List<IClassificationApplicationFunction<FV>> classificationResults) {
		super(classificationResults);
	}

	@Override
	protected double computeClassCommitteeVoteInterestingness(FV fv, List<Double> votesDistribution) {
		List<Integer> distribution = new ArrayList<>();
		for (Double d : votesDistribution)
			distribution.add(d.intValue());

		double diversity = SimpsonsIndex.calculateSimpsonsIndex(distribution);

		// most diverse instance shall win (by min-max normalization)
		return -diversity;
	}

	@Override
	public String getName() {
		return "Class Committee Vote Diversity";
	}

}
