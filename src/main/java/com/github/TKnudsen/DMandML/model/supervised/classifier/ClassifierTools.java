package com.github.TKnudsen.DMandML.model.supervised.classifier;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;

/**
 * <p>
 * Title: ClassifierTools
 * </p>
 * 
 * <p>
 * Description: little helpers that should not be contained in the classifier
 * functionality in itself.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public class ClassifierTools {

	public static <FV extends IFeatureVectorObject<?, ?>> Classifier<FV> createParameterizedCopy(
			WekaClassifierWrapper<FV> classifier) throws Exception {
		WekaClassifierWrapper<FV> classifierNew = classifier.getClass().asSubclass(WekaClassifierWrapper.class)
				.newInstance();

		classifierNew.setWekaClassifier((weka.classifiers.AbstractClassifier) weka.classifiers.AbstractClassifier
				.makeCopy(classifierNew.getWekaClassifier()));
		return classifierNew;
	}
}
