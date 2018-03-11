package com.github.TKnudsen.DMandML.model.supervised.classifier;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IKeyValueProvider;
import com.github.TKnudsen.ComplexDataObject.data.keyValueObject.KeyValueProviders;

/**
 * <p>
 * Title: Classifiers
 * </p>
 * 
 * <p>
 * Description: provides additional functionality in the context of
 * classification tasks.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 * 
 */
public class Classifiers {
	public static <V, T extends IKeyValueProvider<V>> void setAttribute(String attributeName, List<? extends T> objects,
			List<? extends V> attributeValues) {

		KeyValueProviders.setAttribute(attributeName, objects, attributeValues);
	}
}
