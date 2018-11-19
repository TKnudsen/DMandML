package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.separation;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.tools.DataConversion;

import java.util.Map;

import com.github.TKnudsen.DMandML.data.classification.LabelDistribution;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.SpatialClassCentroidsInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * calculates interestingness scores of elements according to the separation of
 * classes (class prediction). For that purpose the distances between instances
 * and class centroids (centers of gravity) multiplied with class probabilities,
 * and added. The Result is an error measure. Instances with minimum errors will
 * win the interestingness function.
 * </p>
 * 
 * Builds upon the Local Class Separation (LCS) principle (estimates how well
 * the predicted classes around a given instance are separated from each other).
 * </p>
 * 
 * Published in: Juergen Bernard, Matthias Zeppelzauer, Markus Lehmann, Martin
 * Mueller, and Michael Sedlmair: Towards User-Centered Active Learning
 * Algorithms. Eurographics Conference on Visualization (EuroVis), Computer
 * Graphics Forum (CGF), 2018.
 * </p>
 * 
 * Measure: Euclidean distance measure
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.06
 */
public class SpatialClassCentroidsDistancesInterestingnessFunction
		extends SpatialClassCentroidsInterestingnessFunction {

	public SpatialClassCentroidsDistancesInterestingnessFunction(
			IClassificationApplicationFunction<NumericalFeatureVector> classificationResultFunction,
			String classifierName) {
		super(classificationResultFunction, classifierName);
	}

	@Override
	protected Double calculateScore(NumericalFeatureVector fv, LabelDistribution labelDistribution,
			Map<String, Double[]> centersOfGravity) {

		double error = 0.0;
		double probabilities = 0.0;

		// multiply probability with distance for each class
		for (String label : labelDistribution.keySet()) {
			Double probability = labelDistribution.getProbability(label);
			double[] centerOfGravity = DataConversion.toPrimitives(centersOfGravity.get(label));

			double distance = 0.0;
			if (centerOfGravity != null) {
				distance = getNumberDistanceMeature()
						.getDistance(DataConversion.toPrimitives(centersOfGravity.get(label)), fv.getVector());
				probabilities += probability;
			}

			error += (probability * distance);
		}

		if (probabilities != 0)
			error /= probabilities;

		return -error;
	}

	@Override
	public String getName() {
		return "Class Centroids Distances [" + getClassifierName() + "]";
	}

}
