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
 * Copyright: (c) 2017-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 */
public class RandomForest extends WekaClassifierWrapper<NumericalFeatureVector> {

	/**
	 * The number of trees to be performed.
	 */
	private int trees = 10;

	/**
	 * number of randomly chosen attributes. If 0, int(log_2(#predictors) + 1) is
	 * used.
	 */
	private int numFeatures = 0;

	/**
	 * maximum depth of the tree, 0 for unlimited.
	 */
	private int depthMax = 0;

	public RandomForest() {

	}

	@Override
	protected void initializeClassifier() {
		setWekaClassifier(new weka.classifiers.trees.RandomForest());

		List<String> aryOpts = new ArrayList<String>();
		aryOpts.add("-I");
		aryOpts.add(trees + "");
		aryOpts.add("-K");
		aryOpts.add(numFeatures + "");
		aryOpts.add("-S");
		aryOpts.add(1 + "");
		aryOpts.add("-depth");
		aryOpts.add(depthMax + "");

		String[] opts = aryOpts.toArray(new String[aryOpts.size()]);

		try {
			((weka.classifiers.trees.RandomForest) getWekaClassifier()).setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getTreeCount() {
		return trees;
	}

	public void setTreeCount(int trees) {
		this.trees = trees;

		initializeClassifier();
	}

	public int getNumFeatures() {
		return numFeatures;
	}

	public void setNumFeatures(int numFeatures) {
		this.numFeatures = numFeatures;

		initializeClassifier();
	}

	public int getDepthMax() {
		return depthMax;
	}

	public void setDepthMax(int depthMax) {
		this.depthMax = depthMax;

		initializeClassifier();
	}
}
