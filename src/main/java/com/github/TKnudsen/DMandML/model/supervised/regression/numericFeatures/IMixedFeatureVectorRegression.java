package main.java.com.github.TKnudsen.DMandML.model.supervised.regression.numericFeatures;

import com.github.TKnudsen.ComplexDataObject.data.features.mixedData.MixedDataFeatureVector;

import main.java.com.github.TKnudsen.DMandML.model.supervised.regression.IRegression;

/**
 * <p>
 * Title: IMixedFeatureVectorRegression
 * </p>
 * 
 * <p>
 * Description: basic algorithmic model that learns numerical label information
 * for mixed data features.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IMixedFeatureVectorRegression extends IRegression<Object, MixedDataFeatureVector> {

}
