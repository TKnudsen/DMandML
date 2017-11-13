package com.github.TKnudsen.DMandML.model.degreeOfInterest.classBased;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResultSupplier;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.IDegreeOfInterestFunction;

/**
 * <p>
 * Title: IClassificationBasedInterestingnessFunction
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
public interface IClassificationBasedInterestingnessFunction<FV extends AbstractFeatureVector<?, ?>> extends IDegreeOfInterestFunction<FV> {

	public IProbabilisticClassificationResultSupplier<FV> getClassificationResultSupplier();
}
