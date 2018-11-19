package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.compactness;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

import java.util.Objects;

import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.ClusteringBasedDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.clusterValidity.ClusterValidityMethods;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Assigns interestingness scores depending on the dissimilarity of each
 * instance w.r.t. its cluster. I.e. it takes each cluster's compactness into
 * account. The higher the similarity for an element the higher its score.
 * </p>
 * 
 * Based on: Peter J. Rousseeuw (1987). "Silhouettes: a Graphical Aid to the
 * Interpretation and Validation of Cluster Analysis". Computational and Applied
 * Mathematics. 20: p. 53-65. doi:10.1016/0377-0427(87)90125-7.
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
public class ClusteringSilhuetteCompactnessDegreeOfInterestingnessFunction<FV>
		extends ClusteringBasedDegreeOfInterestingnessFunction<FV> {

	private final IDistanceMeasure<FV> distanceMeasure;

	public ClusteringSilhuetteCompactnessDegreeOfInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult, IDistanceMeasure<FV> distanceMeasure) {
		super(clusteringResult, true);

		Objects.requireNonNull(distanceMeasure, getName() + ": Distance measure was null");
		this.distanceMeasure = distanceMeasure;
	}

	public ClusteringSilhuetteCompactnessDegreeOfInterestingnessFunction(
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
					"ClusteringSilhuetteIntraClassDissimilarityDegreeOfInterestingnessFunction: Instance has to be part of a cluster.");
		return -ClusterValidityMethods.silhuetteValidityIntraClassDissimilarity(fv, cluster, distanceMeasure);
	}

	@Override
	public String getName() {
		return "Cluster Silhuette Compactness [" + getClusteringResult().getName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the dissimilarity of each instance w.r.t. its cluster.";
	}

}
