package com.github.TKnudsen.DMandML.model.retrieval;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

/**
 * <p>
 * Copyright: (c) 2016-2019 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @version 1.02
 */
public interface IRetrievalAlgorithm<T> extends ISelfDescription {

	public IDistanceMeasure<? super T> getDistanceMeasure();

	public List<Entry<T, Double>> retrieveNeighbors(T query);

	public Collection<T> getElements();

}
