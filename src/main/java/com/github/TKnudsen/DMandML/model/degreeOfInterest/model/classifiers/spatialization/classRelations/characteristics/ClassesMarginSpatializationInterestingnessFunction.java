package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.characteristics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.Double.EuclideanDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.tools.DataConversion;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;
import com.github.TKnudsen.DMandML.data.classification.ClassificationResults;
import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.data.classification.LabelDistribution;
import com.github.TKnudsen.DMandML.data.classification.LabelDistributionTools;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.ClassificationBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2019 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Exploits the spatialization of an instance in the feature space. Uses the
 * winning and the 2nd best class for the interestingness calculation. High
 * interestingness is achieved when the margin between the two classes is high
 * and the distance to the winning class center of gravity is considerably
 * smaller. Function: delta(P1,P2) * (distC1-distC2)
 * </p>
 * 
 * Builds upon the Local Class Diversity (LCD) principle (assesses the diversity
 * of class predictions in the neighborhood of an instance x).
 * </p>
 * 
 * Published in: Juergen Bernard, Matthias Zeppelzauer, Markus Lehmann, Martin
 * Mueller, and Michael Sedlmair: Towards User-Centered Active Learning
 * Algorithms. Eurographics Conference on Visualization (EuroVis), Computer
 * Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.03
 */
public class ClassesMarginSpatializationInterestingnessFunction
		extends ClassificationBasedInterestingnessFunction<NumericalFeatureVector> {

	private IDistanceMeasure<double[]> numberDistanceMeature = new EuclideanDistanceMeasure();

	public ClassesMarginSpatializationInterestingnessFunction(
			IClassificationApplicationFunction<NumericalFeatureVector> classificationResultFunction,
			String classifierName) {
		super(classificationResultFunction, classifierName);
	}

	@Override
	public Map<NumericalFeatureVector, Double> apply(List<? extends NumericalFeatureVector> featureVectors) {

		Objects.requireNonNull(featureVectors);
		if (featureVectors.isEmpty()) {
			return Collections.emptyMap();
		}

		IClassificationResult<NumericalFeatureVector> classificationResult = computeClassificationResult(
				featureVectors);

		LinkedHashMap<NumericalFeatureVector, Double> interestingnessScores = new LinkedHashMap<>();
		Collection<Number> errors = new ArrayList<>();

		if (classificationResult == null || classificationResult.getClassDistributions() == null) {
			for (NumericalFeatureVector fv : featureVectors)
				interestingnessScores.put(fv, 0.0);

			return interestingnessScores;
		}

		// create centers of gravity for every class
		Map<String, Double[]> centersOfGravity = ClassificationResults.createCentersOfGravity(classificationResult);

		// TODO needs testing (environment)
		System.err.println(getName() + ": untested operation");

		for (NumericalFeatureVector fv : featureVectors) {
			LabelDistribution labelDistribution = classificationResult.getLabelDistribution(fv);

			double score = 0.0;

			String winningLabel = labelDistribution.getMostLikelyItem();
			String secondLabel = LabelDistributionTools.getSecondHighestLabel(labelDistribution);

			double d1 = 0.0;
			if (centersOfGravity.containsKey(winningLabel))
				d1 = numberDistanceMeature.getDistance(DataConversion.toPrimitives(centersOfGravity.get(winningLabel)),
						fv.getVector());

			double d2 = 0.0;
			if (centersOfGravity.containsKey(secondLabel))
				d2 = numberDistanceMeature.getDistance(DataConversion.toPrimitives(centersOfGravity.get(secondLabel)),
						fv.getVector());

			double deltaD = d1 - d2;
			double deltaP = labelDistribution.getProbability(secondLabel)
					- labelDistribution.getProbability(winningLabel);

			score = deltaD * deltaP;

			if (Double.isNaN(score))
				System.err.println(getName() + "NaN problem occurred...");

			// zero achieves the highest value
			interestingnessScores.put(fv, score);
			errors.add(score);
		}

		// post-processing
		NormalizationFunction normalizationFunction = new LinearNormalizationFunction(errors);
		for (NumericalFeatureVector fv : interestingnessScores.keySet())
			interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

		return interestingnessScores;
	}

	@Override
	public String getName() {
		return "Classes Margin Spatialization [" + getClassifierName() + "]";
	}

	@Override
	public String getDescription() {
		return getName();
	}

	@Override
	public String toString() {
		return getName();
	}

}