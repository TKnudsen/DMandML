package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.statistics.Entropy;

import java.util.ArrayList;
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
 * Description: Local Class Diversity (LCD) assesses the diversity of class
 * predictions in the neighborhood of an instance x. Thus, each instance needs
 * to have a most likely class y0 assigned by a classifier. Given an instance x
 * and a class label yi, we can compute the portion pi of neighbors with the
 * class prediction y0 = yi.
 * 
 * The local class diversity can then be estimated by a diversity measure div as
 * follows: LCD(x) = div(p), where p is the vector of all portions pi for the n
 * classes: p = (p1; ::; pn). The entropy of p is one possible choice for
 * function div.
 * 
 * Measure: Entropy.
 * 
 * @version 1.01
 */
public class SpatialClassVotesEntropyInterestingnessFunction<FV>
		extends SpatialClassVotesDiversityInterestingnessFunction<FV> {

	public SpatialClassVotesEntropyInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationResultFunction, int kNN,
			IDistanceMeasure<FV> distanceMeasure, String classifierName) {
		super(classificationResultFunction, kNN, distanceMeasure, classifierName);
	}

	@Override
	protected double calculateDivsersity(Collection<Integer> votes) {
		Collection<Double> values = new ArrayList<>();

		for (Integer v : votes)
			values.add(v.doubleValue());

		return Entropy.calculateEntropy(values);
	}

	@Override
	public String getName() {
		return "Class Vote Entropy [" + getClassifierName() + "]";
	}

}
