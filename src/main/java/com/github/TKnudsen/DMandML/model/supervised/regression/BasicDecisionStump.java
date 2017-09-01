package com.github.TKnudsen.DMandML.model.supervised.regression;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

/**
 * <p>
 * Title: DecisionStump
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author Christian Ritter
 * @version 1.02
 */
public class BasicDecisionStump<O, FV extends AbstractFeatureVector<O, ? extends Feature<O>>> extends WekaRegressionWrapper<O, FV> {

	private boolean debug = false;

	@Override
	public String getDescription() {
		return "WEKA's implementation of the DecisionStump regression algorithm.";
	}

	@Override
	public String getName() {
		return "DecisionStump";
	}

	/**
	 * @return the doDebug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @param doDebug
	 *            the doDebug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@Override
	protected void initializeRegression() {
		wekaRegressionModel = new weka.classifiers.trees.DecisionStump();

		List<String> aryOpts = new ArrayList<String>();

		if (isDebug())
			aryOpts.add("-D"); // Enables debug mode

		String[] opts = aryOpts.toArray(new String[aryOpts.size()]);

		try {
			wekaRegressionModel.setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
