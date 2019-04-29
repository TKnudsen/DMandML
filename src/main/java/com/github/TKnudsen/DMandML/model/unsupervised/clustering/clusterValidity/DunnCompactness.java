package com.github.TKnudsen.DMandML.model.unsupervised.clustering.clusterValidity;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;

public class DunnCompactness<FV extends IFeatureVectorObject<?, ?>> {

	private List<Cluster<FV>> clusters;

	private IDistanceMeasure<FV> featureVectorDistanceMeasure;

	private SortedMap<String, Double> dunnCompactnessMap = new TreeMap<String, Double>();

	public DunnCompactness(List<Cluster<FV>> clusters, IDistanceMeasure<FV> featureVectorDistanceMeasure) {

		this.clusters = clusters;
		this.featureVectorDistanceMeasure = featureVectorDistanceMeasure;
	}

	public void run() {

		for (Cluster<FV> cluster : clusters) {

			double compactness = Double.MAX_VALUE - 1;
			double count = 0;
			double sum = 0;

			if (cluster.size() > 1) {
				for (FV fv : cluster) {
					if (cluster.getCentroid() != null) {
						sum += featureVectorDistanceMeasure.getDistance(fv, cluster.getCentroid().getData());
						count++;
						compactness = sum / count;
					}
				}
			}
			if (compactness != Double.MAX_VALUE - 1) {
				dunnCompactnessMap.put(cluster.getName(), compactness);
			}
		}
	}

	public SortedMap<String, Double> getClusterDunnCompactnessMap() {
		return dunnCompactnessMap;
	}

}
