package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.separation;

import java.util.Objects;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
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
 * Assigns a interestingness score to each instance based on its accumulated
 * distance to all clusters it doesn't belong to.
 * </p>
 * 
 * @version 1.01
 */
public class ClusteringOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction<FV>
		extends ClusteringBasedDegreeOfInterestingnessFunction<FV> {

	private final IDistanceMeasure<FV> distanceMeasure;

	public ClusteringOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult, IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResult, true);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	public ClusteringOtherCentroidsDistanceSeparationDegreeOfInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements, IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResult, retrieveNearestClusterForUnassignedElements);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv) {
		double separation = 0.0;
		for (ICluster<FV> cluster : getClusteringResult().getClusters())
			if (!cluster.contains(fv))
				separation += distanceMeasure.applyAsDouble(fv, cluster.getCentroid().getData());
		// values are inverted as high separation is good
		return separation;
	}

	@Override
	public String getName() {
		return "Cluster Other Centroids Distance Separation [" + getClusteringResult().getName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the dissimilarity between each instance and the nearest cluster (that it is not part of).";
	}
}
