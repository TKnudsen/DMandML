package com.github.TKnudsen.DMandML.model.supervised;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;

/**
 * <p>
 * Title: ILearningModel
 * </p>
 * 
 * <p>
 * Description: basic algorithmic model that learns label information for
 * features. Both features and labels are to be defined in extending interfaces
 * / implementing classes.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.05
 * 
 */
public interface ILearningModel<X, Y> extends ISelfDescription {

	public void train(List<X> featureVectors);

	public List<Y> test(List<X> featureVectors);
}
