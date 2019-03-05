package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.separation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.IClusteringBasedDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.ClusteringCommitteeBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.separation.ClusteringSilhuetteSeparationDegreeOfInterestingnessFunction;

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
 * </p>
 * 
 * Based on: Peter J. Rousseeuw (1987). "Silhouettes: a Graphical Aid to the
 * Interpretation and Validation of Cluster Analysis". Computational and Applied
 * Mathematics. 20: p. 53-65. doi:10.1016/0377-0427(87)90125-7.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.02
 */
public class ClusteringCommiteeSilhuetteSeparationDegreeOfInterestingnessFunction<FV>
		extends ClusteringCommitteeBasedInterestingnessFunction<FV> {

	private final IDistanceMeasure<FV> distanceMeasure;

	public ClusteringCommiteeSilhuetteSeparationDegreeOfInterestingnessFunction(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults,
			IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResults, true);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	public ClusteringCommiteeSilhuetteSeparationDegreeOfInterestingnessFunction(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements, IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResults, retrieveNearestClusterForUnassignedElements);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	protected Collection<IClusteringBasedDegreeOfInterestingnessFunction<FV>> createClusteringBasedDOIs(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults) {

		Collection<IClusteringBasedDegreeOfInterestingnessFunction<FV>> clusteringResultDOIs = new ArrayList<>();

		for (IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult : clusteringResults)
			clusteringResultDOIs.add(new ClusteringSilhuetteSeparationDegreeOfInterestingnessFunction<>(
					clusteringResult, isRetrieveNearestClusterForUnassignedElements(), distanceMeasure));

		return clusteringResultDOIs;
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the dissimilarity between each instance and the nearest cluster (that it is not part of).";
	}

	@Override
	public String getName() {
		return "Cluster Committee Silhuette Separation";
	}
}
