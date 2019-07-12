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
 * Queries controversial instances/regions in the input space. Compares the
 * label distributions of every candidate for a given set of models. The winning
 * candidate poses those label distributions where the committee disagrees most.
 * 
 * Measure: Entropy applied on the distribution of winning labels.
 * 
 * Reference: Dagan and S. Engelson. Committee-based sampling for training
 * probabilistic classifiers. In Proceedings of the International Conference on
 * Machine Learning (ICML), pages 150–157. Morgan Kaufmann, 1995.
 * </p>
 * 
 * @version 1.04
 */
public class ClassCommitteeVoteEntropyInterestingnessFunction<FV>
		extends ClassCommitteeVotesInterestingnessFunction<FV> {

	public ClassCommitteeVoteEntropyInterestingnessFunction(
			List<IClassificationApplicationFunction<FV>> classificationResults) {
		super(classificationResults);
	}

	@Override
	protected double computeClassCommitteeVoteInterestingness(FV fv, List<Double> votesDistribution) {
		double entropy = Entropy.calculateEntropy(votesDistribution);

		return entropy;
	}

	@Override
	public String getName() {
		return "Class Committee Vote Entropy";
	}

}
