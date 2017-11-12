package com.github.TKnudsen.DMandML.data.classification;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.IFeatureVectorSupplier;

/**
 * <p>
 * Title: ITrainingDataSupplier
 * </p>
 * 
 * <p>
 * Description: supplier for training data (e.g., used to train a learning
 * model).
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface ITrainingDataSupplier<FV extends AbstractFeatureVector<?, ?>> extends IFeatureVectorSupplier<FV> {

	public String getClassAttribute();
}
