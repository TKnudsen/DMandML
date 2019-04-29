package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes;

import java.util.Collection;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2019 Juergen Bernard,
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
public class ClassVotesWinnerConfidenceInterestingnessFunction<FV>
		extends ClassVotesDiversityInterestingnessFunction<FV> {

	public ClassVotesWinnerConfidenceInterestingnessFunction(
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

		double value = (-winnerCount / (-winnerCount - allCount));
		return value;
	}

	@Override
	public String getName() {
		return "Class Vote Winner Confidence [" + getClassifierName() + "]";
	}

}
