package com.github.TKnudsen.DMandML.model.supervised.classifier.impl;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.DMandML.model.supervised.classifier.WekaClassifierWrapper;

import weka.classifiers.meta.AdaBoostM1;

/**
 * <p>
 * Title: BasicAdaBoost
 * </p>
 * 
 * <p>
 * Description: A generic wrapper class for weka's AdaBoost implementation.
 * </p>
 * 
 * @author Christian Ritter
 * @version 1.02
 * 
 */
public class BasicAdaBoost<FV extends IFeatureVectorObject<?, ?>> extends WekaClassifierWrapper<FV> {

	private String baseClassifier = "weka.classifiers.trees.DecisionStump";

	private boolean debug = false;

	private int numberOfIterations = 10;

	private int randomSeed = 1;

	private boolean useResampling = false;

	private int weightMassPercentage = 100;

	/**
	 * @return the baseClassifier
	 */
	public String getBaseClassifier() {
		return baseClassifier;
	}

	/**
	 * @return the numberOfIterations
	 */
	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	/**
	 * @return the randomSeed
	 */
	public int getRandomSeed() {
		return randomSeed;
	}

	/**
	 * @return the weightMassPercentage
	 */
	public int getWeightMassPercentage() {
		return weightMassPercentage;
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @return the useResampling
	 */
	public boolean isUseResampling() {
		return useResampling;
	}

	/**
	 * @param baseClassifier
	 *            the baseClassifier to set
	 */
	public void setBaseClassifier(String baseClassifier) {
		this.baseClassifier = baseClassifier;
	}

	/**
	 * @param debug
	 *            the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @param numberOfIterations
	 *            the numberOfIterations to set
	 */
	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	/**
	 * @param randomSeed
	 *            the randomSeed to set
	 */
	public void setRandomSeed(int randomSeed) {
		this.randomSeed = randomSeed;
	}

	/**
	 * @param useResampling
	 *            the useResampling to set
	 */
	public void setUseResampling(boolean useResampling) {
		this.useResampling = useResampling;
	}

	/**
	 * @param weightMassPercentage
	 *            the weightMassPercentage to set
	 */
	public void setWeightMassPercentage(int weightMassPercentage) {
		this.weightMassPercentage = weightMassPercentage;
	}

	@Override
	protected void initializeClassifier() {
		setWekaClassifier(new AdaBoostM1());

		List<String> aryOpts = new ArrayList<String>();

		aryOpts.add("-P");
		aryOpts.add(String.valueOf(getWeightMassPercentage()));

		aryOpts.add("-S");
		aryOpts.add(String.valueOf(getRandomSeed()));

		aryOpts.add("-I");
		aryOpts.add(String.valueOf(getNumberOfIterations()));

		aryOpts.add("-W");
		aryOpts.add(String.valueOf(getBaseClassifier()));

		if (isUseResampling())
			aryOpts.add("-Q");

		if (isDebug())
			aryOpts.add("-D");

		String[] opts = aryOpts.toArray(new String[aryOpts.size()]);

		try {
			((AdaBoostM1) getWekaClassifier()).setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}