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
 * Assigns interestingness scores depending on depending on the minimum distance
 * between an instance and all other instances outside of its cluster. I.e. it
 * takes each cluster's separation into account. The higher the minimum distance
 * for an element the higher its score.
 * </p>
 * 
 * Single instance extension of: Dunn, J. (1974). Well-separated clusters and
 * optimal fuzzy partitions. Cybernetics and Systems, 4(1), 95-104.
 * </p>
 * 
 * @version 1.01
 */
public class ClusteringDunnIndexSeparationDegreeOfInterestingnessFunction<FV>
		extends ClusteringBasedDegreeOfInterestingnessFunction<FV> {

	private final IDistanceMeasure<FV> distanceMeasure;

	public ClusteringDunnIndexSeparationDegreeOfInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult, IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResult, true);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	public ClusteringDunnIndexSeparationDegreeOfInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements, IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResult, retrieveNearestClusterForUnassignedElements);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv) {
		ICluster<FV> cluster = getClusteringResult().getCluster(fv);
		if (cluster == null && isRetrieveNearestClusterForUnassignedElements())
			cluster = getClusteringResult().retrieveCluster(fv);

		if (cluster == null)
			throw new IllegalArgumentException(
					"ClusteringDunnIndexSeparationDegreeOfInterestingnessFunction: Instance has to be part of a cluster.");
		double separation = Double.MAX_VALUE;
		for (ICluster<FV> c : getClusteringResult().getClusters()) {
			if (c.equals(cluster))
				continue;
			for (FV f : c.getElements()) {
				separation = Math.min(separation, distanceMeasure.applyAsDouble(fv, f));
			}
		}
		// values are inverted as high separation is good
		return separation;
	}

	@Override
	public String getName() {
		return "Cluster Dunn's Index Separation [" + getClusteringResult().getName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the minimum distance between an instance and all other instances outside of its cluster.";
	}
}
