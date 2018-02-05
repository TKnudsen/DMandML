package com.github.TKnudsen.DMandML.model.supervised.regression;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;

/**
 * <p>
 * Title: ZeroR
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author Christian Ritter
 * @version 1.02
 * 
 * TODO_GENERICS Parameter "O" is not used any more
 */
public class BasicZeroR<O, FV extends IFeatureVectorObject<?, ?>> extends WekaRegressionWrapper<O, FV> {

	private boolean debug = false;

	@Override
	public String getDescription() {
		return "WEKA's implementation of the ZeroR regression algorithm.";
	}

	@Override
	public String getName() {
		return "ZeroR";
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
		wekaRegressionModel = new weka.classifiers.rules.ZeroR();

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
