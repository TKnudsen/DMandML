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
 * Copyright: (c) 2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 * 
 * TODO_GENERICS Parameter "O" is not used any more
 */
public class ClassifierTools {

	public static <O, FV extends IFeatureVectorObject<?, ?>> Classifier<O, FV> createParameterizedCopy(WekaClassifierWrapper<O, FV> classifier) throws Exception {
		WekaClassifierWrapper<O, FV> classifierNew = classifier.getClass().asSubclass(WekaClassifierWrapper.class).newInstance();

		classifierNew.wekaClassifier = (weka.classifiers.AbstractClassifier) weka.classifiers.AbstractClassifier.makeCopy(classifierNew.wekaClassifier);
		return classifierNew;
	}
}
