package com.github.TKnudsen.DMandML.model.retrieval.epsilon;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.model.retrieval.IRetrievalAlgorithm;

public class EpsilonNeighbors<T> implements IRetrievalAlgorithm<T> {

	private final double epsilon;

	private final IDistanceMeasure<? super T> distanceMeasure;

	private final Collection<T> elements;

	public EpsilonNeighbors(double epsilon, IDistanceMeasure<? super T> distanceMeasure,
			Collection<? extends T> elements) {
		this.epsilon = epsilon;
		this.distanceMeasure = Objects.requireNonNull(distanceMeasure, "The distanceMeasure may not be null");
		this.elements = Collections.unmodifiableCollection(elements);
	}

	@Override
	public List<Entry<T, Double>> retrieveNeighbors(T query) {

		List<Entry<T, Double>> result = new ArrayList<>();
		for (T element : elements) {
			double distance = distanceMeasure.applyAsDouble(query, element);
			if (distance <= epsilon) {
				result.add(new SimpleImmutableEntry<>(element, distance));
			}
		}
		Collections.sort(result, Entry.comparingByValue());
		return result;
	}

	@Override
	public Collection<T> getElements() {
		return elements;
	}

	public String getName() {
		return "FastEpsilonNeighbors";
	}

	public String getDescription() {
		return "Retrieval algorithm based on an epsilon-based kriterion";
	}

	@Override
	public IDistanceMeasure<? super T> getDistanceMeasure() {
		return distanceMeasure;
	}
}