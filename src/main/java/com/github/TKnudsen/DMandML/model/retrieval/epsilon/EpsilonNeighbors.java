package com.github.TKnudsen.DMandML.model.retrieval.epsilon;

import java.util.Collection;
import java.util.Collections;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.ranking.Ranking;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

/**
 * <p>
 * Description: neighbors in the epsilon-range of a given element.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2019 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class EpsilonNeighbors<FV> implements IEpsilonRetrievalAlgorithm<FV> {

	private double epsilon;

	private final IDistanceMeasure<? super FV> distanceMeasure;

	private final Collection<FV> elements;

	public EpsilonNeighbors(double epsilon, IDistanceMeasure<? super FV> distanceMeasure,
			Collection<? extends FV> elements) {
		setEpsilon(epsilon);

		this.distanceMeasure = distanceMeasure;
		this.elements = Collections.unmodifiableCollection(elements);
	}

	@Override
	public Ranking<EntryWithComparableKey<Double, FV>> retrieveNeighbors(FV element) {
		Ranking<EntryWithComparableKey<Double, FV>> ranking = new Ranking<>();

		for (FV fv : elements) {
			double distance = getDistanceMeasure().getDistance(fv, element);

			if (distance <= getEpsilon())
				ranking.add(new EntryWithComparableKey<Double, FV>(distance, fv));
		}

		return ranking;
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
		return "Retrieves neighbors in the epsilon range of an element";
	}

	public double getEpsilon() {
		return epsilon;
	}

	@Override
	public void setEpsilon(double epsilon) {
		if (epsilon < 0)
			throw new IllegalArgumentException(
					getName() + ": illegal parameter value for epsilon: " + getEpsilon() + "must be >=0");

		this.epsilon = epsilon;
	}

}
