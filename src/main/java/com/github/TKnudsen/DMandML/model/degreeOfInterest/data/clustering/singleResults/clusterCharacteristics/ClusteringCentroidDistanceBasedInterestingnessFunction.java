package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.clusterCharacteristics;

import com.github.TKnudsen.DMandML.data.cluster.ClusteringResultTools;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.ClusteringBasedDegreeOfInterestingnessFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores of elements on the basis of their distances
 * to the winning centroids of a pre-defined clustering result.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.03
 */
public class ClusteringCentroidDistanceBasedInterestingnessFunction<FV>
		extends ClusteringBasedDegreeOfInterestingnessFunction<FV> {

	public ClusteringCentroidDistanceBasedInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult) {
		this(clusteringResult, true);
	}

	public ClusteringCentroidDistanceBasedInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {

		super(clusteringResult, retrieveNearestClusterForUnassignedElements);
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv) {

		double distanceToNearestClusterCentroid = ClusteringResultTools.getDistanceToNearestClusterCentroid(
				getClusteringResult(), fv, isRetrieveNearestClusterForUnassignedElements());

		if (Double.isNaN(distanceToNearestClusterCentroid))
			throw new IllegalArgumentException(
					getName() + ": clusteringresult did not yield a distance to the neares cluster for instance " + fv);

		// invert scores. will then be normalized to 0...1 in the correct order
		return -distanceToNearestClusterCentroid;
	}

	@Override
	public String getName() {
		return "Cluster Centroid Distance [" + getClusteringResult().getName() + "]";
	}

	@Override
	public String getDescription() {
		return "assings interestingnes scores with respect to the proximity to a cluster centroid";
	}

}
