package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.separation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.IClusteringBasedDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.ClusteringCommitteeBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.separation.ClusteringOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Assigns a interestingness score to each instance based on its accumulated
 * distance to all clusters it doesn't belong to.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.02
 */
public class ClusteringCommiteeOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction<FV>
		extends ClusteringCommitteeBasedInterestingnessFunction<FV> {

	private final IDistanceMeasure<FV> distanceMeasure;

	public ClusteringCommiteeOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults,
			IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResults, true);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	public ClusteringCommiteeOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction(
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
			clusteringResultDOIs.add(new ClusteringOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction<>(
					clusteringResult, isRetrieveNearestClusterForUnassignedElements(), distanceMeasure));

		return clusteringResultDOIs;
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the dissimilarity between each instance and the nearest cluster (that it is not part of).";
	}

	@Override
	public String getName() {
		return "Cluster Committee Other Centroids Distance Separation";
	}
}