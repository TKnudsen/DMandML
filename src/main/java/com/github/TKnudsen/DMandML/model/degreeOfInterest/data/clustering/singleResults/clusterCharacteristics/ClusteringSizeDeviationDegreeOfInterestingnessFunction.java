package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.singleResults.clusterCharacteristics;

import java.util.LinkedHashMap;
import java.util.Map;

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
 * Assigns an interestingness score based on the count of instances in each
 * cluster. All elements in one cluster will be assigned the same score. Both,
 * higher and lower than average counts, will result in a high score.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.04
 */
public class ClusteringSizeDeviationDegreeOfInterestingnessFunction<FV>
		extends ClusteringBasedDegreeOfInterestingnessFunction<FV> {

	Map<ICluster<FV>, Double> interestingnessMapping;

	public ClusteringSizeDeviationDegreeOfInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult) {
		super(clusteringResult, true);
		buildInterestingnessMapping();
	}

	public ClusteringSizeDeviationDegreeOfInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {
		super(clusteringResult, retrieveNearestClusterForUnassignedElements);
		buildInterestingnessMapping();
	}

	private void buildInterestingnessMapping() {
		interestingnessMapping = new LinkedHashMap<>();

		// count elements per cluster and find min and max size
		double mean = 0.0;
		for (ICluster<FV> cluster : getClusteringResult().getClusters()) {
			double d = 1.0 * cluster.size();
			interestingnessMapping.put(cluster, d);
			mean += d;
		}

		// subtract mean and make all values positive
		mean /= getClusteringResult().getClusters().size();
		for (ICluster<FV> cluster : interestingnessMapping.keySet()) {
			interestingnessMapping.put(cluster, Math.abs(interestingnessMapping.get(cluster) - mean));
		}
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv) {
		ICluster<FV> cluster = getClusteringResult().getCluster(fv);
		if (cluster == null && isRetrieveNearestClusterForUnassignedElements())
			cluster = getClusteringResult().retrieveCluster(fv);

		if (cluster == null)
			throw new IllegalArgumentException(getName() + ": Instance has to be part of a cluster.");

		return interestingnessMapping.get(cluster);
	}

	@Override
	public String getName() {
		return "Cluster Size Deviation [" + getClusteringResult().getName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the amount of instances in a cluster.";
	}
}
