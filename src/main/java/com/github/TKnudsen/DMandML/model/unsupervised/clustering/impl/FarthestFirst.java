package main.java.com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

import main.java.com.github.TKnudsen.DMandML.model.unsupervised.clustering.WekaClusteringAlgorithm;

/**
 * <p>
 * Title: FarthestFirst
 * </p>
 * 
 * <p>
 * Description: implementation is based on WEKAs FarthestFirst.
 * 
 * Hochbaum, Shmoys (1985). A best possible heuristic for the k-center problem.
 * Mathematics of Operations Research. 10(2):180-184.
 * 
 * Sanjoy Dasgupta: Performance Guarantees for Hierarchical Clustering. In: 15th
 * Annual Conference on Computational Learning Theory, 351-363, 2002.
 * 
 * Notes: - works as a fast simple approximate clusterer - modelled after
 * SimpleKMeans, might be a useful initializer for it
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class FarthestFirst extends WekaClusteringAlgorithm {

	private int k;

	private int seed;

	protected FarthestFirst() {
		this(2, 1);
	}

	protected FarthestFirst(int k) {
		this(k, 1);
	}

	protected FarthestFirst(int k, int seed) {
		setK(k);
		setSeed(seed);
	}

	@Override
	public IDistanceMeasure<NumericalFeatureVector> getDistanceMeasure() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "FarthestFirst";
	}

	@Override
	protected void initializeClusteringAlgorithm() {
		// Valid options are:
		//
		// -N <num>
		// number of clusters. (default = 2).
		//
		// -S <num>
		// Random number seed.
		// (default 1)

		wekaClusterer = new weka.clusterers.FarthestFirst();

		String[] opts = new String[4];
		opts[0] = "-N";
		opts[1] = "" + getK();
		opts[2] = "-S";
		opts[3] = "" + getSeed();

		try {
			wekaClusterer.setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

}
