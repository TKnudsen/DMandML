package com.github.TKnudsen.DMandML.model.supervised.classifier.numericalFeatures;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.model.supervised.classifier.WekaClassifierWrapper;

/**
 * <p>
 * Title: RandomForest
 * </p>
 * 
 * <p>
 * Description: RandomForest implementation for numerical features. Based on
 * WEKA's RandomForest.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class RandomForest extends WekaClassifierWrapper<Double, NumericalFeatureVector> {

	/**
	 * The number of iterations to be performed.
	 */
	private int numIterations = 100;

	/**
	 * number of randomly chosen attributes. If 0, int(log_2(#predictors) + 1)
	 * is used.
	 */
	private int numFeatures = 0;

	public RandomForest() {

	}

	@Override
	protected void initializeClassifier() {
		this.wekaClassifier = new weka.classifiers.trees.RandomForest();

		List<String> aryOpts = new ArrayList<String>();
		aryOpts.add("-I");
		aryOpts.add(numIterations + "");
		aryOpts.add("-K");
		aryOpts.add(numFeatures + "");
		aryOpts.add("-S");
		aryOpts.add(1 + "");

		String[] opts = aryOpts.toArray(new String[aryOpts.size()]);

		try {
			((weka.classifiers.trees.RandomForest) getWekaClassifier()).setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resetResults();
	}

	public int getNumIterations() {
		return numIterations;
	}

	public void setNumIterations(int numIterations) {
		this.numIterations = numIterations;

		initializeClassifier();
	}

	public int getNumFeatures() {
		return numFeatures;
	}

	public void setNumFeatures(int numFeatures) {
		this.numFeatures = numFeatures;

		initializeClassifier();
	}
}
