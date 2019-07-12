package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers;

import java.util.List;
import java.util.Objects;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * 
 * Base class for degree of interest functions based on classification results.
 * The majority of implementations exploits the output of probabilistic
 * classifiers, thus taking the likelihood of an instance for any class into
 * account.
 * </p>
 * 
 * Builds upon the Class Likelihood (CL) DOI/building block (probability
 * distribution per instance) and the Class Prediction (CP) DOI/building block
 * (winning label per instance) published in: Juergen Bernard, Matthias
 * Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair: Towards
 * User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.03
 */

public abstract class ClassificationBasedInterestingnessFunction<FV> implements IDegreeOfInterestFunction<FV> {

	private String classifierName;

	private final IClassificationApplicationFunction<FV> classificationApplicationFunction;

	public ClassificationBasedInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction) {
		this(classificationApplicationFunction, null);
	}

	public ClassificationBasedInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction, String classifierName) {
		this.classificationApplicationFunction = Objects.requireNonNull(classificationApplicationFunction,
				"The classificationResult may not be null");

		this.classifierName = classifierName;
	}

	// @Override
	// public Map<FV, Double> apply(List<? extends FV> featureVectors) {
	// // TODO it may make sense to divide this functionality in three parts
	// // P1: initialization - always the same, can be applied here
	// // P2: scoring function - can be abstracted to inheriting classes
	// // P3: post processing of scores map (e.g. normalization, value inversion,
	// etc.)
	// }

	protected final IClassificationResult<FV> computeClassificationResult(List<? extends FV> featureVectors) {
		return classificationApplicationFunction.apply(featureVectors);
	}

	public String getClassifierName() {
		return classifierName;
	}

	public void setClassifierName(String classifierName) {
		this.classifierName = classifierName;
	}

}
