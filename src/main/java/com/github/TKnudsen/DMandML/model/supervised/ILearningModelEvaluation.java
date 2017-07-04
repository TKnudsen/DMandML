package main.java.com.github.TKnudsen.DMandML.model.supervised;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

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
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public interface ILearningModelEvaluation<O, X extends AbstractFeatureVector<O, ? extends Feature<O>>, Y> {

	public double getQuality(ILearningModel<O, X, Y> model, List<X> testData, Y targetVariable);
}
