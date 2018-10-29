package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.clusterCharacteristics;

import java.util.ArrayList;
import java.util.Collection;

import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.IClusteringBasedDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.ClusteringCommitteeBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.clusterCharacteristics.ClusteringClusterCrispnessInterestingnessFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores of elements on the basis of their distances
 * to the centroids of a pre-defined clustering result.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.05
 */
public class ClusteringCommitteeClusterCrispnessInterestingnessFunction<FV>
		extends ClusteringCommitteeBasedInterestingnessFunction<FV> {

	public ClusteringCommitteeClusterCrispnessInterestingnessFunction(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults) {
		this(clusteringResults, true);
	}

	public ClusteringCommitteeClusterCrispnessInterestingnessFunction(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {

		super(clusteringResults, retrieveNearestClusterForUnassignedElements);
	}

	@Override
	protected Collection<IClusteringBasedDegreeOfInterestingnessFunction<FV>> createClusteringBasedDOIs(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults) {

		Collection<IClusteringBasedDegreeOfInterestingnessFunction<FV>> clusteringResultDOIs = new ArrayList<>();

		for (IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult : clusteringResults)
			clusteringResultDOIs.add(new ClusteringClusterCrispnessInterestingnessFunction<>(clusteringResult,
					isRetrieveNearestClusterForUnassignedElements()));

		return clusteringResultDOIs;
	}

	@Override
	public String getDescription() {
		return "assings interestingnes scores of multiple clustering-based DOIs, depending on how crisp an instance can be assigned to a single cluster";
	}

	@Override
	public String getName() {
		return "Cluster Committee Crispness";
	}

}
