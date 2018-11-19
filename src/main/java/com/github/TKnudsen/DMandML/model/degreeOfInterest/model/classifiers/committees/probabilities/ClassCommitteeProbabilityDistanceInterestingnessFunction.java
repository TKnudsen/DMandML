package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.probabilities;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.Double.EuclideanDistanceMeasure;

import java.util.List;

import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.ClassificationCommitteeBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores of elements with respect to the uncertainty
 * of pre-given ClusteringResults. Uses the Euclidean distance measure to assess
 * the distances of probability distributions.
 * </p>
 * 
 * @version 1.01
 */
public class ClassCommitteeProbabilityDistanceInterestingnessFunction<FV>
		extends ClassificationCommitteeBasedInterestingnessFunction<FV> {

	public ClassCommitteeProbabilityDistanceInterestingnessFunction(
			List<IClassificationApplicationFunction<FV>> classificationResults) {
		super(classificationResults, new EuclideanDistanceMeasure());
	}

	@Override
	public String getName() {
		return "Class Committee Probability Distance";
	}

}
