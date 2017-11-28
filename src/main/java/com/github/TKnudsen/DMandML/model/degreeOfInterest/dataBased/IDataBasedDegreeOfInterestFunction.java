package com.github.TKnudsen.DMandML.model.degreeOfInterest.dataBased;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.IFeatureVectorSupplier;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.IDegreeOfInterestFunction;

/**
 * <p>
 * Title: IDataBasedInterestingnessFunction
 * </p>
 * 
 * <p>
 * Description: A data supplier supplements the data-based interestingness
 * function. In this way we ensure that the interestingness function is always
 * able to run on an actual data collection, maintained by an external source.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IDataBasedDegreeOfInterestFunction<FV extends AbstractFeatureVector<?, ?>> extends IDegreeOfInterestFunction<FV> {

	public IFeatureVectorSupplier<FV> getFeatureVectorSupplier();

}
