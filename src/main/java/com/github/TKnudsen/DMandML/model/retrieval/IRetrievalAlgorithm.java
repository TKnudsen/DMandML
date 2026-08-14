package com.github.TKnudsen.DMandML.model.retrieval;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;

/**
 * @version 1.03
 * @since 2016
 */
public interface IRetrievalAlgorithm<T> extends ISelfDescription {

	public ToDoubleBiFunction<? super T, ? super T> getDistanceMeasure();

	public List<Entry<T, Double>> retrieveNeighbors(T query);

	public Collection<T> getElements();

}
