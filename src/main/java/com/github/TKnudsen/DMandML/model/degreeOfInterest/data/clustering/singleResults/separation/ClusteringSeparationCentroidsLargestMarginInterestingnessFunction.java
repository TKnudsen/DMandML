package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.separation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.ClusteringBasedDegreeOfInterestingnessFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2019 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Assigns an interestingness score to each instance based on the margin's
 * magnitude (between the winning and the second winning centroid).
 * </p>
 * 
 * @version 1.01
 */
public class ClusteringSeparationCentroidsLargestMarginInterestingnessFunction<FV>
		extends ClusteringBasedDegreeOfInterestingnessFunction<FV> {

	private final IDistanceMeasure<FV> distanceMeasure;

	public ClusteringSeparationCentroidsLargestMarginInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult, IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResult, true);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	public ClusteringSeparationCentroidsLargestMarginInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements, IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResult, retrieveNearestClusterForUnassignedElements);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv) {

		List<Double> centroidDistances = new ArrayList<>();

		for (ICluster<FV> cluster : getClusteringResult().getClusters())
			centroidDistances.add(distanceMeasure.applyAsDouble(fv, cluster.getCentroid().getData()));

		if (centroidDistances.size() < 2)
			return 0.0;

		Collections.sort(centroidDistances);

		double margin = centroidDistances.get(1) - centroidDistances.get(0);

		// largest margin shall win
		return margin;
	}

	@Override
	public String getName() {
		return "Cluster Separation Centroid Largest Margin [" + getClusteringResult().getName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the distances between the centroid margins.";
	}
}
