package com.github.TKnudsen.DMandML.model.supervised.classifier;

import java.util.List;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.supervised.ILearningModel;

/**
 * <p>
 * Title: IClassifier
 * </p>
 * 
 * <p>
 * Description: basic classifier interface. Classifiers learn categorical label
 * information.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.06
 * 
 * TODO_GENERICS Parameter "O" is not used any more
 */
public interface IClassifier<O, X> extends ILearningModel<O, X, String> {

	public IClassificationResult<X> createClassificationResult(List<X> featureVectors);

	public List<String> getLabelAlphabet();

}
