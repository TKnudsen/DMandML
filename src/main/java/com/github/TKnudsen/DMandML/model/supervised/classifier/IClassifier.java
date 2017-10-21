package com.github.TKnudsen.DMandML.model.supervised.classifier;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;
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
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.06
 */
public interface IClassifier<O, X extends AbstractFeatureVector<O, ? extends Feature<O>>> extends ILearningModel<O, X, String> {

	public IClassificationResult<X> createClassificationResult(List<X> featureVectors);

	public List<String> getLabelAlphabet();

}
