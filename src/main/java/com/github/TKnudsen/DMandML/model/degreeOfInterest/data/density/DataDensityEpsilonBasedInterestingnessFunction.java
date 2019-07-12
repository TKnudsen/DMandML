package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.density;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.IDegreeOfInterestFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores for elements with respect to the local
 * density of a pre-given set of elements. Uses a range (epsilon) to calculate
 * density scores (according to the containing neighbor instances).
 * </p>
 * 
 * Similar to the "Density Estimation (DEN)" building block for learning
 * algorithms, published in: Juergen Bernard, Matthias Zeppelzauer, Markus
 * Lehmann, Martin Mueller, and Michael Sedlmair: Towards User-Centered Active
 * Learning Algorithms. Eurographics Conference on Visualization (EuroVis),
 * Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.02
 */
public abstract class DataDensityEpsilonBasedInterestingnessFunction<FV> implements IDegreeOfInterestFunction<FV> {

	/**
	 * allows changing data instance over time. The supplier ensures that the set of
	 * featureVectors is maintained.
	 */
	private final Supplier<? extends List<? extends FV>> featureVectorSupplier;

	private final IDistanceMeasure<? super FV> distanceMeasure;

	private final double epsilon;

	public DataDensityEpsilonBasedInterestingnessFunction(Supplier<? extends List<? extends FV>> featureVectorSupplier,
			IDistanceMeasure<? super FV> distanceMeasure, double epsilon) {

		this.featureVectorSupplier = featureVectorSupplier;
		this.distanceMeasure = distanceMeasure;
		this.epsilon = epsilon;
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> t) {
		Objects.requireNonNull(t);

		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();

		if (t.size() == 0)
			return interestingnessScores;

		List<? extends FV> existingInstances = featureVectorSupplier.get();

		for (FV fv : t)
			interestingnessScores.put(fv, calculateScore(fv, existingInstances));

		NormalizationFunction normalizationFunction = new LinearNormalizationFunction(interestingnessScores.values());
		for (FV fv : interestingnessScores.keySet())
			interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

		return interestingnessScores;
	}

	/**
	 * intrinsic part of inheriting classes.
	 * 
	 * @param fv
	 * @param possibleNeighbors
	 * @return
	 */
	protected abstract double calculateScore(FV fv, List<? extends FV> possibleNeighbors);

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String getDescription() {
		return "Estimates the density for a series of instances according to instances in the vicinity of <= epsilon";
	}

	public IDistanceMeasure<? super FV> getDistanceMeasure() {
		return distanceMeasure;
	}

	public double getEpsilon() {
		return epsilon;
	}

}
