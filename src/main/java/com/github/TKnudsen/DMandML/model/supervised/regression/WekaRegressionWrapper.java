package main.java.com.github.TKnudsen.DMandML.model.supervised.regression;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;
import com.github.TKnudsen.ComplexDataObject.model.tools.WekaConversion;

import weka.classifiers.AbstractClassifier;
import weka.core.Attribute;
import weka.core.Instances;

/**
 * <p>
 * Title: WekaRegressionWrapper
 * </p>
 * 
 * <p>
 * Description: basic algorithmic model that wraps regression models from Weka.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public abstract class WekaRegressionWrapper<O extends Object, FV extends AbstractFeatureVector<O, ? extends Feature<O>>> extends Regression<O, FV> {

	protected AbstractClassifier wekaRegressionModel;

	protected Instances instances;

	@Override
	protected void prepareData() {
		if (getTargetValues() != null)
			instances = WekaConversion.getLabeledInstances(trainFeatureVectors, getTargetValues());
		else if (getTargetAttribute() != null) {
			instances = WekaConversion.getInstances(trainFeatureVectors, false);

			Attribute attribute = instances.attribute(getTargetAttribute());

			instances.setClass(attribute);
			System.out.println(instances.classIndex());
		} else
			throw new NullPointerException("WekaRegressionWrapper: no target variable defined.");
	}

	@Override
	protected void buildRegression() {
		if (instances == null)
			throw new NullPointerException("need to initialize data instances first.");

		try {
			wekaRegressionModel.buildClassifier(instances);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void resetResults() {
		wekaRegressionModel = null;
	}

	// @Override
	// public void train(List<NumericalFeatureVector> featureVectors,
	// List<Double> labels) {
	// setTrainFeatureVectors(featureVectors);
	//
	// instances = WekaConversion.getLabeledInstances(featureVectors, labels);
	//
	// buildRegressionModel();
	// }
	//
	// @Override
	// public void train(List<NumericalFeatureVector> featureVectors, String
	// targetVariable) {
	// setTrainFeatureVectors(featureVectors);
	//
	// Attribute attribute = instances.attribute(targetVariable);
	//
	// instances.setClass(attribute);
	// System.out.println(instances.classIndex());
	//
	// buildRegressionModel();
	// }

	@Override
	public List<Double> test(List<FV> featureVectors) {

		Instances testData = WekaConversion.getInstances(featureVectors, false);
		if (testData.size() != featureVectors.size())
			throw new IllegalArgumentException("WekaConversion failed");

		testData = WekaConversion.addLabelAttributeToInstance(testData, "class", null);
		if (testData.size() != testData.size())
			throw new IllegalArgumentException("WekaConversion failed");

		List<Double> values = new ArrayList<>();

		for (int i = 0; i < testData.size(); i++)
			try {
				values.add(wekaRegressionModel.classifyInstance(testData.get(i)));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		return values;
	}
}
