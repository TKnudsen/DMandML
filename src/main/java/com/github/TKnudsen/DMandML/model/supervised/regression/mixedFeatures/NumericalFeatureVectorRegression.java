package main.java.com.github.TKnudsen.DMandML.model.supervised.regression.mixedFeatures;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;

import main.java.com.github.TKnudsen.DMandML.model.supervised.regression.IRegression;

/**
 * <p>
 * Title: INumericalFeatureVectorRegression
 * </p>
 * 
 * <p>
 * Description: basic algorithmic model that learns label information for
 * numerical features. The labels are numbers, thus, regression-like models are trained.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public interface NumericalFeatureVectorRegression extends IRegression<Double, NumericalFeatureVector> {

}
