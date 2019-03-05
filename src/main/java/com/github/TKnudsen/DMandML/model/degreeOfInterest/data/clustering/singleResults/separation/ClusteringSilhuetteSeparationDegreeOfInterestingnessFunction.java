package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.separation;

import java.util.Objects;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.ClusteringBasedDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.clusterValidity.ClusterValidityMethods;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Assigns interestingness scores depending on the dissimilarity of each
 * instance w.r.t. the nearest cluster (that it is not part of). I.e. it takes
 * the cluster's separation into account. The higher the dissimilarity for an
 * element the higher its score.
 * 
 * Based on: Peter J. Rousseeuw (1987). "Silhouettes: a Graphical Aid to the
 * Interpretation and Validation of Cluster Analysis". Computational and Applied
 * Mathematics. 20: p. 53-65. doi:10.1016/0377-0427(87)90125-7.
 * </p>
 * 
 * @version 1.01
 */
public class ClusteringSilhuetteSeparationDegreeOfInterestingnessFunction<FV>
		extends ClusteringBasedDegreeOfInterestingnessFunction<FV> {

	private final IDistanceMeasure<FV> distanceMeasure;

	public ClusteringSilhuetteSeparationDegreeOfInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult, IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResult, true);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	public ClusteringSilhuetteSeparationDegreeOfInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements, IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResult, retrieveNearestClusterForUnassignedElements);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv) {
		// values are inverted as high separation is good
		return ClusterValidityMethods.silhuetteValidityInterClassDissimilarity(fv, getClusteringResult(),
				distanceMeasure);
	}

	@Override
	public String getName() {
		return "Cluster Silhuette Separation [" + getClusteringResult().getName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the dissimilarity between each instance and the nearest cluster (that it is not part of).";
	}
}
