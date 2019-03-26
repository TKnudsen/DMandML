package com.github.TKnudsen.DMandML.model.unsupervised.clustering.clusterValidity;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;

public class DunnSeparation<FV extends IFeatureVectorObject<?, ?>> {

	private List<Cluster<FV>> clusters;

	private IDistanceMeasure<FV> featureVectorDistanceMeasure;

	private SortedMap<String, Double> dunnSeparationMap = new TreeMap<String, Double>();

	public DunnSeparation(List<Cluster<FV>> clusters, IDistanceMeasure<FV> featureVectorDistanceMeasure) {

		this.clusters = clusters;
		this.featureVectorDistanceMeasure = featureVectorDistanceMeasure;
	}

	public void run() {

		for (Cluster<FV> cluster1 : clusters) {

			double separation = Double.MAX_VALUE - 1;

			for (Cluster<FV> cluster2 : clusters) {
				if (cluster1.equals(cluster2))
					continue;
				else {
					if (cluster1.getCentroid() != null && cluster2.getCentroid() != null) {
						FV fv1 = cluster1.getCentroid().getData();
						FV fv2 = cluster2.getCentroid().getData();
						double d = featureVectorDistanceMeasure.getDistance(fv1, fv2);
						if (d < separation) {
							separation = d;
						}
					}
				}
			}
			if (separation != Double.MAX_VALUE - 1) {
				dunnSeparationMap.put(cluster1.getName(), separation);
			}
		}
	}

	public SortedMap<String, Double> getClusterDunnSeparationMap() {
		return dunnSeparationMap;
	}

}
