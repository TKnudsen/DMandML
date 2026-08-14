package com.github.TKnudsen.DMandML.data.outliers;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;

/**
 * <p>
 * interface for clustering results.
 * </p>
 *
 * @version 1.01
 * @since 2018
 */
public interface IOutlierAnalysisResult<FV> extends IDObject, ISelfDescription, Iterable<FV> {

	boolean containsElement(FV fv);

	double getOutlierScore(FV fv);

	int size();

}