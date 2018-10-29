package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.compactness;

import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.tools.DataConversion;
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
 * Determines the compactness of groups of instances (classes). Calculates
 * interestingness scores of elements according to the similarity to the winning
 * class centroid (center of gravity). This measure can be used to favor
 * instances for labeling in either compact or spatially distributed classes.
 * </p>
 * 
 * Implementation of the Compactness Estimation (CE) DOI/building block published in: Juergen Bernard,
 * Matthias Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair:
 * Towards User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.02
 */
public class SpatialClassCentroidSimilarityInterestingnessFunction
		extends SpatialClassCentroidsInterestingnessFunction {

	public SpatialClassCentroidSimilarityInterestingnessFunction(
			IClassificationApplicationFunction<NumericalFeatureVector> classificationResultFunction,
			String classifierName) {
		super(classificationResultFunction, classifierName);
	}

	@Override
	protected Double calculateScore(NumericalFeatureVector fv, LabelDistribution labelDistribution,
			Map<String, Double[]> centersOfGravity) {

		String winningClass = labelDistribution.getMostLikelyItem();

		double distance = getNumberDistanceMeature()
				.getDistance(DataConversion.toPrimitives(centersOfGravity.get(winningClass)), fv.getVector());

		return -distance;
	}

	@Override
	public String getName() {
		return "Class Centroid Similarity [" + getClassifierName() + "]";
	}

}
