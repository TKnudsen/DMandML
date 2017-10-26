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

	public LabelDistribution getLabelDistribution(X x);
}
