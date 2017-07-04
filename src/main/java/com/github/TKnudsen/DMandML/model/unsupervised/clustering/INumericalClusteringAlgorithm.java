package main.java.com.github.TKnudsen.DMandML.model.unsupervised.clustering;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;

import main.java.com.github.TKnudsen.DMandML.data.IClusteringResult;
import main.java.com.github.TKnudsen.DMandML.data.features.numerical.NumericalFeatureVectorCluster;

/**
 * <p>
 * Title: INumericalClusteringAlgorithm
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
interface INumericalClusteringAlgorithm extends IClusteringAlgorithm<NumericalFeatureVector> {

	public IClusteringResult<NumericalFeatureVector, NumericalFeatureVectorCluster> getClusterResultSet();
}