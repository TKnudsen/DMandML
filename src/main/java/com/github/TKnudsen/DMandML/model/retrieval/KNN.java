package com.github.TKnudsen.DMandML.model.retrieval;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.data.ranking.Ranking;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

/**
 * <p>
 * Title: KNN
 * </p>
 * 
 * <p>
 * Description: k nearest neighbor operation.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class KNN<O extends IDObject> {

	private int kNN;

	private IDistanceMeasure<O> distanceMeasure;

	private List<O> elements;

	public KNN(int kNN, IDistanceMeasure<O> distanceMeasure, List<O> elements) {
		this.kNN = kNN;
		this.distanceMeasure = distanceMeasure;
		this.elements = elements;
	}

	public Ranking<EntryWithComparableKey<Double, O>> getNearestNeighborsWithScores(O element) {
		Ranking<EntryWithComparableKey<Double, O>> ranking = new Ranking<>();

		for (O o : elements) {
			if (o.equals(element))
				continue;

			double distance = distanceMeasure.getDistance(element, o);
			if (ranking.size() >= kNN && ranking.get(kNN - 1).getKey() < distance)
				continue;

			ranking.add(new EntryWithComparableKey<Double, O>(distance, o));

			if (ranking.size() > kNN)
				ranking.removeLast();
		}

		return ranking;
	}

	public List<O> getNearestNeighbors(O element) {
		Ranking<EntryWithComparableKey<Double, O>> nearestNeighborsWithScores = getNearestNeighborsWithScores(element);

		List<O> returnElements = new ArrayList<>();
		for (int i = 0; i < nearestNeighborsWithScores.size(); i++)
			returnElements.add(nearestNeighborsWithScores.get(i).getValue());

		return returnElements;
	}

	public int getKNN() {
		return kNN;
	}

	public void setKNN(int kNN) {
		this.kNN = kNN;
	}
}
