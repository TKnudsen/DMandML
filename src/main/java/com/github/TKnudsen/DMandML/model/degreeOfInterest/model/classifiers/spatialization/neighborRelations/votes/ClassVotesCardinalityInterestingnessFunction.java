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
 * Assigns an interestingness score to each given instance. Exploits the
 * spatialization of an instance in the feature space. The score is based on the
 * label votes of a classifier for the k nearest neighbors of an instance.<br>
 * <br>
 * Measure: Vote cardinality. Ratio of different votes divided by the number of
 * votes. Measure is discrete as the number of committee members limits the
 * number of different result niveaus.
 * 
 * @version 1.02
 */
public class ClassVotesCardinalityInterestingnessFunction<FV> extends ClassVotesDiversityInterestingnessFunction<FV> {

	public ClassVotesCardinalityInterestingnessFunction(
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
