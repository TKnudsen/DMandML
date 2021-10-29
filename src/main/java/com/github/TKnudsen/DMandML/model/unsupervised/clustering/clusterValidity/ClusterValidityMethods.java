package com.github.TKnudsen.DMandML.model.unsupervised.clustering.clusterValidity;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Collection of functions used in different ClusterValidityMethods. Could later
 * be added to their respective class (as public method).
 * 
 * References: Peter J. Rousseeuw (1987). "Silhouettes: a Graphical Aid to the
 * Interpretation and Validation of Cluster Analysis". Computational and Applied
 * Mathematics. 20: p. 53-65. doi:10.1016/0377-0427(87)90125-7.
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.02
 */
public class ClusterValidityMethods {

	/**
	 * 
	 * Dissimilarity of an element w.r.t. its cluster. Measure of class compactness.
	 * 
	 * For reference see Rousseeuw, 1987.
	 * 
	 * @param <FV>            object type
	 * @param fv              input feature vector objects
	 * @param cluster         cluster
	 * @param distanceMeasure distance measure
	 * @return Silhouette validity intra-class dissimilarity
	 */
	public static <FV> Double silhuetteValidityIntraClassDissimilarity(FV fv, ICluster<? extends FV> cluster,
			IDistanceMeasure<FV> distanceMeasure) {
		if (fv == null || cluster == null || distanceMeasure == null)
			throw new IllegalArgumentException(
					"ClusterValidityMethods::silhuetteValidityIntraClassDissimilarity: Given values must not be null.");
		// calculate distance to all elements in cluster except the element
		// itself
		Double dissimilarity = cluster.getElements().stream().filter(x -> !x.equals(fv))
				.map(x -> distanceMeasure.applyAsDouble(x, fv)).reduce(0.0, (x, y) -> x + y) / cluster.size();
		return dissimilarity;
	}

	/**
	 * Dissimilarity of an element w.r.t. the nearest cluster (that it is not part
	 * of). Measure of class separation.
	 * 
	 * @param <FV>             object type
	 * @param fv               query feature vector object
	 * @param clusteringResult target clusters in a clustering result
	 * @param distanceMeasure  distance measure
	 * @return dissimilarity
	 */
	public static <FV> Double silhuetteValidityInterClassDissimilarity(FV fv,
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult, IDistanceMeasure<FV> distanceMeasure) {
		if (fv == null || clusteringResult == null || distanceMeasure == null)
			throw new IllegalArgumentException(
					"ClusterValidityMethods::silhuetteValidityInterClassDissimilarity: Given values must not be null.");
		if (clusteringResult.getClusters().size() < 1)
			throw new IllegalArgumentException(
					"ClusterValidityMethods::silhuetteValidityInterClassDissimilarity: List must contain at least one cluster.");

		// cluster that fv belongs to
		ICluster<? extends FV> c = clusteringResult.retrieveCluster(fv);
		// Find distance to nearest cluster to fv
		double minDistance = Double.MAX_VALUE;
		for (ICluster<? extends FV> cluster : clusteringResult.getClusters()) {
			if (cluster.equals(c))
				continue;
			minDistance = Math.min(minDistance, distanceMeasure.applyAsDouble(fv, cluster.getCentroid().getData()));
		}
		return minDistance;
	}
}
