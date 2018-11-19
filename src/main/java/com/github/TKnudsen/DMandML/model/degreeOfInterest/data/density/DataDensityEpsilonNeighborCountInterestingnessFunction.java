package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.density;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

import java.util.List;
import java.util.function.Supplier;

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
 * @version 1.03
 */
public class DataDensityEpsilonNeighborCountInterestingnessFunction<FV>
		extends DataDensityEpsilonBasedInterestingnessFunction<FV> {

	public DataDensityEpsilonNeighborCountInterestingnessFunction(
			Supplier<? extends List<? extends FV>> featureVectorSupplier, IDistanceMeasure<? super FV> distanceMeasure,
			double epsilon) {

		super(featureVectorSupplier, distanceMeasure, epsilon);
	}

	@Override
	/**
	 * the intrinsic part of the routine is modularized to foster inheritance.
	 * 
	 * @param fv
	 * @param possibleNeighbors
	 * @return
	 */
	protected double calculateScore(FV fv, List<? extends FV> possibleNeighbors) {
		double count = 0.0;

		for (FV possibleNeighbor : possibleNeighbors) {
			double distance = getDistanceMeasure().getDistance(fv, possibleNeighbor);

			if (distance < getEpsilon()) {
				count++;
			}
		}

		return count;
	}

	@Override
	public String getDescription() {
		return "Estimates the density for a series of instances according to the number of instances within a distance of <= epsilon";
	}

	@Override
	public String getName() {
		return "Density Epsilon Neighbor Count";
	}

}
