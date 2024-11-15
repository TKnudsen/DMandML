package com.github.TKnudsen.DMandML.model.retrieval.kNN;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.DMandML.model.retrieval.IRetrievalAlgorithm;

public class KNN<T> implements IRetrievalAlgorithm<T> {

	private final int knn;

	private final ToDoubleBiFunction<? super T, ? super T> distanceMeasure;

	private final Collection<T> elements;

	public KNN(int knn, ToDoubleBiFunction<? super T, ? super T> distanceMeasure, Collection<? extends T> elements) {
		this.knn = knn;
		this.distanceMeasure = Objects.requireNonNull(distanceMeasure, "The distanceMeasure may not be null");
		this.elements = Collections.unmodifiableCollection(elements);
	}

	@Override
	public List<Entry<T, Double>> retrieveNeighbors(T query) {

		Comparator<Entry<T, Double>> comparator = Entry.comparingByValue();
		PriorityQueue<Entry<T, Double>> highest = new PriorityQueue<Entry<T, Double>>(1000, comparator.reversed());

		List<Entry<T, Double>> result = new ArrayList<>();
		for (T element : elements) {
			if (element == null)
				continue;
			if (element.equals(query))
				continue;

			double distance = distanceMeasure.applyAsDouble(query, element);

			if (highest.size() == knn)
				if (highest.peek().getValue() < distance)
					continue;

			SimpleImmutableEntry<T, Double> entry = new SimpleImmutableEntry<>(element, distance);
			highest.offer(entry);
			while (highest.size() > knn) {
				highest.poll();
			}
		}
		while (!highest.isEmpty()) {
			result.add(highest.poll());
		}
		Collections.reverse(result);
		return result;
	}

	@Override
	public Collection<T> getElements() {
		return elements;
	}

	public String getName() {
		return "FastKNN";
	}

	public String getDescription() {
		return "Retrieval algorithm based on k nearest neighbor search";
	}

	@Override
	public ToDoubleBiFunction<? super T, ? super T> getDistanceMeasure() {
		return distanceMeasure;
	}
}