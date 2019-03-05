package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.relevance;

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
 * Calculates interestingness scores of instances with respect to the relevance
 * of predictions of a classifier.
 * </p>
 * 
 * @version 1.01
 */
public abstract class ClassRelevanceBasedInterestingnessFunction<FV>
		extends ClassificationBasedInterestingnessFunction<FV> {

	public ClassRelevanceBasedInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction) {
		this(probabilisticClassificationResultFunction, null);
	}

	public ClassRelevanceBasedInterestingnessFunction(
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
			LabelDistribution labelDistribution = classificationResult.getLabelDistribution(fv);

			double relevanceScore = calculateRelevanceScore(labelDistribution);

			interestingnessScores.put(fv, relevanceScore);
			values.add(relevanceScore);
		}

		// for validation purposes
		if (MapUtils.doiValidationMode) {
			MapUtils.checkForCriticalValue(interestingnessScores, null, true);
			MapUtils.checkForCriticalValue(interestingnessScores, Double.NaN, true);
			MapUtils.checkForCriticalValue(interestingnessScores, Double.NEGATIVE_INFINITY, true);
			MapUtils.checkForCriticalValue(interestingnessScores, Double.POSITIVE_INFINITY, true);
		}

		// normalize
		return MapUtils.normalizeValuesMinMax(interestingnessScores);
	}

	protected abstract double calculateRelevanceScore(LabelDistribution labelDistribution);

	@Override
	public String getDescription() {
		return getName();
	}

	@Override
	public String toString() {
		return getName();
	}
}