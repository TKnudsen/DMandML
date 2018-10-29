package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.density;

import java.util.List;
import java.util.function.Supplier;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.IDegreeOfInterestFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Creates instances of different density-based
 * {@link IDegreeOfInterestFunction} implementations. Uses a variety of criteria
 * is used to create different instances.
 * </p>
 * 
 * @version 1.02
 */
public class DataDensityBasedDegreeOfInterestFunctions {

	/**
	 * 
	 * @param trainingVectorsSupplier
	 * @param distanceMeasure
	 * @param kNN
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDataDensityBased(
			Supplier<? extends List<? extends NumericalFeatureVector>> trainingVectorsSupplier,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure, int kNN) {

		return new DataDensityNearestNeighborsBasedInterestingnessFunction<>(trainingVectorsSupplier, distanceMeasure, kNN);
	}

	/**
	 * 
	 * @param trainingVectorsSupplier
	 * @param distanceMeasure
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDataDensityEpsilonNeighborCount(
			Supplier<? extends List<? extends NumericalFeatureVector>> trainingVectorsSupplier,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure, double epsilon) {

		return new DataDensityEpsilonNeighborCountInterestingnessFunction<>(trainingVectorsSupplier, distanceMeasure,
				epsilon);
	}

	/**
	 * 
	 * @param trainingVectorsSupplier
	 * @param distanceMeasure
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDataDensityEpsilonNeighborDistances(
			Supplier<? extends List<? extends NumericalFeatureVector>> trainingVectorsSupplier,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure, double epsilon) {

		return new DataDensityEpsilonNeighborDistancesInterestingnessFunction<>(trainingVectorsSupplier,
				distanceMeasure, epsilon);
	}

	/**
	 * 
	 * @param trainingVectorsSupplier
	 * @param distanceMeasure
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDataDensitySpatialBalancing(
			Supplier<? extends List<? extends NumericalFeatureVector>> trainingVectorsSupplier,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		return new DataDensitySpatialBalancingInterestingnessFunction<>(trainingVectorsSupplier, distanceMeasure);
	}

}
