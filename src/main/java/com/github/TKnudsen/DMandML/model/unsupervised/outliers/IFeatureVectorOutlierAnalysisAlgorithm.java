package com.github.TKnudsen.DMandML.model.unsupervised.outliers;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;

/**
 * <p>
 * Title: IFeatureVectorOutlierAnalysisAlgorithm
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
public interface IFeatureVectorOutlierAnalysisAlgorithm<FV> extends IOutlierAnalysisAlgorithm<FV>, ISelfDescription {

	@Override
	public double getOutlierScore(FV featureVector);
}
