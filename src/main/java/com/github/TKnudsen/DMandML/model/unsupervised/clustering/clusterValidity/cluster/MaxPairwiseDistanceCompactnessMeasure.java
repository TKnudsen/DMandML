package com.github.TKnudsen.DMandML.model.unsupervised.clustering.clusterValidity.cluster;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrixStatistics;
import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.IDistanceMatrix;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.ComplexDataObject.model.tools.StatisticsSupport;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;

/**
 * <p>
 * Compactess based on the average pairwise distance of all instances.
 * Disadvantage: time-consuming computation.
 * </p>
 *
 * @version 1.01
 * @since 2016
 */
public class MaxPairwiseDistanceCompactnessMeasure<FV extends IFeatureVectorObject<?, ?>>
		extends ClusterCompactnessMeasure<FV> {

	@Override
	public double getMeasure(Cluster<FV> cluster) {

		IDistanceMatrix<FV> pairwiseDistances = this.getPairwiseDistances(cluster);
		StatisticsSupport distanceStatistics = DistanceMatrixStatistics.getPairwiseDistanceStatistics(pairwiseDistances,
				true, true);

		return distanceStatistics.getMax();
	}

}