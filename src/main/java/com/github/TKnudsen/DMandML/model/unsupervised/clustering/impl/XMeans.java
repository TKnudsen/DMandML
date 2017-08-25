package main.java.com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;

import main.java.com.github.TKnudsen.DMandML.model.unsupervised.clustering.WekaClusteringAlgorithm;

/**
 * <p>
 * Title: KMeans
 * </p>
 * 
 * <p>
 * Description: implementation is based on WEKAs XMeans.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class XMeans extends WekaClusteringAlgorithm {

	/**
	 * minimum number of clusters
	 */
	private int k_min;

	/**
	 * maximum number of clusters
	 */
	private int k_max;

	/**
	 * final number of clusters (output) of the model
	 */
	private int k;

	protected XMeans() {
		this(2, 10, null);
	}

	public XMeans(int k_min, int k_max, List<NumericalFeatureVector> featureVectors) {
		super(featureVectors);
		this.k_min = k_min;
		this.k_max = k_max;
	}

	@Override
	public IDistanceMeasure<NumericalFeatureVector> getDistanceMeasure() {
		return new EuclideanDistanceMeasure();
	}

	@Override
	public String getName() {
		return "XMeans";
	}

	@Override
	protected void initializeClusteringAlgorithm() {
		wekaClusterer = new weka.clusterers.XMeans();

		String[] opts = new String[6];
		opts[0] = "-I";
		opts[1] = "" + 1;
		opts[2] = "-L";
		opts[3] = "" + getK_min();
		opts[4] = "-H";
		opts[5] = "" + getK_max();

		try {
			wekaClusterer.setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void calculateClustering() {
		super.calculateClustering();

		if (clusterResult != null)
			k = clusterResult.size();
	}

	public int getK_min() {
		return k_min;
	}

	public void setK_min(int k_min) {
		this.k_min = k_min;
	}

	public int getK_max() {
		return k_max;
	}

	public void setK_max(int k_max) {
		this.k_max = k_max;
	}

	public int getK() {
		return k;
	}
}
