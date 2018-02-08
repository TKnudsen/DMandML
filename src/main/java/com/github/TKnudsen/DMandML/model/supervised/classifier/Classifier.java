package com.github.TKnudsen.DMandML.model.supervised.classifier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@JsonIgnore
	protected List<FV> trainFeatureVectors;

	/**
	 * the attribute that will be looked up in the feature vectors. Note that this
	 * is an attribute in the features. not a feature in itself.
	 * 
	 * Similarly, the class attribute, e.g., in WEKA will always be "class" instead
	 * of classAttribute.
	 */
	protected String classAttribute = "class";

	/**
	 * "set" of unique labels.
	 */
	protected List<String> labelAlphabet;

	protected abstract void initializeClassifier();

	protected abstract void prepareData();

	protected abstract void resetResults();

	/**
	 * allows individual execution of classifiers
	 */
	protected abstract void buildClassifier();

	public abstract List<Map<String, Double>> getLabelDistributionResult();

	@Override
	public void train(List<FV> featureVectors, List<String> labels) {
		if (featureVectors == null || labels == null)
			throw new NullPointerException();
		if (featureVectors.size() != labels.size())
			throw new IllegalArgumentException();

		this.trainFeatureVectors = new ArrayList<>(featureVectors);
		for (int i = 0; i < featureVectors.size(); i++)
			trainFeatureVectors.get(i).add(classAttribute, labels.get(i));

		train(featureVectors, classAttribute);
	}

	@Override
	public void train(List<FV> featureVectors, String targetVariable) {
		if (featureVectors == null)
			throw new NullPointerException();

		this.trainFeatureVectors = new ArrayList<>(featureVectors);
		this.classAttribute = targetVariable;

		Set<String> labels = new LinkedHashSet<>();
		for (int i = 0; i < featureVectors.size(); i++)
			if (trainFeatureVectors.get(i) != null)
				if (trainFeatureVectors.get(i).getAttribute(targetVariable) != null)
					labels.add(trainFeatureVectors.get(i).getAttribute(targetVariable).toString());
		this.labelAlphabet = new ArrayList<>(labels);

		initializeClassifier();

		prepareData();

		resetResults();

		buildClassifier();
	}

	@Override
	public IProbabilisticClassificationResult<FV> createClassificationResult(List<FV> featureVectors) {
		Map<FV, Map<String, Double>> labelDistributionMap = new LinkedHashMap<>();
		for (FV fv : featureVectors)
			labelDistributionMap.put(fv, getLabelDistribution(fv));

		IProbabilisticClassificationResult<FV> result = new ProbabilisticClassificationResult<>(labelDistributionMap);

		return result;
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

	public String getClassAttribute() {
		return classAttribute;
	}

	public void setClassAttribute(String classAttribute) {
		this.classAttribute = classAttribute;
	}

	public List<FV> getTrainFeatureVectors() {
		return trainFeatureVectors;
	}

	public void setTrainFeatureVectors(List<FV> trainFeatureVectors) {
		this.trainFeatureVectors = trainFeatureVectors;
	}

	@Override
	public List<String> getLabelAlphabet() {
		return labelAlphabet;
	}
}
