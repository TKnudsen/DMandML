package com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
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
 * @version 1.04
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

	public DBScan(double epsilon, int minPoints) {
		setEpsilon(epsilon);
		setMinPoints(minPoints);
	}

	public DBScan(List<? extends NumericalFeatureVector> featureVectors, double epsilon, int minPoints) {
		this(epsilon, minPoints);

		setFeatureVectors(featureVectors);
	}

	@Override
	public String getName() {
		return "DBScan";
	}

	@Override
	public String getDescription() {
		return getName();
	}

	@Override
	protected void initializeClusteringAlgorithm() {
		if (data == null)
			data = WekaConversion.getInstances(featureVectors, false);

		wekaClusterer = new weka.clusterers.DBSCAN();
		String[] options = new String[5];
		options[0] = "-E";
		options[1] = "" + getEpsilon();
		options[2] = "-M";
		options[3] = "" + getMinPoints();
		options[4] = "-no-gui";

		try {
			wekaClusterer.setOptions(options);
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
