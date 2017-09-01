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
 * Description: implementation is based on WEKAs SimpleKmeans.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class KMeans extends WekaClusteringAlgorithm {

	private int k = 2;

	private int iterations = 100;

	private int seed = 10;

	public KMeans() {
		this(2, 17);
	}

	public KMeans(int k) {
		this(k, 17);
	}

	public KMeans(int k, int seed) {
		this.k = k;
		this.seed = seed;
	}

	public KMeans(int k, int seed, List<NumericalFeatureVector> featureVectors) {
		super(featureVectors);
		this.k = k;
		this.seed = seed;
	}

	@Override
	protected void initializeClusteringAlgorithm() {
		wekaClusterer = new weka.clusterers.SimpleKMeans();

		String[] opts = new String[7];
		opts[0] = "-N";
		opts[1] = "" + getK();
		opts[2] = "-S";
		opts[3] = "" + getSeed();
		opts[4] = "-V";
		opts[5] = "-I";
		opts[6] = "" + getIterations();

		try {
			wekaClusterer.setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	@Override
	public String getName() {
		return "KMeans";
	}

	@Override
	public IDistanceMeasure<NumericalFeatureVector> getDistanceMeasure() {
		return new EuclideanDistanceMeasure();
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;

		// initializeClusteringAlgorithm();
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;

		// initializeClusteringAlgorithm();
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;

		// initializeClusteringAlgorithm();
	}
}
