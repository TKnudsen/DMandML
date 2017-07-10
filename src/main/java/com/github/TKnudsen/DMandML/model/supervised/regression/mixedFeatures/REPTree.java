package main.java.com.github.TKnudsen.DMandML.model.supervised.regression.mixedFeatures;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.mixedData.MixedDataFeatureVector;

import main.java.com.github.TKnudsen.DMandML.model.supervised.regression.WekaRegressionWrapper;

/**
 * <p>
 * Title: REPTree
 * </p>
 * 
 * <p>
 * Description: Weka's REPTree algorithm. Used for mixed feature vectors.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class REPTree extends WekaRegressionWrapper<Object, MixedDataFeatureVector> {

	private int minimumInstancesPerLeaf = 2;

	private double minimumNumericVarianceProportionForSplit = 1e-3;

	private int foldCount = 3;

	private int seed = 1;

	private boolean prune = true;

	private int maximumTreeDepth = -1;

	@Override
	protected void initializeRegression() {

		wekaRegressionModel = new weka.classifiers.trees.REPTree();

		List<String> aryOpts = new ArrayList<String>();

		// Valid options are:
		//
		aryOpts.add("-M"); // <minimum number of instances>
		aryOpts.add(minimumInstancesPerLeaf + "");
		// Set minimum number of instances per leaf (default 2).
		//
		//
		aryOpts.add("-V"); // <minimum variance for split>
		aryOpts.add(minimumNumericVarianceProportionForSplit + "");
		// Set minimum numeric class variance proportion
		// of train variance for split (default 1e-3).
		//
		//
		aryOpts.add("-N"); // <number of folds>
		aryOpts.add(foldCount + "");
		// Number of folds for reduced error pruning (default 3).
		//
		//
		aryOpts.add("-S"); // <seed>
		aryOpts.add(seed + "");
		// Seed for random data shuffling (default 1).
		//
		//
		if (!prune)
			aryOpts.add("-P");
		// -P
		// No pruning.
		//
		//
		aryOpts.add("-L"); // -L
		aryOpts.add(maximumTreeDepth + "");
		// Maximum tree depth (default -1, no maximum)

		String[] opts = aryOpts.toArray(new String[aryOpts.size()]);

		try {
			wekaRegressionModel.setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return "REPTree";
	}

	@Override
	public String getDescription() {
		return "WEKA's implementation of the REPTree regression algorithm.";
	}

	public int getMinimumInstancesPerLeaf() {
		return minimumInstancesPerLeaf;
	}

	public void setMinimumInstancesPerLeaf(int minimumInstancesPerLeaf) {
		this.minimumInstancesPerLeaf = minimumInstancesPerLeaf;

		resetResults();
		initializeRegression();
	}

	public double getMinimumNumericVarianceProportionForSplit() {
		return minimumNumericVarianceProportionForSplit;
	}

	public void setMinimumNumericVarianceProportionForSplit(double minimumNumericVarianceProportionForSplit) {
		this.minimumNumericVarianceProportionForSplit = minimumNumericVarianceProportionForSplit;

		resetResults();
		initializeRegression();
	}

	public int getFoldCount() {
		return foldCount;
	}

	public void setFoldCount(int foldCount) {
		this.foldCount = foldCount;

		resetResults();
		initializeRegression();
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;

		resetResults();
		initializeRegression();
	}

	public boolean isPrune() {
		return prune;
	}

	public void setPrune(boolean prune) {
		this.prune = prune;

		resetResults();
		initializeRegression();
	}

	public int getMaximumTreeDepth() {
		return maximumTreeDepth;
	}

	public void setMaximumTreeDepth(int maximumTreeDepth) {
		this.maximumTreeDepth = maximumTreeDepth;

		resetResults();
		initializeRegression();
	}

}
