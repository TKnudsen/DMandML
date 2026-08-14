package com.github.TKnudsen.DMandML.model.unsupervised.clustering;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;

/**
 * @version 1.11
 * @since 2016
 */
public interface IClusteringAlgorithm<F> extends ISelfDescription {

//	/**
//	 * returns a unmodifiable view on the internal data state.
//	 * 
//	 * @return
//	 */
//	public List<F> getFeatureVectors();

	/**
	 * constructs a copy of a given list of elements.
	 * 
	 * Note: don't forget to reset the internal state of the model such as previous
	 * clustering results.
	 * 
	 * @param featureVectors feature vectors
	 */
	public void setFeatureVectors(List<? extends F> featureVectors);

	/**
	 * runs the clustering algorithm. To be general no parameter shall be required
	 * at this state.
	 */
	public void calculateClustering();

	public IClusteringResult<F, ? extends ICluster<F>> getClusteringResult();
}
