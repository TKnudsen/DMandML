package main.java.com.github.TKnudsen.DMandML.model.supervised.regression;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

import main.java.com.github.TKnudsen.DMandML.model.supervised.ILearningModel;

/**
 * <p>
 * Title: IRegression
 * </p>
 * 
 * <p>
 * Description: basic algorithmic model that learns numerical label information.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public interface IRegression<O, X extends AbstractFeatureVector<O, ? extends Feature<O>>> extends ILearningModel<O, X, Double> {
	public Double getAccuracy(X featureVector);
}
