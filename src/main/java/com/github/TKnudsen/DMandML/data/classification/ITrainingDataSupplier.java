package com.github.TKnudsen.DMandML.data.classification;

import java.util.List;
import java.util.function.Supplier;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;

/**
 * <p>
 * supplier for training data (e.g., used to train a learning model).
 * </p>
 *
 * @version 1.02
 * @since 2016
 */
public interface ITrainingDataSupplier<FV extends IFeatureVectorObject<?, ?>> extends Supplier<List<FV>> {

	public String getClassAttribute();
}
