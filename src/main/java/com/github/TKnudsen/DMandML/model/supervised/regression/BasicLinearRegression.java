package main.java.com.github.TKnudsen.DMandML.model.supervised.regression;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

/**
 * <p>
 * Title: LinearRegression
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author Christian Ritter
 * @version 1.02
 */
public class BasicLinearRegression<O, FV extends AbstractFeatureVector<O, ? extends Feature<O>>> extends WekaRegressionWrapper<O, FV> {

	private boolean additionalStats = false;
	private boolean conserveMemory = false;
	private boolean debug = false;
	private boolean doNotCheckClassifierCapabilities = false;
	private boolean eliminateColinearAttributes = false;
	private double ridgeParameter = 1.0e-8;
	private int selectionMethod = 0;

	@Override
	public String getDescription() {
		return "WEKA's implementation of the LinearRegression regression algorithm.";
	}

	@Override
	public String getName() {
		return "LinearRegression";
	}

	/**
	 * @return the ridgeParameter
	 */
	public double getRidgeParameter() {
		return ridgeParameter;
	}

	/**
	 * @return the selectionMethod
	 */
	public int getSelectionMethod() {
		return selectionMethod;
	}

	/**
	 * @return the additionalStats
	 */
	public boolean isAdditionalStats() {
		return additionalStats;
	}

	/**
	 * @return the conserveMemory
	 */
	public boolean isConserveMemory() {
		return conserveMemory;
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @return the doNotCheckClassifierCapabilities
	 */
	public boolean isDoNotCheckClassifierCapabilities() {
		return doNotCheckClassifierCapabilities;
	}

	/**
	 * @return the eliminateColinearAttributes
	 */
	public boolean isEliminateColinearAttributes() {
		return eliminateColinearAttributes;
	}

	/**
	 * @param additionalStats
	 *            the additionalStats to set
	 */
	public void setAdditionalStats(boolean additionalStats) {
		this.additionalStats = additionalStats;
	}

	/**
	 * @param conserveMemory the conserveMemory to set
	 */
	public void setConserveMemory(boolean conserveMemory) {
		this.conserveMemory = conserveMemory;
	}

	/**
	 * @param debug
	 *            the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @param doNotCheckClassifierCapabilities
	 *            the doNotCheckClassifierCapabilities to set
	 */
	public void setDoNotCheckClassifierCapabilities(boolean doNotCheckClassifierCapabilities) {
		this.doNotCheckClassifierCapabilities = doNotCheckClassifierCapabilities;
	}

	/**
	 * @param eliminateColinearAttributes
	 *            the eliminateColinearAttributes to set
	 */
	public void setEliminateColinearAttributes(boolean eliminateColinearAttributes) {
		this.eliminateColinearAttributes = eliminateColinearAttributes;
	}

	/**
	 * @param ridgeParameter
	 *            the ridgeParameter to set
	 */
	public void setRidgeParameter(double ridgeParameter) {
		this.ridgeParameter = ridgeParameter;
	}

	/**
	 * @param selectionMethod
	 *            the selectionMethod to set
	 */
	public void setSelectionMethod(int selectionMethod) {
		this.selectionMethod = selectionMethod;
	}

	@Override
	protected void initializeRegression() {
		wekaRegressionModel = new weka.classifiers.functions.LinearRegression();

		List<String> aryOpts = new ArrayList<String>();

		aryOpts.add("-S"); // Attribute selection method: 1 = None, 2 = Greedy (default 0 = M5' method)
		aryOpts.add(String.valueOf(getSelectionMethod()));

		if (isEliminateColinearAttributes())
			aryOpts.add("-C"); // Do not try to eliminate colinear attributes

		aryOpts.add("-R"); // Set ridge parameter (default 1.0e-8)
		aryOpts.add(String.valueOf(getRidgeParameter()));

		if (isConserveMemory())
			aryOpts.add("-minimal"); // Conserve memory, don't keep dataset header and means/stdevs (default: keep data)

		if (isAdditionalStats())
			aryOpts.add("-additional-stats"); // Output additional statistics

		if (isDebug())
			aryOpts.add("-output-debug-info"); // Enables debug mode

		if (isDoNotCheckClassifierCapabilities())
			aryOpts.add("-do-not-check-capabilities"); // Classifier capabilities are not checked before classifier is built (use with caution)

		String[] opts = aryOpts.toArray(new String[aryOpts.size()]);

		try {
			wekaRegressionModel.setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
