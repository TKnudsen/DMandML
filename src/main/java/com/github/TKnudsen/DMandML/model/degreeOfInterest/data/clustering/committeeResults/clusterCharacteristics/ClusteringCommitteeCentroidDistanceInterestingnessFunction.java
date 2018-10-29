package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.clusterCharacteristics;

import java.util.ArrayList;
import java.util.Collection;

import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.IClusteringBasedDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.ClusteringCommitteeBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.clusterCharacteristics.ClusteringCentroidDistanceBasedInterestingnessFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores of elements on the basis of their distances
 * to the centroids of pre-defined {@link IClusteringResult}. Uses an ensemble
 * of multiple clustering results, which will be fed into individual DOIs for
 * single {@link IClusteringResult}s.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.04
 */
public class ClusteringCommitteeCentroidDistanceInterestingnessFunction<FV>
		extends ClusteringCommitteeBasedInterestingnessFunction<FV> {

	public ClusteringCommitteeCentroidDistanceInterestingnessFunction(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults) {
		this(clusteringResults, true);
	}

	public ClusteringCommitteeCentroidDistanceInterestingnessFunction(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {

		super(clusteringResults, retrieveNearestClusterForUnassignedElements);
	}

	@Override
	protected Collection<IClusteringBasedDegreeOfInterestingnessFunction<FV>> createClusteringBasedDOIs(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults) {

		Collection<IClusteringBasedDegreeOfInterestingnessFunction<FV>> clusteringResultDOIs = new ArrayList<>();

		for (IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult : clusteringResults)
			clusteringResultDOIs.add(new ClusteringCentroidDistanceBasedInterestingnessFunction<>(clusteringResult,
					isRetrieveNearestClusterForUnassignedElements()));

		return clusteringResultDOIs;
	}

	@Override
	public String getDescription() {
		return "assings interestingnes scores with respect to the proximity to cluster centroids. Uses multiple clustering results to identify centroid-like areas in the data set.";
	}

	@Override
	public String getName() {
		return "Cluster Committee Centroid Distance";
	}

}
