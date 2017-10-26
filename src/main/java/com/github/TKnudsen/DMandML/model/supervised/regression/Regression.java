package com.github.TKnudsen.DMandML.model.supervised.regression;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

/**
 * <p>
 * Title: Regression
 * </p>
 * 
 * <p>
 * Description: basic algorithmic model for regression tasks.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 */
public abstract class Regression<O, FV extends AbstractFeatureVector<O, ? extends Feature<O>>> implements IRegression<O, FV> {

	@JsonIgnore
	protected List<FV> trainFeatureVectors;

	/**
	 * the attribute that will be looked up in the feature vectors. Note that
	 * this is an attribute in the features. not a feature in itself.
	 * 
	 * Similarly, the class attribute, e.g., in WEKA will always be "class"
	 * instead of classAttribute.
	 */
	protected String targetAttribute = "class";

	private List<Double> targetValues;

	protected abstract void initializeRegression();

	protected abstract void prepareData();

	/**
	 * allows individual execution of regression models
	 */
	protected abstract void buildRegression();

	protected abstract void resetResults();

	@Override
	public void train(List<FV> featureVectors, List<Double> targetValues) {
		resetResults();
		
		setTrainFeatureVectors(featureVectors);

		setTargetValues(targetValues);

		initializeRegression();

		prepareData();

		buildRegression();
	}

	@Override
	public void train(List<FV> featureVectors, String targetVariable) {
		resetResults();
		
		setTrainFeatureVectors(featureVectors);

		setTargetAttribute(targetVariable);

		initializeRegression();

		prepareData();

		buildRegression();
	}

	public String getTargetAttribute() {
		return targetAttribute;
	}

	public void setTargetAttribute(String classAttribute) {
		this.targetAttribute = classAttribute;

		resetResults();
	}

	public List<FV> getTrainFeatureVectors() {
		return trainFeatureVectors;
	}

	public void setTrainFeatureVectors(List<FV> trainFeatureVectors) {
		this.trainFeatureVectors = trainFeatureVectors;

		resetResults();
	}

	public List<Double> getTargetValues() {
		return targetValues;
	}

	public void setTargetValues(List<Double> targetValues) {
		this.targetValues = targetValues;
	}
}
