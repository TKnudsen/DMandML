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
 * Copyright: (c) 2016-2019 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Assigns an interestingness score based on the size of the cluster
 * affiliation. The smaller the cluster size the higher the interestingness
 * score.
 * </p>
 * 
 * @author Juergen Bernard
 * 
 * @version 1.01
 */
public class ClusteringSmallClusterSizeDegreeOfInterestFunction<FV>
		extends ClusteringBasedDegreeOfInterestingnessFunction<FV> {

	Map<ICluster<FV>, Double> interestingnessMapping;

	public ClusteringSmallClusterSizeDegreeOfInterestFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult) {
		super(clusteringResult, true);
		buildInterestingnessMapping();
	}

	public ClusteringSmallClusterSizeDegreeOfInterestFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {
		super(clusteringResult, retrieveNearestClusterForUnassignedElements);
		buildInterestingnessMapping();
	}

	private void buildInterestingnessMapping() {
		interestingnessMapping = new LinkedHashMap<>();

		// invert size
		for (ICluster<FV> cluster : getClusteringResult().getClusters()) {
			double d = cluster.size();
			interestingnessMapping.put(cluster, -d);
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
		return "Clustering Small Cluster Size [" + getClusteringResult().getName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the size of the corresponding cluster.";
	}
}
