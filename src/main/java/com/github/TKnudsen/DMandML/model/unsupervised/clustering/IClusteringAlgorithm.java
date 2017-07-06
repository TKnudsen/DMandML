package main.java.com.github.TKnudsen.DMandML.model.unsupervised.clustering;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

import main.java.com.github.TKnudsen.DMandML.data.IClusteringResult;

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
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.07
 */
public interface IClusteringAlgorithm<F extends IDObject> {

	public IDistanceMeasure<F> getDistanceMeasure();

	public List<F> getFeatureVectors();
	
	public void calculateClustering();

	public IClusteringResult<F, ?> getClusterResultSet();

	public String getName();
}
