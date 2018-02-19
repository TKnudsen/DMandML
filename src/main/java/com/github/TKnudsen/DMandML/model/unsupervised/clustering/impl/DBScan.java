package com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.tools.WekaConversion;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.WekaClusteringAlgorithm;

/**
 * <p>
 * Title: DBScan
 * </p>
 * 
 * <p>
 * Description: implementation is based on WEKAs DBSCAN.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 */
public class DBScan extends WekaClusteringAlgorithm {

	/**
	 * the maximum epsilon (distance) between points that is allowed for a cluster
	 * structure
	 */
	private double epsilon;

	/**
	 * the number of minPoints of a potential dense (distances <= epsilon) cluster
	 * structure to be a valid cluster
	 */
	private int minPoints;

	protected DBScan() {
	}

	public DBScan(List<NumericalFeatureVector> featureVectors) {
		this(featureVectors, 0.1, 10);
	}

	public DBScan(double epsilon, int minPoints) {
		setFeatureVectors(null);
		setEpsilon(epsilon);
		setMinPoints(minPoints);
	}

	public DBScan(List<NumericalFeatureVector> featureVectors, double epsilon, int minPoints) {
		setFeatureVectors(featureVectors);
		setEpsilon(epsilon);
		setMinPoints(minPoints);
	}

	@Override
	public IDistanceMeasure<NumericalFeatureVector> getDistanceMeasure() {
		return new EuclideanDistanceMeasure();
	}

	@Override
	public String getName() {
		return "DBScan";
	}

	@Override
	protected void initializeClusteringAlgorithm() {
		if (data == null)
			data = WekaConversion.getInstances(featureVectors, false);

		wekaClusterer = new weka.clusterers.DBSCAN();
		String[] opts = new String[5];
		opts[0] = "-E";
		opts[1] = "" + getEpsilon();
		opts[2] = "-M";
		opts[3] = "" + getMinPoints();
		opts[4] = "-no-gui";

		try {
			wekaClusterer.setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public int getMinPoints() {
		return minPoints;
	}

	public void setMinPoints(int minPoints) {
		this.minPoints = minPoints;
	}
}
