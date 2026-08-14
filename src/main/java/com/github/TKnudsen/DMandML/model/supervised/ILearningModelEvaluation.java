package com.github.TKnudsen.DMandML.model.supervised;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;

/**
 * <p>
 * evaluator for learning models
 * </p>
 *
 * @version 1.03
 * @since 2016
 */
public interface ILearningModelEvaluation<X extends IFeatureVectorObject<?, ?>, Y> {

	public double getQuality(ILearningModel<X, Y> model, List<X> testData, Y targetVariable);
}
