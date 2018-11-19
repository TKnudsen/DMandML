package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.clusterCharacteristics;

import com.github.TKnudsen.ComplexDataObject.model.tools.StatisticsSupport;

import com.github.TKnudsen.DMandML.data.cluster.ClusterDistanceDistribution;
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
 * According to: Juergen Bernard, Matthias Zeppelzauer, Markus Lehmann, Martin
 * Mueller, and Michael Sedlmair: Towards User-Centered Active Learning
 * Algorithms. Eurographics Conference on Visualization (EuroVis), Computer
 * Graphics Forum (CGF), 2018.
 * </p>
 * 
 * Calculates interestingness scores of elements on the basis of their distances
 * to the centroids of a pre-defined clustering result. For every instance a
 * {@link ClusterDistanceDistribution} is calculated. The maximum cluster
 * probability defines the interestingness score. As such, the implementation
 * combines the "Clustering (CLU)" building block with Class Prediction (CP)
 * (applied for clusters, not classes).
 * </p>
 * 
 * @author Juergen Bernard
 * 
 * @version 1.05
 */
public class ClusteringClusterLikelihoodInterestingnessFunction<FV>
		extends ClusteringBasedDegreeOfInterestingnessFunction<FV> {

	public ClusteringClusterLikelihoodInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult) {
		this(clusteringResult, true);
	}

	public ClusteringClusterLikelihoodInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {

		super(clusteringResult, retrieveNearestClusterForUnassignedElements);
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv) {

		ClusterDistanceDistribution<FV, ? extends ICluster<FV>> clusterDistances = ClusteringResultTools
				.getClusterDistances(getClusteringResult(), fv, true);

		StatisticsSupport statistics = new StatisticsSupport(clusterDistances.getClusterDistances().values());
		double winnerConfidence = statistics.getMax();

		// winnerConficence =
		// Entropy.calculateEntropy(clusterDistances.getClusterDistances().values());

		if (Double.isNaN(winnerConfidence))
			throw new IllegalArgumentException(
					getName() + ": not able to calculate the likelihood of the winning cluster" + fv);

		return winnerConfidence;
	}

	@Override
	public String getName() {
		return "Cluster Likelihood [" + getClusteringResult().getName() + "]";
	}

	@Override
	public String getDescription() {
		return "assings interestingnes scores depending on how likely an instance can be assigned to a single cluster";
	}

}
