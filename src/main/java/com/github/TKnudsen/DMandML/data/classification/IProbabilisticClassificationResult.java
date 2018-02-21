package com.github.TKnudsen.DMandML.data.classification;

/**
 * <p>
 * Title: IProbabilisticClassificationResult
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IProbabilisticClassificationResult<X> extends IClassificationResult<X> {

	/**
	 * Returns the {@link LabelDistribution} for the given feature vector,
	 * or <code>null</code> if the give feature vector is not contained
	 * in the {@link #getFeatureVectors() feature vectors of this result}
	 * 
	 * @param x The feature vector
	 * @return The {@link LabelDistribution}
	 */
	public LabelDistribution getLabelDistribution(X x);
}
