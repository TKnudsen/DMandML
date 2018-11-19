package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.statistics.SimpsonsIndex;

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
 * Local Class Diversity (LCD) assesses the diversity of class predictions in
 * the neighborhood of an instance x. For every instance in the vicinity the
 * winning label is used to calculate the diversity measure.
 * 
 * @version 1.03
 */
public class SpatialClassVotesSimpsonsInterestingnessFunction<FV>
		extends SpatialClassVotesDiversityInterestingnessFunction<FV> {

	public SpatialClassVotesSimpsonsInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationResultFunction, int kNN,
			IDistanceMeasure<FV> distanceMeasure, String classifierName) {
		super(classificationResultFunction, kNN, distanceMeasure, classifierName);
	}

	@Override
	protected double calculateDivsersity(Collection<Integer> votes) {
		return SimpsonsIndex.calculateSimpsonsIndex(votes);
	}

	@Override
	public String getName() {
		return "Class Vote Simpson Diversity [" + getClassifierName() + "]";
	}

}
