package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.density;

import java.util.List;
import java.util.function.Supplier;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

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
 * 
 * This implementation provides discrete density scores according to the number
 * of neighbors / the maximum number of neighbors per instance.
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
public class DataDensityEpsilonNeighborDistancesInterestingnessFunction<FV>
		extends DataDensityEpsilonBasedInterestingnessFunction<FV> {

	public DataDensityEpsilonNeighborDistancesInterestingnessFunction(
			Supplier<? extends List<? extends FV>> featureVectorSupplier, IDistanceMeasure<? super FV> distanceMeasure,
			double epsilon) {

		super(featureVectorSupplier, distanceMeasure, epsilon);
	}

	protected double calculateScore(FV fv, List<? extends FV> possibleNeighbors) {
		double count = 0.0;

		for (FV possibleNeighbor : possibleNeighbors) {
			double distance = getDistanceMeasure().getDistance(fv, possibleNeighbor);

			if (distance < getEpsilon())
				count += (1.0 - distance / getEpsilon());
		}

		return count;
	}

	@Override
	public String getDescription() {
		return "Estimates the density for a series of instances according to the number of instances within a distance of <= epsilon and their distances";
	}

	@Override
	public String getName() {
		return "Density Epsilon Neighbor Distance";
	}

}
