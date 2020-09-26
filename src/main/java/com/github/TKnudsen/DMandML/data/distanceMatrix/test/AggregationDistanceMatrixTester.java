package com.github.TKnudsen.DMandML.data.distanceMatrix.test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrixParallel;
import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.IDistanceMatrix;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.tools.MathFunctions;
import com.github.TKnudsen.DMandML.data.distanceMatrix.AggregationDistanceMatrix;

/**
 * 
 * Experiment with lower number of elements shows that aggregation is not
 * necessary for less than 2k elements
 * 
 * See AggregationDistanceMatrixTestResults for test results
 * 
 * INSIGHT: error is max for many dimensions and few elements. In this case, the
 * data space is very large but weakly populated
 * 
 * <p>
 * Copyright: (c) 2017-2020 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 */
public class AggregationDistanceMatrixTester {
	public static void main(String[] args) {
		Random random = new Random(42);
		List<Integer> dataElements = Arrays.asList(new Integer[] { 1000, 2000, 5000, 10000 });
		List<Integer> dimensions = Arrays.asList(new Integer[] { 5, 10, 15, 25, 50 });

		for (int n : dataElements)
			for (int dim : dimensions) {

				List<NumericalFeatureVector> fvs = AggregationDistanceMatrixCalibrationExperiment
						.randomFeatureVectors(n, dim, random);

				long before = System.nanoTime();
				IDistanceMatrix<NumericalFeatureVector> dmConventional = new DistanceMatrixParallel<>(fvs,
						new EuclideanDistanceMeasure(), true, true);
				long after = System.nanoTime();
				double durationOldDm = (after - before) / 1e6;

				before = System.nanoTime();
				AggregationDistanceMatrix<NumericalFeatureVector> dmAggregated = new AggregationDistanceMatrix<NumericalFeatureVector>(
						fvs, new EuclideanDistanceMeasure(), dim, 1000);
				after = System.nanoTime();
				double durationNewDm = (after - before) / 1e6;

				double error = AggregationDistanceMatrixCalibrationExperiment.assessError(fvs, dmConventional,
						dmAggregated);
				double memory = AggregationDistanceMatrixCalibrationExperiment.assessMemoryAllocation(fvs,
						dmAggregated);
				System.out.println("Elements:\t" + n + ",\tdim:\t" + dim + ", k: " + dmAggregated.getClusterCount()
						+ ",\tper cluster:\t"
						+ MathFunctions.round((fvs.size() / (double) dmAggregated.getClusterCount()), 3) + ",\terror:\t"
						+ MathFunctions.round(error * 100, 2) + "%,\tmemory:\t" + MathFunctions.round(memory * 100, 2)
						+ "%,\tduration:\t" + MathFunctions.round(durationNewDm / durationOldDm * 100, 2) + "%");
				System.out.println();
			}
	}

}
