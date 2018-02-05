package com.github.TKnudsen.DMandML.model.supervised.regression;

import com.github.TKnudsen.DMandML.model.supervised.ILearningModel;

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
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 * 
 * TODO_GENERICS Parameter "O" is not used any more
 */
public interface IRegression<O, X> extends ILearningModel<O, X, Double> {
	// public Double getAccuracy(X featureVector);
}
