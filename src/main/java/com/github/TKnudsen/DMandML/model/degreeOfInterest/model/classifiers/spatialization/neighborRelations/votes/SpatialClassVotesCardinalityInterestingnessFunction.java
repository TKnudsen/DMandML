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
 * Assigns an interestingness score to each given instance. The score is based
 * on the label vote of the k nearest neighbors of each instance.<br>
 * <br>
 * Measure: Vote cardinality. Ratio of different votes divided by the number of
 * votes. Measure is discrete as the number of committee members limits the
 * number of different result niveaus.
 * 
 * @version 1.01
 */
public class SpatialClassVotesCardinalityInterestingnessFunction<FV>
		extends SpatialClassVotesDiversityInterestingnessFunction<FV> {

	public SpatialClassVotesCardinalityInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationResultFunction, int kNN,
			IDistanceMeasure<FV> distanceMeasure, String classifierName) {
		super(classificationResultFunction, kNN, distanceMeasure, classifierName);
	}

	@Override
	protected double calculateDivsersity(Collection<Integer> votes) {
		double numberOfDifferentVotes = 0.0;

		for (Integer i : votes) {
			if (i != 0)
				numberOfDifferentVotes++;
		}

		// TODO if SpatialClassVotesDiversityInterestingnessFunction.apply()
		// will change to invert its output this has to be made negative
		double value = numberOfDifferentVotes / votes.size();
		return value;
	}

	@Override
	public String getName() {
		return "Class Vote Cardinality [" + getClassifierName() + "]";
	}
}
