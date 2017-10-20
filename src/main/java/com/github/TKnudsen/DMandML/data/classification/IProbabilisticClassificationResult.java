package com.github.TKnudsen.DMandML.data.classification;

public interface IProbabilisticClassificationResult<X> extends IClassificationResult<X> {

	public LabelDistribution getLabelDistribution(X x);
}
