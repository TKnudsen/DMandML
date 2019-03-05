package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults;

import java.util.Collection;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.clusterCharacteristics.ClusteringCommiteeSizeDeviationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.clusterCharacteristics.ClusteringCommitteeCentroidDistanceInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.clusterCharacteristics.ClusteringCommitteeClusterLikelihoodInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.compactness.ClusteringCommiteeDunnIndexCompactnessDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.compactness.ClusteringCommiteeSilhuetteCompactnessDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.compactness.ClusteringCommitteeClusterVarianceInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.separation.ClusteringCommiteeDunnIndexSeparationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.separation.ClusteringCommiteeOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.separation.ClusteringCommiteeSilhuetteSeparationDegreeOfInterestingnessFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Creates instances of clustering committee-based
 * {@link IDegreeOfInterestFunction} implementations.
 * 
 * Uses a set of {@link IClusteringResult}s to create DOI-instances.
 * </p>
 * 
 * @version 1.03
 */
public class ClusteringCommitteeDegreeOfInterestFunctions {

	/**
	 * creates an instance a clustering committee-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given collection of {@link IClusteringResult} is used to instantiate the
	 * DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCommitteeClusterCrispness(
			Collection<IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {

		return new ClusteringCommitteeClusterLikelihoodInterestingnessFunction<NumericalFeatureVector>(clusteringResults,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance a clustering committee-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given collection of {@link IClusteringResult} is used to instantiate the
	 * DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCommitteeWinningCentroidDistance(
			Collection<IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {

		return new ClusteringCommitteeCentroidDistanceInterestingnessFunction<NumericalFeatureVector>(clusteringResults,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance a clustering committee-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given collection of {@link IClusteringResult} is used to instantiate the
	 * DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCommitteeWinningClusterVariance(
			Collection<IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {

		return new ClusteringCommitteeClusterVarianceInterestingnessFunction<NumericalFeatureVector>(clusteringResults,
				retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance a clustering committee-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given collection of {@link IClusteringResult} is used to instantiate the
	 * DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCommitteeCountCompactness(
			Collection<IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {

		return new ClusteringCommiteeSizeDeviationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResults, retrieveNearestClusterForUnassignedElements);
	}

	/**
	 * creates an instance a clustering committee-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given collection of {@link IClusteringResult} is used to instantiate the
	 * DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCommitteeDunnIndexCompactness(
			Collection<IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {

		return new ClusteringCommiteeDunnIndexCompactnessDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResults, retrieveNearestClusterForUnassignedElements, new EuclideanDistanceMeasure());
	}

	/**
	 * creates an instance a clustering committee-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given collection of {@link IClusteringResult} is used to instantiate the
	 * DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCommitteeSilhuetteCompactness(
			Collection<IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {

		return new ClusteringCommiteeSilhuetteCompactnessDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResults, retrieveNearestClusterForUnassignedElements, new EuclideanDistanceMeasure());
	}

	/**
	 * creates an instance a clustering committee-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given collection of {@link IClusteringResult} is used to instantiate the
	 * DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCommitteeOtherCentroidsSeparation(
			Collection<IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {

		return new ClusteringCommiteeOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResults, retrieveNearestClusterForUnassignedElements, new EuclideanDistanceMeasure());
	}

	/**
	 * creates an instance a clustering committee-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given collection of {@link IClusteringResult} is used to instantiate the
	 * DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCommitteeDunnIndexSeparation(
			Collection<IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {

		return new ClusteringCommiteeDunnIndexSeparationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResults, retrieveNearestClusterForUnassignedElements, new EuclideanDistanceMeasure());
	}

	/**
	 * creates an instance a clustering committee-based
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given collection of {@link IClusteringResult} is used to instantiate the
	 * DOI.
	 * 
	 * @param featureVectors
	 * @param k
	 * @param retrieveNearestClusterForUnassignedElements
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClusteringCommitteeSilhuetteSeparation(
			Collection<IClusteringResult<NumericalFeatureVector, ? extends ICluster<NumericalFeatureVector>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {

		return new ClusteringCommiteeSilhuetteSeparationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				clusteringResults, retrieveNearestClusterForUnassignedElements, new EuclideanDistanceMeasure());
	}

}
