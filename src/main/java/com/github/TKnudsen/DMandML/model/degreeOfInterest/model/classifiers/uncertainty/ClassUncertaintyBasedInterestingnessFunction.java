package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.uncertainty;

import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
 * Description: calculates interestingness scores of instances with respect to
 * the uncertainty of predictions of a classifier. different implementations may
 * be based on winning labels, margins, or Entropy. The majority of
 * implementations exploits the output of probabilistic classifiers, thus taking
 * the likelihood of an instance for any class into account.
 * </p>
 * 
 * Builds upon the Class Likelihood (CL) DOI/building block (probability
 * distribution per instance) published in: Juergen Bernard, Matthias
 * Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair: Towards
 * User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.02
 */
abstract class ClassUncertaintyBasedInterestingnessFunction<FV> extends ClassificationBasedInterestingnessFunction<FV> {

	public ClassUncertaintyBasedInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction) {
		this(probabilisticClassificationResultFunction, null);
	}

	public ClassUncertaintyBasedInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction, String classifierName) {
		super(probabilisticClassificationResultFunction, classifierName);
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> featureVectors) {

		Objects.requireNonNull(featureVectors);
		if (featureVectors.isEmpty()) {
			return Collections.emptyMap();
		}

		IClassificationResult<FV> classificationResult = computeClassificationResult(featureVectors);

		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();
		Collection<Number> values = new ArrayList<>();

		if (classificationResult == null || classificationResult.getClassDistributions() == null) {
			for (FV fv : featureVectors)
				interestingnessScores.put(fv, 0.0);

			return interestingnessScores;
		}

		for (FV fv : featureVectors) {
			double uncertaintyScore = 1.0;
			LabelDistribution labelDistribution = classificationResult.getLabelDistribution(fv);

			uncertaintyScore = calculateUncertaintyScore(labelDistribution);

			interestingnessScores.put(fv, uncertaintyScore);
			values.add(uncertaintyScore);
		}

		// for validation purposes
		MapUtils.checkForCriticalValue(interestingnessScores, null, true);
		MapUtils.checkForCriticalValue(interestingnessScores, Double.NaN, true);
		MapUtils.checkForCriticalValue(interestingnessScores, Double.NEGATIVE_INFINITY, true);
		MapUtils.checkForCriticalValue(interestingnessScores, Double.POSITIVE_INFINITY, true);

		// post-processing
		NormalizationFunction normalizationFunction = new LinearNormalizationFunction(values);
		for (FV fv : interestingnessScores.keySet())
			interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

		return interestingnessScores;
	}

	protected abstract double calculateUncertaintyScore(LabelDistribution labelDistribution);

	@Override
	public String getDescription() {
		return getName();
	}

	@Override
	public String toString() {
		return getName();
	}

}
