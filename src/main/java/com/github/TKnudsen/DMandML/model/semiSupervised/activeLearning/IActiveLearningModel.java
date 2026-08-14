package com.github.TKnudsen.DMandML.model.semiSupervised.activeLearning;

import java.util.List;

/**
 * <p>
 * active learners suggest unlabeled instances to oracles (i.e., users) in a
 * way that a given learning model (e.g., a classifier) is supposed to improce
 * its quality in a "best" way. Formalization of "best" depends on the
 * particular implementation.
 * </p>
 *
 * @version 1.07
 * @since 2016
 */
public interface IActiveLearningModel<FV, Y> {

	public void setCandidates(List<? extends FV> candidates);

	public FV suggestCandidate();

	public List<FV> suggestCandidates(int count);

	public double getRemainingUncertainty();

	public double getCandidateScore(FV candidate);
}
