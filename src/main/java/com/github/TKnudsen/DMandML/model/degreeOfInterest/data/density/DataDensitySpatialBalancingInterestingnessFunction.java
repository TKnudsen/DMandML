package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.density;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import com.github.TKnudsen.ComplexDataObject.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores of elements with respect to the distances
 * to a pre-given set of elements. The list of pre-given elements is modeled
 * with a supplier function to ensure that the elements are always in the actual
 * state that is controlled somewhere else.
 * </p>
 * 
 * One application example of spatial balancing is a data labeling task where
 * instances are to be discovered that are far away from an existing set of
 * (already labeled) instances, i.e. training data.
 * </p>
 * 
 * According to the "Spatial Balancing (SPB)" building block for learning
 * algorithms. Tries to locate instances in so far undiscovered areas of the
 * feature space and thereby tries to uniformly distribute the labeling
 * candidates across the space.
 * </p>
 * 
 * published in: Juergen Bernard, Matthias Zeppelzauer, Markus Lehmann, Martin
 * Mueller, and Michael Sedlmair: Towards User-Centered Active Learning
 * Algorithms. Eurographics Conference on Visualization (EuroVis), Computer
 * Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.04
 */
public class DataDensitySpatialBalancingInterestingnessFunction<FV> implements IDegreeOfInterestFunction<FV> {

	private final Supplier<? extends List<? extends FV>> existingInstancesSupplier;

	private final IDistanceMeasure<? super FV> distanceMeasure;

	public DataDensitySpatialBalancingInterestingnessFunction(
			Supplier<? extends List<? extends FV>> existingInstancesSupplier,
			IDistanceMeasure<? super FV> distanceMeasure) {

		this.existingInstancesSupplier = existingInstancesSupplier;
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> t) {
		Objects.requireNonNull(t);

		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();

		if (t.size() == 0)
			return interestingnessScores;

		Collection<Number> distances = new ArrayList<>();

		for (FV fv : t) {
			double dist = calculateMinimumDistanceToExistingInstances(fv);

			interestingnessScores.put(fv, dist);
			distances.add(dist);
		}

		NormalizationFunction normalizationFunction = new LinearNormalizationFunction(distances);
		for (FV fv : interestingnessScores.keySet())
			interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

		return interestingnessScores;
	}

	private double calculateMinimumDistanceToExistingInstances(FV fv) {
		List<? extends FV> instances = existingInstancesSupplier.get();

		double minimumDistance = Double.MAX_VALUE;

		for (FV candidate : instances)
			minimumDistance = Math.min(minimumDistance, distanceMeasure.getDistance(candidate, fv));

		return minimumDistance;
	}

	@Override
	public String getName() {
		return "Density Spatial Balancing";
	}

	@Override
	public String getDescription() {
		return "Degree-of-interest function that applies spatial balancing.";
	}

}
