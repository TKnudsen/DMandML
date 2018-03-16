package com.github.TKnudsen.DMandML.model.supervised.classifier.impl.numericalFeatures;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.model.supervised.classifier.WekaClassifierWrapper;

/**
 * <p>
 * Title: BayesNet
 * </p>
 * 
 * <p>
 * Description: Bayes Network learning using various search algorithms and
 * quality measures.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class BayesNet extends WekaClassifierWrapper<NumericalFeatureVector> {

	private boolean neglectADTree = false;

	public BayesNet() {

	}

	public BayesNet(boolean neglectADTree) {
		this.neglectADTree = neglectADTree;
	}

	@Override
	protected void initializeClassifier() {
		setWekaClassifier(new weka.classifiers.bayes.BayesNet());

		List<String> aryOpts = new ArrayList<String>();

		if (neglectADTree)
			aryOpts.add("-D");

		String[] opts = aryOpts.toArray(new String[aryOpts.size()]);

		try {
			((weka.classifiers.bayes.BayesNet) getWekaClassifier()).setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isNeglectADTree() {
		return neglectADTree;
	}

	public void setNeglectADTree(boolean neglectADTree) {
		this.neglectADTree = neglectADTree;

		initializeClassifier();
	}

}
