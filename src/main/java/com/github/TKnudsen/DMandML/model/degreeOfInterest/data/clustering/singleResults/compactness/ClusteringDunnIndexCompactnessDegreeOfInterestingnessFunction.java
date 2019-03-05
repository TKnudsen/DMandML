package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.compactness;

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
 * Assigns interestingness scores depending on depending on the maximum distance
 * between an instance and all other instances within its cluster. I.e. it takes
 * each cluster's compactness into account. The smaller the maximum distance for
 * an element the higher its score.
 * </p>
 * 
 * Single instance extension of: Dunn, J. (1974). Well-separated clusters and
 * optimal fuzzy partitions. Cybernetics and Systems, 4(1), 95-104.
 * </p>
 * 
 * Implementation of the Compactness Estimation (CE) DOI/building block published in: Juergen Bernard,
 * Matthias Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair:
 * Towards User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.02
 */
public class ClusteringDunnIndexCompactnessDegreeOfInterestingnessFunction<FV>
		extends ClusteringBasedDegreeOfInterestingnessFunction<FV> {

	private final IDistanceMeasure<FV> distanceMeasure;

	public ClusteringDunnIndexCompactnessDegreeOfInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult, IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResult, true);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	public ClusteringDunnIndexCompactnessDegreeOfInterestingnessFunction(
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
					"ClusteringDunnIndexCompactnessDegreeOfInterestingnessFunction: Instance has to be part of a cluster.");
		double interestingness = Double.MIN_VALUE;
		for (FV f : cluster.getElements()) {
			if (fv.equals(f))
				continue;
			interestingness = Math.max(interestingness, distanceMeasure.applyAsDouble(fv, f));
		}
		return -interestingness;
	}

	@Override
	public String getName() {
		return "Cluster Dunn's Index Compactness [" + getClusteringResult().getName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the maximum distance between an instance and all other instances within its cluster.";
	}
}
