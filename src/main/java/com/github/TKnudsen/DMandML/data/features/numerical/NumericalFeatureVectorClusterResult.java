package main.java.com.github.TKnudsen.DMandML.data.features.numerical;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;

import main.java.com.github.TKnudsen.DMandML.data.ClusteringResult;

/**
 * <p>
 * Title: NumericalFeatureVectorClusterResult
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class NumericalFeatureVectorClusterResult extends ClusteringResult<NumericalFeatureVector, NumericalFeatureVectorCluster> {

	public NumericalFeatureVectorClusterResult(List<NumericalFeatureVectorCluster> clusters) {
		super(clusters);
	}

}
