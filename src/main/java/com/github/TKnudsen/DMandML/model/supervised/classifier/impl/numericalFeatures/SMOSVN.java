package com.github.TKnudsen.DMandML.model.supervised.classifier.impl.numericalFeatures;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.model.supervised.classifier.WekaClassifierWrapper;

import weka.classifiers.functions.SMO;

/**
 * <p>
 * Title: SMOSVN
 * </p>
 * 
 * <p>
 * Description: Implements John C. Platt's sequential minimal optimization
 * algorithm for training a support vector classifier using polynomial or RBF
 * kernels.
 * 
 * J. Platt (1998). Fast Training of Support Vector Machines using Sequential
 * Minimal Optimization. Advances in Kernel Methods - Support Vector Learning,
 * B. Schölkopf, C. Burges, and A. Smola, eds., MIT Press.
 * 
 * S.S. Keerthi, S.K. Shevade, C. Bhattacharyya, K.R.K. Murthy, Improvements to
 * Platt's SMO Algorithm for SVM Classifier Design. Neural Computation, 13(3),
 * pp 637-649, 2001.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * 
 * @version 1.01
 * 
 */
public class SMOSVN extends WekaClassifierWrapper<NumericalFeatureVector> {

	/**
	 * The complexity parameter C.
	 */
	private double complexityParameter = 1.0;

	/**
	 * The exponent for the polynomial kernel. (default 1)
	 */
	private double polynomialKernelExponent = 1.0;

	/**
	 * Gamma for the RBF kernel. (default 0.01)
	 */
	private double gammaForKernel = 0.01;

	/**
	 * -N <0|1|2> Whether to 0=normalize/1=standardize/2=neither. (default
	 * 0=normalize)
	 */
	private int normalizeType = 0;

	/**
	 * Feature-space normalization (only for non-linear polynomial kernels).
	 */
	private boolean featureSpaceNormalization = false;

	/**
	 * Use lower-order terms (only for non-linear polynomial kernels).
	 */
	private boolean useLowerOrderTerms = false;

	/**
	 * Use the RBF kernel. (default poly)
	 */
	private boolean useRBGKernel = false;

	/**
	 * The number of folds for cross-validation used to generate training data for
	 * logistic models (-1 means use training data).
	 */
	private int numFolds = -1;

	/**
	 * The tolerance parameter (shouldn't be changed).
	 */
	private double toleranceParameter = 0.001;

	/**
	 * Fit logistic models to SVM outputs.
	 */
	private boolean fitLogisticModelsToSVMOutput = true;

	@Override
	protected void initializeClassifier() {
		setWekaClassifier(new weka.classifiers.functions.SMO());

		List<String> aryOpts = new ArrayList<String>();
		aryOpts.add("-C");
		aryOpts.add(complexityParameter + "");

		aryOpts.add("-E");
		aryOpts.add(polynomialKernelExponent + "");

		aryOpts.add("-G");
		aryOpts.add(gammaForKernel + "");

		aryOpts.add("-N");
		aryOpts.add(normalizeType + "");

		if (featureSpaceNormalization)
			aryOpts.add("-F");

		if (useLowerOrderTerms)
			aryOpts.add("-O");

		if (useRBGKernel)
			aryOpts.add("-R");

		aryOpts.add("-P");
		aryOpts.add("1.0E-12");

		aryOpts.add("-V");
		aryOpts.add(numFolds + "");

		aryOpts.add("-T");
		aryOpts.add(toleranceParameter + "");

		if (fitLogisticModelsToSVMOutput)
			aryOpts.add("-M");

		// The Kernel to use.
		// aryOpts.add("-K");
		// aryOpts.add("weka.classifiers.functions.supportVector.PolyKernel -C
		// 250007 -E 1.0 ");

		String[] opts = aryOpts.toArray(new String[aryOpts.size()]);

		try {
			((SMO) getWekaClassifier()).setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getPolynomialKernelExponent() {
		return polynomialKernelExponent;
	}

	public void setPolynomialKernelExponent(double polynomialKernelExponent) {
		this.polynomialKernelExponent = polynomialKernelExponent;

		initializeClassifier();
	}

	public double getComplexityParameter() {
		return complexityParameter;
	}

	public void setComplexityParameter(double complexityParameter) {
		this.complexityParameter = complexityParameter;

		initializeClassifier();
	}

	public double getGammaForKernel() {
		return gammaForKernel;
	}

	public void setGammaForKernel(double gammaForKernel) {
		this.gammaForKernel = gammaForKernel;

		initializeClassifier();
	}

	public int getNormalizeType() {
		return normalizeType;
	}

	public void setNormalizeType(int normalizeType) {
		this.normalizeType = normalizeType;

		initializeClassifier();
	}

	public boolean isFeatureSpaceNormalization() {
		return featureSpaceNormalization;
	}

	public void setFeatureSpaceNormalization(boolean featureSpaceNormalization) {
		this.featureSpaceNormalization = featureSpaceNormalization;

		initializeClassifier();
	}

	public boolean isUseLowerOrderTerms() {
		return useLowerOrderTerms;
	}

	public void setUseLowerOrderTerms(boolean useLowerOrderTerms) {
		this.useLowerOrderTerms = useLowerOrderTerms;

		initializeClassifier();
	}

	public boolean isUseRBGKernel() {
		return useRBGKernel;
	}

	public void setUseRBGKernel(boolean useRBGKernel) {
		this.useRBGKernel = useRBGKernel;

		initializeClassifier();
	}

	public int getNumFolds() {
		return numFolds;
	}

	public void setNumFolds(int numFolds) {
		this.numFolds = numFolds;

		initializeClassifier();
	}

	public double getToleranceParameter() {
		return toleranceParameter;
	}

	public void setToleranceParameter(double toleranceParameter) {
		this.toleranceParameter = toleranceParameter;

		initializeClassifier();
	}

	public boolean isFitLogisticModelsToSVMOutput() {
		return fitLogisticModelsToSVMOutput;
	}

	public void setFitLogisticModelsToSVMOutput(boolean fitLogisticModelsToSVMOutput) {
		this.fitLogisticModelsToSVMOutput = fitLogisticModelsToSVMOutput;

		initializeClassifier();
	}
}