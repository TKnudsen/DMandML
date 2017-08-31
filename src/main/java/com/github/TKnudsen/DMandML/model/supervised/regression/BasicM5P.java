package main.java.com.github.TKnudsen.DMandML.model.supervised.regression;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

/**
 * <p>
 * Title: M5Base
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author Christian Ritter
 * @version 1.02
 */
public class BasicM5P<O, FV extends AbstractFeatureVector<O, ? extends Feature<O>>> extends WekaRegressionWrapper<O, FV> {

	private int minimumNumberOfInstances = 4;
	private boolean safeInstances = false;
	private boolean useUnprunedTree = false;
	private boolean useUnsmoothedPredictions = false;

	@Override
	public String getDescription() {
		return "WEKA's implementation of the M5P regression algorithm.";
	}

	/**
	 * @return the minimumNumberOfInstances
	 */
	public int getMinimumNumberOfInstances() {
		return minimumNumberOfInstances;
	}

	@Override
	public String getName() {
		return "M5P";
	}

	/**
	 * @return the safeInstances
	 */
	public boolean isSafeInstances() {
		return safeInstances;
	}

	/**
	 * @return the useUnprunedTree
	 */
	public boolean isUseUnprunedTree() {
		return useUnprunedTree;
	}

	/**
	 * @return the useUnsmoothedPredictions
	 */
	public boolean isUseUnsmoothedPredictions() {
		return useUnsmoothedPredictions;
	}

	/**
	 * @param minimumNumberOfInstances the minimumNumberOfInstances to set
	 */
	public void setMinimumNumberOfInstances(int minimumNumberOfInstances) {
		this.minimumNumberOfInstances = minimumNumberOfInstances;
	}

	/**
	 * @param safeInstances the safeInstances to set
	 */
	public void setSafeInstances(boolean safeInstances) {
		this.safeInstances = safeInstances;
	}

	/**
	 * @param useUnprunedTree the useUnprunedTree to set
	 */
	public void setUseUnprunedTree(boolean useUnprunedTree) {
		this.useUnprunedTree = useUnprunedTree;
	}

	/**
	 * @param useUnsmoothedPredictions the useUnsmoothedPredictions to set
	 */
	public void setUseUnsmoothedPredictions(boolean useUnsmoothedPredictions) {
		this.useUnsmoothedPredictions = useUnsmoothedPredictions;
	}

	@Override
	protected void initializeRegression() {
		wekaRegressionModel = new weka.classifiers.trees.M5P();

		List<String> aryOpts = new ArrayList<String>();

		if (isUseUnprunedTree())
			aryOpts.add("-N"); // Use unpruned tree/rules

		if (isUseUnsmoothedPredictions())
			aryOpts.add("-U"); // Use unsmoothed predictions

		aryOpts.add("-R"); // Build regression tree/rule rather than a model tree/rule

		aryOpts.add("-M"); // Set minimum number of instances per leaf (default 4)
		aryOpts.add(String.valueOf(getMinimumNumberOfInstances()));

		if (isSafeInstances())
			aryOpts.add("-L"); // Save instances at the nodes in the tree (for visualization purposes)

		String[] opts = aryOpts.toArray(new String[aryOpts.size()]);

		try {
			wekaRegressionModel.setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
