package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.density;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.ranking.Ranking;
import com.github.TKnudsen.ComplexDataObject.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.MapUtils;
import com.github.TKnudsen.DMandML.model.retrieval.KNN;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores for elements with respect to the local
 * density of a pre-given set of elements. The list of pre-given elements is
 * modeled with a supplier function to ensure that the elements are always in
 * the actual state that is controlled somewhere else.
 * </p>
 * 
 * One application example of density estimation is a data labeling task where
 * instances are to be discovered that are located in dense regions of a data
 * set.
 * </p>
 * 
 * According to the "Nearest Spatial Neighbors (NSN)" building block for
 * learning algorithms. Retrieves instances in the neighborhood of a candidate
 * instance, allowing the assessment of local data characteristics around it. It
 * is implemented in a straight-forward manner based on k-nearest neighbor (kNN)
 * search.
 * </p>
 * 
 * Published in: Juergen Bernard, Matthias Zeppelzauer, Markus Lehmann, Martin
 * Mueller, and Michael Sedlmair: Towards User-Centered Active Learning
 * Algorithms. Eurographics Conference on Visualization (EuroVis), Computer
 * Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.03
 */
public class DataDensityNearestNeighborsBasedInterestingnessFunction<FV> implements IDegreeOfInterestFunction<FV> {

	private final Supplier<? extends List<? extends FV>> existingInstancesSupplier;

	private final IDistanceMeasure<? super FV> distanceMeasure;

	private final int kNNCount;

	private KNN<FV> kNNOperation;

	public DataDensityNearestNeighborsBasedInterestingnessFunction(
			Supplier<? extends List<? extends FV>> existingInstancesSupplier,
			IDistanceMeasure<? super FV> distanceMeasure, int kNNCount) {

		this.existingInstancesSupplier = existingInstancesSupplier;
		this.distanceMeasure = distanceMeasure;
		this.kNNCount = kNNCount;
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> t) {
		Objects.requireNonNull(t);

		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();

		if (t.size() == 0)
			return interestingnessScores;

		List<? extends FV> existingInstances = existingInstancesSupplier.get();

		Collection<Number> densities = new ArrayList<>();

		kNNOperation = new KNN<FV>(kNNCount, distanceMeasure, existingInstances);

		for (FV fv : t) {
			Ranking<EntryWithComparableKey<Double, FV>> nearestNeighborsWithScores = kNNOperation
					.getNearestNeighborsWithScores(fv);

			double sum = 0;
			for (EntryWithComparableKey<Double, FV> element : nearestNeighborsWithScores)
				sum += element.getKey();

			interestingnessScores.put(fv, sum);
			densities.add(sum);
		}

		NormalizationFunction normalizationFunction = new LinearNormalizationFunction(densities);
		for (FV fv : interestingnessScores.keySet())
			interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

		return MapUtils.affineTransformValues(interestingnessScores, -1, 1);
	}

	@Override
	public String getName() {
		return "Density Neighbor Distances";
	}

	@Override
	public String getDescription() {
		return "Estimates the density for a series if FV with respect to a pre-given data distribution.";
	}

}
