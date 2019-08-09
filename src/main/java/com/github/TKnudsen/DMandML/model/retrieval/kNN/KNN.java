package com.github.TKnudsen.DMandML.model.retrieval.kNN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.ranking.Ranking;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

/**
 * <p>
 * Title: KNN
 * </p>
 * 
 * <p>
 * Description: k nearest neighbor operation, applied on a collection of
 * pre-given instancess.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2019 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 */
public class KNN<FV> implements IkNNRetrievalAlgorithm<FV> {

	private int kNN;

	private final IDistanceMeasure<? super FV> distanceMeasure;

	private final Collection<FV> elements;

	public KNN(int kNN, IDistanceMeasure<? super FV> distanceMeasure, Collection<? extends FV> elements) {
		setKNN(kNN);

		this.distanceMeasure = distanceMeasure;
		this.elements = Collections.unmodifiableCollection(elements);
	}

	@Override
	public Ranking<EntryWithComparableKey<Double, FV>> retrieveNeighbors(FV element) {
		return getNearestNeighborsWithScores(element);
	}

	/**
	 * 
	 * @param element
	 * @return
	 * @Deprecated use retrieveNeighbors in future
	 */
	public Ranking<EntryWithComparableKey<Double, FV>> getNearestNeighborsWithScores(FV element) {
		Ranking<EntryWithComparableKey<Double, FV>> ranking = new Ranking<>();

		for (FV o : elements) {
			if (o.equals(element))
				continue;

			double distance = distanceMeasure.getDistance(element, o);
			if (ranking.size() >= kNN && ranking.get(kNN - 1).getKey() < distance)
				continue;

			ranking.add(new EntryWithComparableKey<Double, FV>(distance, o));

			if (ranking.size() > kNN)
				ranking.removeLast();
		}

		return ranking;
	}

	public List<FV> getNearestNeighbors(FV element) {
		Ranking<EntryWithComparableKey<Double, FV>> nearestNeighborsWithScores = getNearestNeighborsWithScores(element);

		List<FV> returnElements = new ArrayList<>();
		for (int i = 0; i < nearestNeighborsWithScores.size(); i++)
			returnElements.add(nearestNeighborsWithScores.get(i).getValue());

		return returnElements;
	}

	public int getKNN() {
		return kNN;
	}

	@Override
	public void setKNN(int kNN) {
		if (kNN < 1)
			throw new IllegalArgumentException("KNN: illegal parameter value for kNN: " + kNN + "must be >0");

		this.kNN = kNN;
	}

	public IDistanceMeasure<? super FV> getDistanceMeasure() {
		return distanceMeasure;
	}

	public Collection<FV> getElements() {
		return elements;
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public String getDescription() {
		return "Retrieves k nearest neighbors for a given element";
	}

}
