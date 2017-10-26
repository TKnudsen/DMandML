package com.github.TKnudsen.DMandML.model.supervised;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;

/**
 * <p>
 * Title: ILearningModel
 * </p>
 * 
 * <p>
 * Description: basic algorithmic model that learns label information for
 * features. Both features and labels are to be defined in extending interfaces / implementing classes.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.04
 */
public interface ILearningModel<O, X extends AbstractFeatureVector<O, ? extends Feature<O>>, Y> extends ISelfDescription {

	public void train(List<X> featureVectors, List<Y> labels);

	public void train(List<X> featureVectors, String targetVariable);

	public List<Y> test(List<X> featureVectors);
}
