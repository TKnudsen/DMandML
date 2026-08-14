package com.github.TKnudsen.DMandML.model.supervised.classifier;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.IKeyValueProvider;
import com.github.TKnudsen.ComplexDataObject.data.keyValueObject.KeyValueProviders;

/**
 * <p>
 * provides additional functionality in the context of classification tasks.
 * Little helpers that should not be contained in the classifier functionality
 * in itself.
 * </p>
 *
 * @version 1.02
 * @since 2017
 */
public class Classifiers {
	public static <V, T extends IKeyValueProvider<V>> void setAttribute(String attributeName, List<? extends T> objects,
			List<? extends V> attributeValues) {

		KeyValueProviders.setAttribute(attributeName, objects, attributeValues);
	}

	public static <FV extends IFeatureVectorObject<?, ?>> Classifier<FV> createParameterizedCopy(
			WekaClassifierWrapper<FV> classifier) throws Exception {
		WekaClassifierWrapper<FV> classifierNew = classifier.getClass().asSubclass(WekaClassifierWrapper.class)
				.newInstance();

		classifierNew.setWekaClassifier((weka.classifiers.AbstractClassifier) weka.classifiers.AbstractClassifier
				.makeCopy(classifierNew.getWekaClassifier()));
		return classifierNew;
	}
}
