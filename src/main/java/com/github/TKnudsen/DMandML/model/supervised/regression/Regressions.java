package com.github.TKnudsen.DMandML.model.supervised.regression;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IKeyValueProvider;
import com.github.TKnudsen.ComplexDataObject.data.keyValueObject.KeyValueProviders;

import java.util.List;

/**
 * <p>
 * Title: Regressions
 * </p>
 * 
 * <p>
 * Description: provides additional functionality in the context of regression
 * tasks.
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
public class Regressions {
	public static <V, T extends IKeyValueProvider<V>> void setAttribute(String attributeName, List<? extends T> objects,
			List<? extends V> attributeValues) {

		KeyValueProviders.setAttribute(attributeName, objects, attributeValues);
	}
}