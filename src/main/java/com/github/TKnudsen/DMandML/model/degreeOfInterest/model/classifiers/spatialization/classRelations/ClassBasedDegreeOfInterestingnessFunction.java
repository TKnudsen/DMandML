package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations;

import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
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
 * Degree-of-interest functions based on classification results. The majority of
 * implementations exploits the output of probabilistic classifiers, thus taking
 * the likelihood of an instance for any class into account.
 * </p>
 * 
 * Builds upon the Class Likelihood (CL) DOI/building block published in:
 * Juergen Bernard, Matthias Zeppelzauer, Markus Lehmann, Martin Mueller, and
 * Michael Sedlmair: Towards User-Centered Active Learning Algorithms.
 * Eurographics Conference on Visualization (EuroVis), Computer Graphics Forum
 * (CGF), 2018.
 * </p>
 * 
 * @author Juergen Bernard
 * 
 * @version 1.03
 */
public abstract class ClassBasedDegreeOfInterestingnessFunction<FV>
		extends ClassificationBasedInterestingnessFunction<FV> {

	public ClassBasedDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction) {
		super(classificationApplicationFunction);
	}

	public ClassBasedDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction, String classifierName) {
		super(classificationApplicationFunction, classifierName);
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> featureVectors) {

		Objects.requireNonNull(featureVectors);

		IClassificationResult<FV> classificationResult = computeClassificationResult(featureVectors);

		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();

		if (classificationResult == null || classificationResult.getClassDistributions() == null) {
			for (FV fv : featureVectors)
				interestingnessScores.put(fv, 0.0);

			return interestingnessScores;
		}

		if (featureVectors.size() == 0)
			return interestingnessScores;

		for (FV fv : featureVectors) {
			Double score = calculateInterestingnessScore(fv, classificationResult);
			if (score == null || Double.isNaN(score)) {
				System.err.println(getName() + "DOI produced");
				throw new NullPointerException(getName() + "Routine produced ill-defined score");
			}
			interestingnessScores.put(fv, score);
		}

		// for validation purposes
		MapUtils.checkForCriticalValue(interestingnessScores, null, true);
		MapUtils.checkForCriticalValue(interestingnessScores, Double.NaN, true);
		MapUtils.checkForCriticalValue(interestingnessScores, Double.NEGATIVE_INFINITY, true);
		MapUtils.checkForCriticalValue(interestingnessScores, Double.POSITIVE_INFINITY, true);

		NormalizationFunction normalizationFunction = new LinearNormalizationFunction(interestingnessScores.values());
		for (FV fv : interestingnessScores.keySet())
			interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

		return interestingnessScores;
	}

	/**
	 * @param fv
	 * @param classificationResult
	 * @return
	 */
	abstract protected Double calculateInterestingnessScore(FV fv, IClassificationResult<FV> classificationResult);

}
