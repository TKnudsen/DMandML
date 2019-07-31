package com.github.TKnudsen.DMandML.model.unsupervised.outliers.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.data.outliers.OutlierAnalysisResult;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.ElkiBasedOutlierAlgorithm;

import de.lmu.ifi.dbs.elki.algorithm.outlier.DWOF;
import de.lmu.ifi.dbs.elki.algorithm.outlier.DWOF.Parameterizer;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.database.ids.DBIDIter;
import de.lmu.ifi.dbs.elki.database.ids.DBIDs;
import de.lmu.ifi.dbs.elki.database.relation.DoubleRelation;
import de.lmu.ifi.dbs.elki.database.relation.Relation;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.EuclideanDistanceFunction;
import smile.math.Math;

/**
 * <p>
 * Title: DynamicWindowOutlierFactorOutlierAnalysis
 * </p>
 * 
 * <p>
 * Description:
 * 
 * Rana Momtaz, Nesma Mohssen and Mohammad A. Gowayyed: DWOF: A Robust
 * Density-Based OutlierDetection Approach. <br>
 * In: Pattern Recognition and Image Analysis , Proc. 6th Iberian Conference,
 * IbPRIA 2013, Funchal, Madeira, Portugal, June 5-7, 2013.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class DynamicWindowOutlierFactorOutlierAnalysis extends ElkiBasedOutlierAlgorithm {

	/**
	 * Holds the value of {@link Parameterizer#K_ID} i.e. Number of neighbors to
	 * consider during the calculation of DWOF scores.
	 */
	private int kNN;

	private double delta = 1.1;

	public DynamicWindowOutlierFactorOutlierAnalysis(int kNN) {
		this.kNN = kNN;
	}

	@Override
	protected void initializeOutlierAlgorithm() {
		this.outlierAlgorithm = new DWOF<NumberVector>(EuclideanDistanceFunction.STATIC, kNN, delta);
	}

	@Override
	public String getDescription() {
		return "Computes dynamic-window outlier factors in a dataset";
	}

	@Override
	/**
	 * DWOF seems to produce scores where low values mean outlier-likely. Thus, the
	 * output needs to be inverted.
	 */
	public void calculateOutlierAnalysisResult() {
		if (elkiOutlierResult == null)
			throw new NullPointerException(getName() + ": no result available. Has not been run yet.");

		DoubleRelation scores = elkiOutlierResult.getScores();
		DBIDs iDs = scores.getDBIDs();

		// this will facilitate the inversion of the value domain [0-max] => [max]
		double max = Double.MIN_VALUE + 1;
		for (DBIDIter it = iDs.iter(); it.valid(); it.advance())
			max = Math.max(max, scores.doubleValue(it));

		Relation<NumberVector> rel = getElkiDataWrapper().getRelation();
		Map<NumericalFeatureVector, Double> outlierAnalysisResultMap = new LinkedHashMap<>();

		for (DBIDIter it = iDs.iter(); it.valid(); it.advance()) {
			NumberVector v = rel.get(it);
			NumericalFeatureVector fv = getElkiDataWrapper().getFeatureVector(v);

			if (fv == null)
				throw new NullPointerException(getName() + ".getClusteringResult(): feature vector lookup failed.");

			outlierAnalysisResultMap.put(fv, max - scores.doubleValue(it));
		}

		this.outlierAnalysisResult = new OutlierAnalysisResult<>(outlierAnalysisResultMap, getName());
	}

	public int getkNN() {
		return kNN;
	}

	public void setkNN(int kNN) {
		this.kNN = kNN;

		initializeOutlierAlgorithm();
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;

		initializeOutlierAlgorithm();
	}

}
