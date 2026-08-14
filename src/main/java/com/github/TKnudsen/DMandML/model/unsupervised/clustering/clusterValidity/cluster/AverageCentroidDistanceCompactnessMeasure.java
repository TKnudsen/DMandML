package com.github.TKnudsen.DMandML.model.unsupervised.clustering.clusterValidity.cluster;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.ComplexDataObject.model.tools.StatisticsSupport;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;

/**
 * <p>
 * Compactess based on the average distance to the cluster centroid.
 * </p>
 *
 * @version 1.01
 * @since 2016
 */
public class AverageCentroidDistanceCompactnessMeasure<FV extends IFeatureVectorObject<?, ?>> extends ClusterCompactnessMeasure<FV> {

	@Override
	public double getMeasure(Cluster<FV> cluster) {

		StatisticsSupport distancesToCentroid = this.getDistancesToCentroid(cluster);

		return distancesToCentroid.getMean();
	}

}
