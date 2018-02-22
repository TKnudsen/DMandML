package com.github.TKnudsen.DMandML.model.supervised.classifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IKeyValueProvider;
import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResult;
import com.github.TKnudsen.DMandML.data.classification.ProbabilisticClassificationResult;

/**
 * <p>
 * Title: Classifier
 * </p>
 * 
 * <p>
 * Description: abstract basic class for all classifiers. The decision was made
 * to have the probabilistic classifier interface as default.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 * 
 */
public abstract class Classifier<FV extends IKeyValueProvider<Object>> implements IProbabilisticClassifier<FV> {

	/**
	 * The "set" of unique labels.
	 */
	private final List<String> labelAlphabet;

	private final String classAttribute;

	protected Classifier(String classAttribute) {
		this.labelAlphabet = new ArrayList<>();
		this.classAttribute = Objects.requireNonNull(classAttribute, "The classAttribute may not be null");
	}

	@Override
	public void train(List<FV> featureVectors) {
		Objects.requireNonNull(featureVectors, "The featureVectors may not be null");

		updateLabelAlphabet(featureVectors);
		buildClassifier(featureVectors);
	}

	/**
	 * The method containing the implementation of the classifier training. It is
	 * called in {@link #train(List)}, after the {@link #getLabelAlphabet() label
	 * alphabet} has been updated
	 */
	protected abstract void buildClassifier(List<FV> featureVectors);

	/**
	 * Updates the {@link #labelAlphabet} by collecting all unique values of all
	 * values of the {@link #classAttribute} of the given feature vectors.
	 * 
	 * @param featureVectors
	 *            The feature vectors
	 */
	protected final void updateLabelAlphabet(Iterable<? extends FV> featureVectors) {
		this.labelAlphabet.clear();

		Set<String> labels = new LinkedHashSet<>();
		for (FV fv : featureVectors) {
			Object classAttributeValue = fv.getAttribute(classAttribute);
			if (classAttributeValue == null) {
				System.err.println(
						"com.github.TKnudsen.DMandML.model.supervised.classifier.Classifier#updateLabelAlphabet: "
								+ "There is no attribute value for class attribute " + classAttribute + " in " + fv);
			} else {
				labels.add(String.valueOf(classAttributeValue));
			}
		}
		this.labelAlphabet.clear();
		this.labelAlphabet.addAll(labels);
	}

	@Override
	public IProbabilisticClassificationResult<FV> createClassificationResult(List<FV> featureVectors) {
		Map<FV, Map<String, Double>> labelDistributionMap = new LinkedHashMap<>();
		for (FV fv : featureVectors) {
			labelDistributionMap.put(fv, getLabelDistribution(fv));
		}
		return new ProbabilisticClassificationResult<>(labelDistributionMap);
	}

	@Override
	public List<String> getLabelAlphabet() {
		return Collections.unmodifiableList(labelAlphabet);
	}

	@Override
	public String getClassAttribute() {
		return classAttribute;
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String getDescription() {
		return this.getClass().getCanonicalName();
	}

	@Override
	public String toString() {
		return getName();
	}

}
