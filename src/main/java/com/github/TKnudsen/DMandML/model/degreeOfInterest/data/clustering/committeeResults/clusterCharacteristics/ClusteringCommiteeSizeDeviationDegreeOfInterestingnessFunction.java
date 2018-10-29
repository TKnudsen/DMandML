package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.clusterCharacteristics;

import java.util.ArrayList;
import java.util.Collection;

import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.IClusteringBasedDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.ClusteringCommitteeBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.clusterCharacteristics.ClusteringSizeDeviationDegreeOfInterestingnessFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Assigns an interestingness score based on the count of instances in each
 * cluster. All elements in one cluster will be assigned the same score. Both,
 * higher and lower than average counts, will result in a high score.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.04
 */
public class ClusteringCommiteeSizeDeviationDegreeOfInterestingnessFunction<FV>
		extends ClusteringCommitteeBasedInterestingnessFunction<FV> {

	public ClusteringCommiteeSizeDeviationDegreeOfInterestingnessFunction(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults) {
		this(clusteringResults, true);
	}

	public ClusteringCommiteeSizeDeviationDegreeOfInterestingnessFunction(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {
		super(clusteringResults, retrieveNearestClusterForUnassignedElements);
	}

	@Override
	protected Collection<IClusteringBasedDegreeOfInterestingnessFunction<FV>> createClusteringBasedDOIs(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults) {

		Collection<IClusteringBasedDegreeOfInterestingnessFunction<FV>> clusteringResultDOIs = new ArrayList<>();

		for (IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult : clusteringResults)
			clusteringResultDOIs.add(new ClusteringSizeDeviationDegreeOfInterestingnessFunction<>(clusteringResult,
					isRetrieveNearestClusterForUnassignedElements()));

		return clusteringResultDOIs;
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the amount of instances in a cluster.";
	}

	@Override
	public String getName() {
		return "Cluster Committee Size Deviation";
	}
}
