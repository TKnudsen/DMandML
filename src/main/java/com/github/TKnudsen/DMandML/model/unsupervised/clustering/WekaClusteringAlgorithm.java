package main.java.com.github.TKnudsen.DMandML.model.unsupervised.clustering;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.tools.WekaConversion;

import main.java.com.github.TKnudsen.DMandML.data.features.numerical.NumericalFeatureVectorClusterResult;
import main.java.com.github.TKnudsen.DMandML.model.unsupervised.clustering.tools.WekaClusteringTools;
import weka.clusterers.AbstractClusterer;
import weka.core.Instances;

/**
 * <p>
 * Title: WekaClusteringAlgorithm
 * </p>
 * 
 * <p>
 * Description: abstract baseline routine for WEKA-base clustering routines.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public abstract class WekaClusteringAlgorithm implements IClusteringAlgorithm<NumericalFeatureVector> {

	/**
	 * data to be clustered
	 */
	protected List<NumericalFeatureVector> featureVectors;

	/**
	 * WEKA representation of the data
	 */
	protected Instances data;

	/**
	 * the WEKA-based clustering routine
	 */
	protected AbstractClusterer wekaClusterer;

	/**
	 * cluster result that can be used for downstream analysis
	 */
	protected NumericalFeatureVectorClusterResult clusterResult;

	public WekaClusteringAlgorithm() {
	}

	public WekaClusteringAlgorithm(List<NumericalFeatureVector> featureVectors) {
		this.setFeatureVectors(featureVectors);
	}

	protected abstract void initializeClusteringAlgorithm();

	@Override
	public void calculateClustering() {
		initializeClusteringAlgorithm();

		try {
			clusterResult = WekaClusteringTools.getClusterResultOutOfWekaClusterer(wekaClusterer, data, featureVectors);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public NumericalFeatureVectorClusterResult getClusterResultSet() {
		if (clusterResult == null)
			throw new NullPointerException("WekaClusteringAlgorithm: cluster result was null. Was the clustering calculated yet?");

		return clusterResult;
	}

	public List<NumericalFeatureVector> getFeatureVectors() {
		return featureVectors;
	}

	public void setFeatureVectors(List<NumericalFeatureVector> featureVectors) {
		this.featureVectors = featureVectors;
		data = WekaConversion.getInstances(featureVectors, false);
	}
}
