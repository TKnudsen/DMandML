package com.github.TKnudsen.DMandML.data.distanceMatrix.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrixParallel;
import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.IDistanceMatrix;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVectors;
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
public class AggregationDistanceMatrixCalibrationExperiment {
	public static void main(String[] args) {
		int n = 10000;
		int dim = 10;
		Random random = new Random(42);
		List<Double> clusterCountAvg = Arrays
				.asList(new Double[] { 1.5, 1.75, 2.0, 3.0, 4.0, 5.0, 10.0, 15.0, 20.0, 30.0, 50.0 });

		List<NumericalFeatureVector> fvs = randomFeatureVectors(n, dim, random);

		System.out.println("ELEMENTS:\t" + n + ",\tDIM:\t" + dim);
		System.out.println();

		for (double avg : clusterCountAvg) {
			long before = System.nanoTime();
			// conventional distance matrix
			IDistanceMatrix<NumericalFeatureVector> dmConventional = new DistanceMatrixParallel<>(fvs,
					new EuclideanDistanceMeasure(), true, true);
			long after = System.nanoTime();
			double durationOldDm = (after - before) / 1e6;

			before = System.nanoTime();
			AggregationDistanceMatrix<NumericalFeatureVector> dmAggregated = new AggregationDistanceMatrix<NumericalFeatureVector>(
					fvs, new EuclideanDistanceMeasure(), true, true, 1000, avg);
			after = System.nanoTime();
			double durationNewDm = (after - before) / 1e6;

			int k = (int) (fvs.size() / (double) avg);
			System.out.println("K: " + k + ",\tper cluster:\t" + avg + ",\terror:\t"
					+ MathFunctions.round(assessError(fvs, dmConventional, dmAggregated) * 100, 2) + "%,\tmemory:\t"
					+ MathFunctions.round(assessMemoryAllocation(fvs, dmAggregated) * 100, 2) + "%,\tduration:\t"
					+ MathFunctions.round(durationNewDm / durationOldDm * 100, 2) + "%");
			System.out.println();
		}
	}

	static List<NumericalFeatureVector> randomFeatureVectors(int elements, int dim, Random random) {
		List<NumericalFeatureVector> fvs = new ArrayList<>();
		for (int i = 0; i < elements; i++) {
			double[] vector = new double[dim];
			for (int d = 0; d < dim; d++)
				vector[d] = random.nextDouble();
			fvs.add(NumericalFeatureVectors.createNumericalFeatureVector(vector));
		}

		return fvs;
	}

	static <T> double assessError(List<T> elements, IDistanceMatrix<T> referenceDM, IDistanceMatrix<T> testDM) {
		double distanceSum = 0.0;
		double errorSum = 0.0;
		for (T t : elements)
			for (T u : elements)
				if (u == t)
					continue;
				else {
					double d1 = referenceDM.getDistance(t, u);
					double d2 = testDM.getDistance(t, u);

					distanceSum += d1;
					errorSum += Math.abs(d1 - d2);
				}

		return errorSum / distanceSum;
	}

	static <T> double assessMemoryAllocation(List<T> elements, AggregationDistanceMatrix<T> testDM) {
		double size = elements.size() * elements.size();
		return testDM.getInternalMatrixSize() / size;
	}
}
