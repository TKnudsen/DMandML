package com.github.TKnudsen.DMandML.model.supervised;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;

/**
 * <p>
 * Title: ILearningModelEvaluation
 * </p>
 * 
 * <p>
 * Description: evaluator for learning models
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 * 
 * TODO_GENERICS Parameter "O" is not used any more
 */
public interface ILearningModelEvaluation<O, X extends IFeatureVectorObject<?, ?>, Y> {

	public double getQuality(ILearningModel<O, X, Y> model, List<X> testData, Y targetVariable);
}
