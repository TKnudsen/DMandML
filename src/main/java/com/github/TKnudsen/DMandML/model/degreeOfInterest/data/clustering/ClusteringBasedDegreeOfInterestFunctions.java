package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;

import java.util.List;
import java.util.function.Supplier;

import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.clusterCharacteristics.ClusteringCentroidDistanceBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.clusterCharacteristics.ClusteringCentroidProximityBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.clusterCharacteristics.ClusteringClusterLikelihoodInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.clusterCharacteristics.ClusteringLargeClusterSizeDegreeOfInterestFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.clusterCharacteristics.ClusteringSizeDeviationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.clusterCharacteristics.ClusteringSmallClusterSizeDegreeOfInterestFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.compactness.ClusteringClusterVarianceInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.compactness.ClusteringDunnIndexCompactnessDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.compactness.ClusteringSilhuetteCompactnessDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.separation.ClusteringDunnIndexSeparationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.separation.ClusteringOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.separation.ClusteringSeparationCentroidsLargestMarginInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.separation.ClusteringSeparationCentroidsSmallestInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.separation.ClusteringSilhuetteSeparationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.IClusteringAlgorithm;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.enums.LinkageStrategy;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.AffinityPropagation;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.Canopy;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.Cobweb;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.DBScan;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.ExpectationMaximization;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.FarthestFirst;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.HierarchicalClustering;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.KMeans;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.OPTICS;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.XMeans;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Creates instances of different clustering-based
 * {@link IDegreeOfInterestFunction} implementations.
 * 
 * Uses a variety of clustering algorithms to create a series of different
 * instances
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.03
 */
public class ClusteringBasedDegreeOfInterestFunctions {

	// NEW NICE AND SHINY METHODS

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateClusterSizeBasedInterestingnessFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringSizeDeviationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateClusteringSmallClusterSizeBasedDegreeOfInterestFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringSmallClusterSizeDegreeOfInterestFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateClusteringLargeClusterSizeBasedDegreeOfInterestFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringLargeClusterSizeDegreeOfInterestFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateClusterCrispnessBasedInterestingnessFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringClusterLikelihoodInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateClusterCentroidDistanceBasedInterestingnessFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringCentroidDistanceBasedInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateClusterCentroidProximityBasedInterestingnessFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringCentroidProximityBasedInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateClusterWinningClusterVarianceBasedInterestingnessFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringClusterVarianceInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateDunnIndexCompactnessBasedInterestingnessFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringDunnIndexCompactnessDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements, distanceMeasure);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateDunnIndexSeparationBasedInterestingnessFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringDunnIndexSeparationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements, distanceMeasure);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateOtherCentroidsSeparationBasedInterestingnessFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements, distanceMeasure);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateSeparationCentroidsLargestMarginInterestingnessFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringSeparationCentroidsLargestMarginInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements, distanceMeasure);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateSeparationCentroidsSmallestMarginInterestingnessFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringSeparationCentroidsSmallestInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements, distanceMeasure);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateSilhuetteCompactnessBasedInterestingnessFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringSilhuetteCompactnessDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements, distanceMeasure);

		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateSilhuetteSeparationBasedInterestingnessFunction(
			IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringSilhuetteSeparationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements, distanceMeasure);

		return degreeOfInterestFunction;
	}

	// CENTROID DISTANCE BASED

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCountCompactnessBasedAffinityPropagation(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double quantile, double lambda,
			int convergence, int maxiter, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new AffinityPropagation(featureVectors.get(), quantile,
				lambda, convergence, maxiter);

		clusterer.calculateClustering();

		return instantiateClusterCountBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCountCompactnessBasedCanopy(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Canopy(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCountBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCountCompactnessBasedCobweb(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double acuity, double cutoff,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Cobweb(acuity, cutoff, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCountBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCountCompactnessBasedDBScan(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new DBScan(featureVectors.get(), epsilon, minPoints);

		clusterer.calculateClustering();

		return instantiateClusterCountBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCountCompactnessBasedExpectationMaximization(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new ExpectationMaximization(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCountBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCountCompactnessBasedFarthestFirst(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new FarthestFirst(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCountBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCountCompactnessBasedHierarchicalClustering(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			LinkageStrategy linkageStrategy, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new HierarchicalClustering(k, linkageStrategy,
				featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCountBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCountCompactnessBasedKMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new KMeans(k, 42, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCountBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCountCompactnessBasedOPTICS(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new OPTICS(epsilon, minPoints, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCountBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCountCompactnessBasedXMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int minK, int maxK,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new XMeans(minK, maxK, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCountBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCrispnessBasedAffinityPropagation(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double quantile, double lambda,
			int convergence, int maxiter, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new AffinityPropagation(featureVectors.get(), quantile,
				lambda, convergence, maxiter);

		clusterer.calculateClustering();

		return instantiateClusterCrispnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	// CLUSTER CRISPNESS BASED

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCrispnessBasedCanopy(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Canopy(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCrispnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCrispnessBasedCobweb(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double acuity, double cutoff,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Cobweb(acuity, cutoff, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCrispnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCrispnessBasedDBScan(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new DBScan(featureVectors.get(), epsilon, minPoints);

		clusterer.calculateClustering();

		return instantiateClusterCrispnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCrispnessBasedExpectationMaximization(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new ExpectationMaximization(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCrispnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCrispnessBasedFarthestFirst(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new FarthestFirst(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCrispnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCrispnessBasedHierarchicalClustering(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			LinkageStrategy linkageStrategy, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new HierarchicalClustering(k, linkageStrategy,
				featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCrispnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCrispnessBasedKMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new KMeans(k, 42, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCrispnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCrispnessBasedOPTICS(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new OPTICS(epsilon, minPoints, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCrispnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusterCrispnessBasedXMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int minK, int maxK,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new XMeans(minK, maxK, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusterCrispnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCentroidDistanceBasedAffinityPropagation(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double quantile, double lambda,
			int convergence, int maxiter, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new AffinityPropagation(featureVectors.get(), quantile,
				lambda, convergence, maxiter);

		clusterer.calculateClustering();

		return instantiateClusteringCentroidDistanceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCentroidDistanceBasedCanopy(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Canopy(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringCentroidDistanceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	// WINNING CLUSTER VARIANCE BASED

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCentroidDistanceBasedCobweb(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double acuity, double cutoff,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Cobweb(acuity, cutoff, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringCentroidDistanceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCentroidDistanceBasedDBScan(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new DBScan(featureVectors.get(), epsilon, minPoints);

		clusterer.calculateClustering();

		return instantiateClusteringCentroidDistanceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCentroidDistanceBasedExpectationMaximization(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new ExpectationMaximization(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringCentroidDistanceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCentroidDistanceBasedFarthestFirst(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new FarthestFirst(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringCentroidDistanceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCentroidDistanceBasedHierarchicalClustering(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			LinkageStrategy linkageStrategy, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new HierarchicalClustering(k, linkageStrategy,
				featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringCentroidDistanceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCentroidDistanceBasedKMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new KMeans(k, 42, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringCentroidDistanceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCentroidDistanceBasedOPTICS(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new OPTICS(epsilon, minPoints, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringCentroidDistanceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCentroidDistanceBasedXMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int minK, int maxK,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new XMeans(minK, maxK, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringCentroidDistanceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringWinningClusterVarianceBasedAffinityPropagation(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double quantile, double lambda,
			int convergence, int maxiter, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new AffinityPropagation(featureVectors.get(), quantile,
				lambda, convergence, maxiter);

		clusterer.calculateClustering();

		return instantiateClusteringWinningClusterVarianceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringWinningClusterVarianceBasedCanopy(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Canopy(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringWinningClusterVarianceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringWinningClusterVarianceBasedCobweb(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double acuity, double cutoff,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Cobweb(acuity, cutoff, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringWinningClusterVarianceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	// COUNT COMPACTNESS

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringWinningClusterVarianceBasedDBScan(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new DBScan(featureVectors.get(), epsilon, minPoints);

		clusterer.calculateClustering();

		return instantiateClusteringWinningClusterVarianceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringWinningClusterVarianceBasedExpectationMaximization(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new ExpectationMaximization(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringWinningClusterVarianceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringWinningClusterVarianceBasedFarthestFirst(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new FarthestFirst(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringWinningClusterVarianceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringWinningClusterVarianceBasedHierarchicalClustering(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			LinkageStrategy linkageStrategy, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new HierarchicalClustering(k, linkageStrategy,
				featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringWinningClusterVarianceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringWinningClusterVarianceBasedKMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new KMeans(k, 42, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringWinningClusterVarianceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringWinningClusterVarianceBasedOPTICS(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new OPTICS(epsilon, minPoints, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringWinningClusterVarianceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringWinningClusterVarianceBasedXMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int minK, int maxK,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new XMeans(minK, maxK, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateClusteringWinningClusterVarianceBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexCompactnessBasedAffinityPropagation(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double quantile, double lambda,
			int convergence, int maxiter, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new AffinityPropagation(featureVectors.get(), quantile,
				lambda, convergence, maxiter);

		clusterer.calculateClustering();

		return instantiateDunnIndexCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexCompactnessBasedCanopy(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Canopy(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexCompactnessBasedCobweb(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double acuity, double cutoff,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Cobweb(acuity, cutoff, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexCompactnessBasedDBScan(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new DBScan(featureVectors.get(), epsilon, minPoints);

		clusterer.calculateClustering();

		return instantiateDunnIndexCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	// SILHUETTE COMPACTNESS

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexCompactnessBasedExpectationMaximization(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new ExpectationMaximization(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexCompactnessBasedFarthestFirst(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new FarthestFirst(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexCompactnessBasedHierarchicalClustering(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			LinkageStrategy linkageStrategy, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new HierarchicalClustering(k, linkageStrategy,
				featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexCompactnessBasedKMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new KMeans(k, 42, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexCompactnessBasedOPTICS(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new OPTICS(epsilon, minPoints, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexCompactnessBasedXMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int minK, int maxK,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new XMeans(minK, maxK, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexSeparationBasedAffinityPropagation(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double quantile, double lambda,
			int convergence, int maxiter, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new AffinityPropagation(featureVectors.get(), quantile,
				lambda, convergence, maxiter);

		clusterer.calculateClustering();

		return instantiateDunnIndexSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexSeparationBasedCanopy(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Canopy(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexSeparationBasedCobweb(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double acuity, double cutoff,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Cobweb(acuity, cutoff, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexSeparationBasedDBScan(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new DBScan(featureVectors.get(), epsilon, minPoints);

		clusterer.calculateClustering();

		return instantiateDunnIndexSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexSeparationBasedExpectationMaximization(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new ExpectationMaximization(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	// DUNN INDEX COMPACTNESS

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexSeparationBasedFarthestFirst(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new FarthestFirst(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexSeparationBasedHierarchicalClustering(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			LinkageStrategy linkageStrategy, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new HierarchicalClustering(k, linkageStrategy,
				featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexSeparationBasedKMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new KMeans(k, 42, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexSeparationBasedOPTICS(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new OPTICS(epsilon, minPoints, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexSeparationBasedXMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int minK, int maxK,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new XMeans(minK, maxK, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateDunnIndexSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOtherCentroidsSeparationBasedAffinityPropagation(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double quantile, double lambda,
			int convergence, int maxiter, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new AffinityPropagation(featureVectors.get(), quantile,
				lambda, convergence, maxiter);

		clusterer.calculateClustering();

		return instantiateOtherCentroidsSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOtherCentroidsSeparationBasedCanopy(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Canopy(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateOtherCentroidsSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOtherCentroidsSeparationBasedCobweb(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double acuity, double cutoff,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Cobweb(acuity, cutoff, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateOtherCentroidsSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOtherCentroidsSeparationBasedDBScan(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new DBScan(featureVectors.get(), epsilon, minPoints);

		clusterer.calculateClustering();

		return instantiateOtherCentroidsSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOtherCentroidsSeparationBasedExpectationMaximization(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new ExpectationMaximization(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateOtherCentroidsSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOtherCentroidsSeparationBasedFarthestFirst(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new FarthestFirst(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateOtherCentroidsSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	// OTHER CENTROIDS

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOtherCentroidsSeparationBasedHierarchicalClustering(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			LinkageStrategy linkageStrategy, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new HierarchicalClustering(k, linkageStrategy,
				featureVectors.get());

		clusterer.calculateClustering();

		return instantiateOtherCentroidsSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOtherCentroidsSeparationBasedKMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new KMeans(k, 42, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateOtherCentroidsSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOtherCentroidsSeparationBasedOPTICS(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new OPTICS(epsilon, minPoints, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateOtherCentroidsSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOtherCentroidsSeparationBasedXMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int minK, int maxK,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new XMeans(minK, maxK, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateOtherCentroidsSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteCompactnessBasedAffinityPropagation(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double quantile, double lambda,
			int convergence, int maxiter, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new AffinityPropagation(featureVectors.get(), quantile,
				lambda, convergence, maxiter);

		clusterer.calculateClustering();

		return instantiateSilhuetteCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteCompactnessBasedCanopy(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Canopy(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteCompactnessBasedCobweb(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double acuity, double cutoff,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Cobweb(acuity, cutoff, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteCompactnessBasedDBScan(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new DBScan(featureVectors.get(), epsilon, minPoints);

		clusterer.calculateClustering();

		return instantiateSilhuetteCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteCompactnessBasedExpectationMaximization(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new ExpectationMaximization(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteCompactnessBasedFarthestFirst(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new FarthestFirst(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteCompactnessBasedHierarchicalClustering(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			LinkageStrategy linkageStrategy, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new HierarchicalClustering(k, linkageStrategy,
				featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	// SILHUETTE SEPARATION

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteCompactnessBasedKMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new KMeans(k, 42, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteCompactnessBasedOPTICS(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new OPTICS(epsilon, minPoints, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteCompactnessBasedXMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int minK, int maxK,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new XMeans(minK, maxK, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteCompactnessBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteSeparationBasedAffinityPropagation(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double quantile, double lambda,
			int convergence, int maxiter, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new AffinityPropagation(featureVectors.get(), quantile,
				lambda, convergence, maxiter);

		clusterer.calculateClustering();

		return instantiateSilhuetteSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteSeparationBasedCanopy(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Canopy(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteSeparationBasedCobweb(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double acuity, double cutoff,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new Cobweb(acuity, cutoff, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteSeparationBasedDBScan(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new DBScan(featureVectors.get(), epsilon, minPoints);

		clusterer.calculateClustering();

		return instantiateSilhuetteSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteSeparationBasedExpectationMaximization(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new ExpectationMaximization(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteSeparationBasedFarthestFirst(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new FarthestFirst(k, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteSeparationBasedHierarchicalClustering(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			LinkageStrategy linkageStrategy, boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new HierarchicalClustering(k, linkageStrategy,
				featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteSeparationBasedKMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int k,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new KMeans(k, 42, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	// DUNN INDEX SEPARATION

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteSeparationBasedOPTICS(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, double epsilon, int minPoints,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new OPTICS(epsilon, minPoints, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance of the particular clustering-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link NumericalFeatureVector} is used to calculate the
	 * clustering and the resulting {@link IClusteringResult}. The latter is used to
	 * instantiate the DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	@Deprecated
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteSeparationBasedXMeans(
			Supplier<? extends List<? extends NumericalFeatureVector>> featureVectors, int minK, int maxK,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringAlgorithm<NumericalFeatureVector> clusterer = new XMeans(minK, maxK, featureVectors.get());

		clusterer.calculateClustering();

		return instantiateSilhuetteSeparationBasedInterestingnessFunction(clusterer,
				retrieveNearestClusterForUnassignedElements);
	}

	@Deprecated
	private static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateClusterCountBasedInterestingnessFunction(
			IClusteringAlgorithm<NumericalFeatureVector> clusterer,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult = clusterer
				.getClusteringResult();

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringSizeDeviationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements);

		return degreeOfInterestFunction;
	}

	@Deprecated
	private static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateClusterCrispnessBasedInterestingnessFunction(
			IClusteringAlgorithm<NumericalFeatureVector> clusterer,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult = clusterer
				.getClusteringResult();

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringClusterLikelihoodInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements);

		return degreeOfInterestFunction;
	}

	@Deprecated
	private static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateClusteringCentroidDistanceBasedInterestingnessFunction(
			IClusteringAlgorithm<NumericalFeatureVector> clusterer,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult = clusterer
				.getClusteringResult();

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringCentroidDistanceBasedInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements);

		return degreeOfInterestFunction;
	}

	@Deprecated
	private static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateClusteringWinningClusterVarianceBasedInterestingnessFunction(
			IClusteringAlgorithm<NumericalFeatureVector> clusterer,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult = clusterer
				.getClusteringResult();

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringClusterVarianceInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements);

		return degreeOfInterestFunction;
	}

	@Deprecated
	private static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateDunnIndexCompactnessBasedInterestingnessFunction(
			IClusteringAlgorithm<NumericalFeatureVector> clusterer,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult = clusterer
				.getClusteringResult();

		// TODO how to choose distance measure?
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringDunnIndexCompactnessDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements, new EuclideanDistanceMeasure());

		return degreeOfInterestFunction;
	}

	@Deprecated
	private static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateDunnIndexSeparationBasedInterestingnessFunction(
			IClusteringAlgorithm<NumericalFeatureVector> clusterer,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult = clusterer
				.getClusteringResult();

		// TODO how to choose distance measure?
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringDunnIndexSeparationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements, new EuclideanDistanceMeasure());

		return degreeOfInterestFunction;
	}

	@Deprecated
	private static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateOtherCentroidsSeparationBasedInterestingnessFunction(
			IClusteringAlgorithm<NumericalFeatureVector> clusterer,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult = clusterer
				.getClusteringResult();

		// TODO how to choose distance measure?
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements, new EuclideanDistanceMeasure());

		return degreeOfInterestFunction;
	}

	// ClusteringSeparationSmallestCentroidsMarginInterestingnessFunction missing

	@Deprecated
	private static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateSilhuetteCompactnessBasedInterestingnessFunction(
			IClusteringAlgorithm<NumericalFeatureVector> clusterer,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult = clusterer
				.getClusteringResult();

		// TODO how to choose distance measure?
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringSilhuetteCompactnessDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements, new EuclideanDistanceMeasure());

		return degreeOfInterestFunction;
	}

	@Deprecated
	private static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateSilhuetteSeparationBasedInterestingnessFunction(
			IClusteringAlgorithm<NumericalFeatureVector> clusterer,
			boolean retrieveNearestClusterForUnassignedElements) {

		IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>> clusteringResult = clusterer
				.getClusteringResult();

		// TODO how to choose distance measure?
		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClusteringSilhuetteSeparationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResult, retrieveNearestClusterForUnassignedElements, new EuclideanDistanceMeasure());

		return degreeOfInterestFunction;
	}
}
