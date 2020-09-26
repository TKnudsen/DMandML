/*
 * www.javagl.de - Data
 *
 * Copyright (c) 2013-2020 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction.generic;

import java.util.List;
import java.util.Random;
import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrices;

class GenericMDSUtils {
	static <T> double[][] computeNormalizedDistanceMatrix(List<? extends T> elements,
			ToDoubleBiFunction<? super T, ? super T> distanceFunction) {

		double distanceMatrix[][] = DistanceMatrices.distanceMatrix(elements, distanceFunction, true);

		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < elements.size(); i++) {
			for (int j = i + 1; j < elements.size(); j++) {
				double d = distanceMatrix[i][j];
				min = Math.min(min, d);
				max = Math.max(max, d);
			}
		}
		return normalize(distanceMatrix, min, max, null);
	}

	private static double[] normalize(double array[], double min, double max, double result[]) {
		double localResult[] = result;
		if (localResult == null) {
			localResult = array.clone();
		}
		double normalization = 1.0 / (max - min);
		for (int i = 0; i < array.length; i++) {
			localResult[i] = (array[i] - min) * normalization;
		}
		return localResult;
	}

	private static double[][] normalize(double array[][], double min, double max, double result[][]) {
		double localResult[][] = result;
		if (localResult == null) {
			localResult = new double[array.length][];
		}
		for (int i = 0; i < array.length; i++) {
			localResult[i] = normalize(array[i], min, max, localResult[i]);
		}
		return localResult;
	}

	static double[][] createRandomArrays(int count, int length, Random random) {
		double arrays[][] = new double[count][];
		for (int i = 0; i < count; i++) {
			arrays[i] = createRandomArray(length, random);
		}
		return arrays;
	}

	private static double[] createRandomArray(int length, Random random) {
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = random.nextDouble();
		}
		return array;
	}

	static double euclideanDistance(double a0[], double a1[]) {
		return Math.sqrt(squaredEuclideanDistance(a0, a1));
	}

	private static double squaredEuclideanDistance(double a0[], double a1[]) {
		double sum = 0;
		for (int i = 0; i < a0.length; i++) {
			double d = a0[i] - a1[i];
			sum += d * d;
		}
		return sum;
	}

	static double squaredEuclideanDistance(double a0[][], double a1[][]) {
		double sum = 0;
		for (int i = 0; i < a0.length; i++) {
			sum += squaredEuclideanDistance(a0[i], a1[i]);
		}
		return sum;
	}

	static boolean epsilonEqual(double a0[], double a1[], double epsilon) {
		if (a0.length != a1.length) {
			return false;
		}
		for (int i = 0; i < a0.length; i++) {
			double d = a0[i] - a1[i];
			if (d > epsilon) {
				return false;
			}
		}
		return true;
	}

}
