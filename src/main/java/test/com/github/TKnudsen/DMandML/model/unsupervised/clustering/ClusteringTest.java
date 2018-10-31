package test.com.github.TKnudsen.DMandML.model.unsupervised.clustering;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.data.cluster.featureVector.numerical.NumericalFeatureVectorClusterResult;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.INumericalClusteringAlgorithm;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.KMeans;

import test.com.github.TKnudsen.DMandML.model.supervised.classifier.ClassificationTest;

/**
 * <p>
 * Title: ClusteringTest
 * </p>
 * 
 * <p>
 * Description: Simple test/example of a clustering algorithm.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class ClusteringTest {

	public static void main(String[] args) {

		List<NumericalFeatureVector> featureVectors = ClassificationTest.provideFeatureVectors();

		INumericalClusteringAlgorithm clusteringAlgorithm = new KMeans(4);

		clusteringAlgorithm.setFeatureVectors(featureVectors);

		clusteringAlgorithm.calculateClustering();

		NumericalFeatureVectorClusterResult clusteringResult = clusteringAlgorithm.getClusteringResult();

		System.out.println(clusteringResult);
	}
}
