package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.compactness;

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
 * Calculates interestingness scores of elements according to the variance of
 * clusters.
 * </p>
 * 
 * Determines the compactness of groups of instances (clusters) using a variance
 * criterion. This measure can be used to favor instances for labeling in either
 * compact or spatially distributed groups.
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
 * @version 1.04
 */
public class ClusteringClusterVarianceInterestingnessFunction<FV>
		extends ClusteringBasedDegreeOfInterestingnessFunction<FV> {

	public ClusteringClusterVarianceInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {

		super(clusteringResult, retrieveNearestClusterForUnassignedElements);
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv) {

		ICluster<FV> cluster = getClusteringResult().getCluster(fv);

		if (cluster == null)
			if (isRetrieveNearestClusterForUnassignedElements())
				cluster = getClusteringResult().retrieveCluster(fv);

		double centroidVariance = Double.NaN;
		if (cluster == null)
			System.err.println(getName() + ": no cluster assigned to element (" + fv + ")");
		else
			centroidVariance = cluster.getVariance();

		if (Double.isNaN(centroidVariance))
			throw new IllegalArgumentException(
					getName() + ": not able to calculate the variance of the winning cluster" + fv);

		// largest variance wins. otherwise it would be somewhat compactness.
		return centroidVariance;
	}

	// TODO rename to Cluster Variance [getClusteringResult().getName()]
	@Override
	public String getName() {
		return "Cluster Variance [" + getClusteringResult().getName() + "]";
	}

	@Override
	public String getDescription() {
		return "assings interestingnes scores with respect to the variance of winning clusters";
	}

}
