package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.Double.EuclideanDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;
import com.github.TKnudsen.DMandML.data.classification.ClassificationResults;
import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.data.classification.LabelDistribution;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.MapUtils;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.ClassificationBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * {@link IDegreeOfInterestFunction} that takes the spatial locations of class
 * centroids into account, combined with the output of probabilistic
 * classifiers. The latter is referred to as Class Likelihood (CL) DOI/building
 * blocks. Spatialization either addresses Local Class Diversity (LCD) (assesses
 * the diversity of class predictions in the neighborhood of an instance x) or
 * Local Class Separation (LCS) (estimates how well the predicted classes around
 * a given instance are separated from each other).
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
 * @author Juergen Bernard
 * 
 * @version 1.02
 */
public abstract class SpatialClassCentroidsInterestingnessFunction
		extends ClassificationBasedInterestingnessFunction<NumericalFeatureVector> {

	private IDistanceMeasure<double[]> numberDistanceMeature = new EuclideanDistanceMeasure();

	public SpatialClassCentroidsInterestingnessFunction(
			IClassificationApplicationFunction<NumericalFeatureVector> classificationResultFunction) {
		this(classificationResultFunction, null);
	}

	public SpatialClassCentroidsInterestingnessFunction(
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

		LinkedHashMap<NumericalFeatureVector, Double> interestingnessScores = new LinkedHashMap<>();

		IClassificationResult<NumericalFeatureVector> classificationResult = computeClassificationResult(
				featureVectors);

		if (classificationResult == null || classificationResult.getClassDistributions() == null) {
			for (NumericalFeatureVector fv : featureVectors)
				interestingnessScores.put(fv, 0.0);

			return interestingnessScores;
		}

		// create centers of gravity for every class
		Map<String, Double[]> centersOfGravity = ClassificationResults.createCentersOfGravity(classificationResult);

		for (NumericalFeatureVector fv : featureVectors) {
			LabelDistribution labelDistribution = classificationResult.getLabelDistribution(fv);

			Double score = calculateScore(fv, labelDistribution, centersOfGravity);

			interestingnessScores.put(fv, score);
		}

		// for validation purposes
		if (MapUtils.doiValidationMode) {
			MapUtils.checkForCriticalValue(interestingnessScores, null, true);
			MapUtils.checkForCriticalValue(interestingnessScores, Double.NaN, true);
			MapUtils.checkForCriticalValue(interestingnessScores, Double.NEGATIVE_INFINITY, true);
			MapUtils.checkForCriticalValue(interestingnessScores, Double.POSITIVE_INFINITY, true);
		}

		// post-processing
		NormalizationFunction normalizationFunction = new LinearNormalizationFunction(interestingnessScores.values());
		for (NumericalFeatureVector fv : interestingnessScores.keySet())
			interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

		// return interestingnessScores;
		return MapUtils.affineTransformValues(interestingnessScores, -1, 1);
	}

	protected abstract Double calculateScore(NumericalFeatureVector fv, LabelDistribution labelDistribution,
			Map<String, Double[]> centersOfGravity);

	@Override
	public String getDescription() {
		return getName();
	}

	@Override
	public String toString() {
		return getName();
	}

	public IDistanceMeasure<double[]> getNumberDistanceMeature() {
		return numberDistanceMeature;
	}

	public void setNumberDistanceMeature(IDistanceMeasure<double[]> numberDistanceMeature) {
		this.numberDistanceMeature = numberDistanceMeature;
	}

}
