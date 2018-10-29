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
 * Calculates interestingness scores of elements on the basis of their distances
 * to the centroids of a pre-defined clustering result.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.03
 */
public class ClusteringClusterCrispnessInterestingnessFunction<FV>
		extends ClusteringBasedDegreeOfInterestingnessFunction<FV> {

	public ClusteringClusterCrispnessInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult) {
		this(clusteringResult, true);
	}

	public ClusteringClusterCrispnessInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {

		super(clusteringResult, retrieveNearestClusterForUnassignedElements);
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv) {

		ClusterDistanceDistribution<FV, ? extends ICluster<FV>> clusterDistances = ClusteringResultTools
				.getClusterDistances(getClusteringResult(), fv, true);

		StatisticsSupport statistics = new StatisticsSupport(clusterDistances.getClusterDistances().values());
		double winnerConficence = statistics.getMax();

		// winnerConficence =
		// Entropy.calculateEntropy(clusterDistances.getClusterDistances().values());

		if (Double.isNaN(winnerConficence))
			throw new IllegalArgumentException(
					getName() + ": not able to calculate the confidence of the winning cluster" + fv);

		return winnerConficence;
	}

	@Override
	public String getName() {
		return "Cluster Crispness [" + getClusteringResult().getName() + "]";
	}

	@Override
	public String getDescription() {
		return "assings interestingnes scores depending on how crisp an instance can be assigned to a single cluster";
	}

}
