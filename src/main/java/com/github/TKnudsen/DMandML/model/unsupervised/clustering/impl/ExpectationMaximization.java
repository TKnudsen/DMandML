package com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl;

import com.github.TKnudsen.DMandML.model.unsupervised.clustering.WekaClusteringAlgorithm;

/**
 * <p>
 * Title: ExpectationMaximization
 * </p>
 * 
 * <p>
 * Description: implementation is based on WEKAs EM.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public class ExpectationMaximization extends WekaClusteringAlgorithm {

	/**
	 * number of iterations to be trained.
	 */
	private int maxIterations = 100;

	/**
	 * number of clusters to be calculated.
	 */
	private int k;

	protected ExpectationMaximization() {
		this(3, 100);
	}

	public ExpectationMaximization(int k) {
		this(k, 100);
	}

	public ExpectationMaximization(int k, int maxIterations) {
		setK(k);
		setMaxIterations(maxIterations);
	}

	@Override
	public String getName() {
		return "Expectation Maximization (EM)";
	}

	@Override
	public String getDescription() {
		return getName();
	}

	@Override
	protected void initializeClusteringAlgorithm() {
		wekaClusterer = new weka.clusterers.EM();

		String[] opts = new String[4];
		opts[0] = "-I";
		opts[1] = getMaxIterations() + "";
		opts[2] = "-N";
		opts[3] = getK() + "";
		try {
			wekaClusterer.setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}
}
