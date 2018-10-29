package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.separation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.IClusteringBasedDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults.ClusteringCommitteeBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.separation.ClusteringDunnIndexSeparationDegreeOfInterestingnessFunction;

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
 * 
 * Single instance extension of: Dunn, J. (1974). Well-separated clusters and
 * optimal fuzzy partitions. Cybernetics and Systems, 4(1), 95-104.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.02
 */
public class ClusteringCommiteeDunnIndexSeparationDegreeOfInterestingnessFunction<FV>
		extends ClusteringCommitteeBasedInterestingnessFunction<FV> {

	private final IDistanceMeasure<FV> distanceMeasure;

	public ClusteringCommiteeDunnIndexSeparationDegreeOfInterestingnessFunction(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults,
			IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResults, true);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	public ClusteringCommiteeDunnIndexSeparationDegreeOfInterestingnessFunction(
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
			clusteringResultDOIs.add(new ClusteringDunnIndexSeparationDegreeOfInterestingnessFunction<>(
					clusteringResult, isRetrieveNearestClusterForUnassignedElements(), distanceMeasure));

		return clusteringResultDOIs;
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the minimum distance between an instance and all other instances outside of its cluster.";
	}

	@Override
	public String getName() {
		return "Cluster Committee Dunn's Index Separation";
	}
}