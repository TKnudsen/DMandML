package com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction.generic;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrices;

public class GenericMDS<T> {
	private final List<? extends T> elementsH;
	private final ToDoubleBiFunction<? super T, ? super T> distanceFunctionH;
	private final int outputDim;
	private final int maxIterations;
	private final double stressDifferenceRateThreshold;
	private final ToDoubleBiFunction<double[], double[]> distanceFunctionL;

	public GenericMDS(List<? extends T> elementsH, ToDoubleBiFunction<? super T, ? super T> distanceFunctionH,
			int outputDim) {
		this(elementsH, distanceFunctionH, outputDim, 1000);
	}

	public GenericMDS(List<? extends T> elementsH, ToDoubleBiFunction<? super T, ? super T> distanceFunctionH,
			int outputDim, int maxIterations) {
		this.elementsH = Objects.requireNonNull(elementsH, "The elements may not be null");
		this.distanceFunctionH = Objects.requireNonNull(distanceFunctionH, "The distanceFunction may not be null");
		this.outputDim = outputDim;

		this.maxIterations = maxIterations;
		this.stressDifferenceRateThreshold = 0.000001;
		this.distanceFunctionL = GenericMDSUtils::euclideanDistance;
	}

	public double[][] compute() {
		double distanceMatrixH[][] = GenericMDSUtils.computeNormalizedDistanceMatrix(elementsH, distanceFunctionH);

		Random random = new Random(0);
		double[][] elementsL = GenericMDSUtils.createRandomArrays(elementsH.size(), outputDim, random);

		double previousStress = Double.NaN;
		for (int i = 0; i < maxIterations; i++) {
			double distanceMatrixL[][] = DistanceMatrices.distanceMatrix(Arrays.asList(elementsL), distanceFunctionL);
			double stress = GenericMDSUtils.squaredEuclideanDistance(distanceMatrixH, distanceMatrixL);

			if (!Double.isNaN(previousStress)) {
				double rate = Math.abs(previousStress / stress - 1);
				if (rate < stressDifferenceRateThreshold) {
					break;
				}
			}
			previousStress = stress;

			System.out.println("MDS: iteration " + (i + 1) + ":Kruskal's stress = " + stress);

			int n = elementsH.size();
			double newElementsL[][] = new double[n][outputDim];
			for (int k = 0; k < n; k++) {
				double newElement[] = new double[outputDim];
				for (int d = 0; d < outputDim; d++) {
					for (int j = 0; j < n; j++) {
						if (j == k) {
							continue;
						}
						double[] elementLk = elementsL[k];
						double[] elementLj = elementsL[j];
						double dd = distanceMatrixL[k][j] - distanceMatrixH[k][j];
						newElement[d] += elementLk[d] + dd * (elementLj[d] - elementLk[d]);
					}
					newElement[d] /= (n - 1);
				}
				newElementsL[k] = newElement;
			}
			elementsL = newElementsL;
		}
		return elementsL;
	}

}
