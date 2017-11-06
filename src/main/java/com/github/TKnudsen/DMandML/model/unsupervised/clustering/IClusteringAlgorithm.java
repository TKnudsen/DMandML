package com.github.TKnudsen.DMandML.model.unsupervised.clustering;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;

/**
 * <p>
 * Title: IClusteringAlgorithm
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.08
 */
public interface IClusteringAlgorithm<F extends IDObject> {

	public IDistanceMeasure<F> getDistanceMeasure();

	public List<F> getFeatureVectors();
	
	public void setFeatureVectors(List<F> featureVectors) ;

	public void calculateClustering();

	public IClusteringResult<F, ? extends Cluster<F>> getClusteringResult();

	public String getName();
}
