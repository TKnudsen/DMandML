package com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrixParallel;
import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrixStatistics;
import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.IDistanceMatrix;
import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVectors;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.Double.DoubleDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.Double.EuclideanDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.transformations.dimensionalityReduction.DimensionalityReduction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.dimensionalityReduction.DimensionalityReductions;

/**
 * <p>
 * Title: MDS
 * </p>
 * 
 * <p>
 * Description: Multi-dimensional Scaling (MDS) is a non-linear dimension
 * reduction algorithm proposed by Joseph B. KRUSKAL in 1964: "Multidimensional
 * scaling by optimizing goodness of fit to a nonmetric hypothesis" In
 * Psychometrika 29, 1 (1964).
 * 
 * The principal idea is to iteratively optimize the low-dimensional (often 2D)
 * positions of objects with respect to pairwise distances in the original space
 * and a stress-minimization function.
 * 
 * <p>
 * Copyright: (c) 2016-2020 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard, Christian Ritter
 * @version 1.07
 */
public class MDS<X extends AbstractFeatureVector<?, ?>> extends DimensionalityReduction<X> {

	/**
	 * Euclidean distance metric for double[]
	 */
	private final DoubleDistanceMeasure doubleDistanceMeasure = new EuclideanDistanceMeasure();

	/**
	 * distance matrix, internally used for the low-dimensional manifold
	 * optimization
	 */
	private IDistanceMatrix<X> distanceMatrix;

	private double dmMin;
	private double dmMax;

	// TODO integrate alternative termination criterion
	private int maxIterations = 1000;
	private Double lastStress = null;
	private double stressDifferenceRateToBreak = 0.000001;

	protected boolean printProgress = false;

	private double normMinMax(double val) {
		return (val - getDmMin()) / (dmMax - getDmMin());
	}

	public MDS(List<X> featureVectors, IDistanceMeasure<X> distanceMeasure, int outputDimensionality) {
		if (distanceMeasure == null)
			throw new IllegalArgumentException("Distance measure was null");

		this.featureVectors = featureVectors;
		this.distanceMeasure = distanceMeasure;

		this.outputDimensionality = outputDimensionality;
	}

	public MDS(IDistanceMatrix<X> distanceMatrix, int outputDimensionality) {
		if (distanceMatrix == null)
			throw new IllegalArgumentException("Multidimensional Scaling: distance matrix is null");

		this.distanceMatrix = distanceMatrix;
		this.featureVectors = distanceMatrix.getElements();

		this.outputDimensionality = outputDimensionality;
	}

	/**
	 * sum of squared errors between distance matrix and pointDistances.
	 * 
	 * @param distanceMatrix
	 * @param pointDistances
	 * @return
	 */
	private double calculateStress(IDistanceMatrix<X> distanceMatrix, double[][] pointDistances,
			List<? extends X> inputObjects) {
		double stress = 0;
		for (int i = 0; i < pointDistances.length; i++) {
			for (int j = 0; j < pointDistances[0].length; j++) {
				double dij = pointDistances[i][j];
				double Dij = normMinMax(distanceMatrix.getDistance(inputObjects.get(i), inputObjects.get(j)));
				stress += Math.pow(Dij - dij, 2);
			}
		}
		return stress;
	}

	/**
	 * calculates pairwise distances of the given NumericFeatureVectors. These
	 * distances are used to assess the distance relations of the original data.
	 * 
	 * @param fvs
	 * @return
	 */
	private void calculateDistanceMatrix(List<? extends X> fvs) {
		distanceMatrix = new DistanceMatrixParallel<>(fvs, distanceMeasure);
	}

	/**
	 * calculates the distances between points in the low-dimensional manifold.
	 * 
	 * @param lowDimensionalPoints
	 * @return
	 */
	private double[][] calculatePointDistances(List<double[]> lowDimensionalPoints) {
		double[][] distanceMatrix = new double[lowDimensionalPoints.size()][lowDimensionalPoints.size()];

		for (int x = 0; x < lowDimensionalPoints.size(); x++) {
			distanceMatrix[x][x] = 0;
			for (int y = 0; y < x; y++) {
				double distance = doubleDistanceMeasure.getDistance(lowDimensionalPoints.get(x),
						lowDimensionalPoints.get(y));
				distanceMatrix[x][y] = distance;
				distanceMatrix[y][x] = distance;
			}
		}

		return distanceMatrix;
	}

	/**
	 * initialize points of the low-dimensional embedding
	 * 
	 * @param dimensionality
	 * @return
	 */
	private List<double[]> initializeLowDimensionalPoints(int dimensionality, int count) {
		List<double[]> lowDimensionalPoints = new ArrayList<>(count);

		Random random = new Random();

		for (int i = 0; i < count; i++) {
			double[] point = new double[dimensionality];
			for (int dim = 0; dim < dimensionality; dim++)
				point[dim] = random.nextDouble();
			lowDimensionalPoints.add(point);
		}

		return lowDimensionalPoints;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	public boolean isPrintProgress() {
		return printProgress;
	}

	public void setPrintProgress(boolean printProgress) {
		this.printProgress = printProgress;
	}

	@Override
	public void calculateDimensionalityReduction() {
		if (featureVectors == null)
			throw new NullPointerException("MDS: feature vectors null");

		mapping = new LinkedHashMap<>();

		if (distanceMatrix == null)
			calculateDistanceMatrix(featureVectors);

		if (distanceMatrix == null)
			throw new IllegalArgumentException("MDS: wrong input.");

		DistanceMatrixStatistics<X> distanceMatrixStatistics = new DistanceMatrixStatistics<>(distanceMatrix);
		dmMin = distanceMatrixStatistics.getMinDistance();
		dmMax = distanceMatrixStatistics.getMaxDistance();

		// initialize points of the low-dimensional embedding
		List<double[]> lowDimensionalPoints = initializeLowDimensionalPoints(outputDimensionality,
				featureVectors.size());

		boolean breakCalculation = false;
		for (int iteration = 0; iteration < maxIterations; iteration++) {
			if (breakCalculation)
				break;

			double[][] pointDistances = calculatePointDistances(lowDimensionalPoints);

			double stress = calculateStress(distanceMatrix, pointDistances, featureVectors);
			if (Double.isNaN(stress)) {
				System.err.println("MDS: NaN occurrence in calculation process.");
				breakCalculation = true;
			} else if (lastStress != null && (Math.abs(lastStress / stress - 1)) < stressDifferenceRateToBreak) {
				System.out.println("MDS: calulation finished. Stress difference smaller than "
						+ stressDifferenceRateToBreak + " %");
				breakCalculation = true;
			}
			lastStress = stress;

			// calculate Kruskal's stress for every pair of objects
			if (printProgress) {
				System.out.println("MDS: iteration " + (iteration + 1) + ":Kruskal's stress = " + stress);
			}

			// optimize low-dimensional embedding
			double[][] newPoints = new double[lowDimensionalPoints.size()][outputDimensionality];
			for (int i = 0; i < lowDimensionalPoints.size(); i++) {
				double[] newPointCoordinates = new double[outputDimensionality];
				for (int d = 0; d < outputDimensionality; d++) {
					for (int j = 0; j < lowDimensionalPoints.size(); j++) {
						if (j == i)
							continue;
						newPointCoordinates[d] += lowDimensionalPoints.get(i)[d] + (pointDistances[i][j]
								- normMinMax(distanceMatrix.getDistance(featureVectors.get(i), featureVectors.get(j))))
								* (lowDimensionalPoints.get(j)[d] - lowDimensionalPoints.get(i)[d]);
					}
					newPointCoordinates[d] /= (lowDimensionalPoints.size() - 1);
				}
				newPoints[i] = newPointCoordinates;
			}

			// assign the new coordinates
			for (int i = 0; i < lowDimensionalPoints.size(); i++)
				for (int j = 0; j < lowDimensionalPoints.size(); j++)
					lowDimensionalPoints.set(i, newPoints[i]);
		}

		for (int i = 0; i < featureVectors.size(); i++) {
			X inputFeatureVector = featureVectors.get(i);

			NumericalFeatureVector outputFeatureVector = NumericalFeatureVectors.createNumericalFeatureVector(
					lowDimensionalPoints.get(i), inputFeatureVector.getName(), inputFeatureVector.getDescription());

			DimensionalityReductions.synchronizeFeatureVectorMetadata(inputFeatureVector, outputFeatureVector);

			mapping.put(inputFeatureVector, outputFeatureVector);
		}
	}

	public double getDmMin() {
		return dmMin;
	}

	public void setDmMin(double dmMin) {
		this.dmMin = dmMin;
	}

	public double getStressDifferenceRateToBreak() {
		return stressDifferenceRateToBreak;
	}

	public void setStressDifferenceRateToBreak(double stressDifferenceRatioToBreak) {
		this.stressDifferenceRateToBreak = stressDifferenceRatioToBreak;
	}
}
