package com.github.TKnudsen.DMandML.data.classification;

import com.github.TKnudsen.ComplexDataObject.data.uncertainty.string.LabelUncertainty;

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
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IProbabilisticClassificationResult<X> extends IClassificationResult<X> {

	public LabelUncertainty getLabelDistribution(X x);
}
