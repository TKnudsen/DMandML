package main.java.com.github.TKnudsen.DMandML.model.supervised.classifier;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

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
 * Copyright: (c) 2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class ClassifierTools {

	public static <O extends Object, FV extends AbstractFeatureVector<O, ? extends Feature<O>>> Classifier<O, FV> createParameterizedCopy(WekaClassifierWrapper<O, FV> classifier) throws Exception {
		WekaClassifierWrapper<O, FV> classifierNew = classifier.getClass().asSubclass(WekaClassifierWrapper.class).newInstance();

		classifierNew.wekaClassifier = (weka.classifiers.AbstractClassifier) weka.classifiers.AbstractClassifier.makeCopy(classifierNew.wekaClassifier);
		return classifierNew;
	}
}
