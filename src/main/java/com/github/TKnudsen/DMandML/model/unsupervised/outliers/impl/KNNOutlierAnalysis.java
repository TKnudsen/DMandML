package com.github.TKnudsen.DMandML.model.unsupervised.outliers.impl;

import com.github.TKnudsen.DMandML.model.unsupervised.outliers.ElkiBasedOutlierAlgorithm;

import de.lmu.ifi.dbs.elki.algorithm.outlier.distance.KNNOutlier;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.EuclideanDistanceFunction;

/**
 * <p>
 * Title: KNNOutlierAnalysis
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public class KNNOutlierAnalysis extends ElkiBasedOutlierAlgorithm {

	private final int kNN;

	public KNNOutlierAnalysis(int kNN) {
		this.kNN = kNN;
	}

	@Override
	protected void initializeOutlierAlgorithm() {
		this.outlierAlgorithm = new KNNOutlier<>(EuclideanDistanceFunction.STATIC, kNN);
	}

	public int getkNN() {
		return kNN;
	}

	@Override
	public String getName() {
		return "KNN Outlier Algorithm";
	}

	@Override
	public String getDescription() {
		return getName();
	}

}
