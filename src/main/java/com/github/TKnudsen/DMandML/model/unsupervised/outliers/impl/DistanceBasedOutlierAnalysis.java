package com.github.TKnudsen.DMandML.model.unsupervised.outliers.impl;

import com.github.TKnudsen.DMandML.model.unsupervised.outliers.ElkiBasedOutlierAlgorithm;

import de.lmu.ifi.dbs.elki.algorithm.outlier.distance.DBOutlierDetection;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.EuclideanDistanceFunction;

/**
 * <p>
 * Title: DistanceBasedOutlierAnalysis
 * </p>
 * 
 * <p>
 * Description:
 * 
 * E.M. Knorr, R. T. Ng:<br />
 * Algorithms for Mining Distance-Based Outliers in Large Datasets,<br />
 * In: Procs Int. Conf. on Very Large Databases (VLDB'98), New York, USA, 1998.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class DistanceBasedOutlierAnalysis extends ElkiBasedOutlierAlgorithm {

	/**
	 * Radius parameter d.
	 */
	private double d;

	/**
	 * Density threshold percentage p.
	 */
	private double p;

	public DistanceBasedOutlierAnalysis(double d, double p) {
		this.d = d;
		this.p = p;
	}

	@Override
	protected void initializeOutlierAlgorithm() {
		this.outlierAlgorithm = new DBOutlierDetection<>(EuclideanDistanceFunction.STATIC, d, p);
	}

	@Override
	public String getDescription() {
		return "Simple distanced based outlier detection algorithm";
	}

}
