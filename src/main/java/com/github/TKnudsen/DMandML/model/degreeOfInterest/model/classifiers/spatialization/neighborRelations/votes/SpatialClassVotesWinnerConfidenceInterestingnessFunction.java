package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

import java.util.Collection;

import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Spatial Class Winner Confidence measure. Uses the winning label of a
 * particular instance and compares it to the winners in the vicinity.
 * 
 * Only distinguishes between matching and non-matching winners. Similar to
 * least significant confidence calculations of instances for probabilistic
 * classification results.
 * 
 * @version 1.04
 */
public class SpatialClassVotesWinnerConfidenceInterestingnessFunction<FV>
		extends SpatialClassVotesDiversityInterestingnessFunction<FV> {

	public SpatialClassVotesWinnerConfidenceInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationResultFunction, int kNN,
			IDistanceMeasure<FV> distanceMeasure, String classifierName) {
		super(classificationResultFunction, kNN, distanceMeasure, classifierName);
	}

	@Override
	protected double calculateDivsersity(Collection<Integer> votes) {
		double winnerCount = 0.0;
		double allCount = 0.0;

		for (Integer i : votes) {
			winnerCount = Math.max(winnerCount, i.doubleValue());
			allCount += i.doubleValue();
		}

		// System.err.println(getName() + ": check whether the results yield a
		// meaningful positive score.");

		double value = (-winnerCount / (-winnerCount - allCount));
		return value;
	}

	@Override
	public String getName() {
		return "Class Vote Winner Confidence [" + getClassifierName() + "]";
	}

}
