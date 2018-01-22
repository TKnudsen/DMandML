package com.github.TKnudsen.DMandML.model.unsupervised.outliers;

import java.util.List;

/**
 * <p>
 * Title: IOutlierAnalysisAlgorithm
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IOutlierAnalysisAlgorithm<O> {

	public void setData(List<O> data);

	/**
	 * parameters function call
	 */
	public void run();

	/**
	 * the outlier prediction score. Score is a more general term than
	 * weight/probability, etc. However, it is expected that implementations
	 * make use of a relative value domain where 1.0 means the maximum
	 * likelihood to be an outlier.
	 * 
	 * @param object
	 * @return
	 */
	public double getOutlierScore(O object);

	/**
	 * allows resetting calculated scores, e.g. when data or parameters have
	 * been changed. allows lazy implementations.
	 */
	public void resetScores();
}
