package main.java.com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

import main.java.com.github.TKnudsen.DMandML.model.unsupervised.clustering.WekaClusteringAlgorithm;

/**
 * <p>
 * Title: Cobweb
 * </p>
 * 
 * <p>
 * Description: implementation is based on WEKAs Cobweb.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class Cobweb extends WekaClusteringAlgorithm {

	private double acuity;

	private double cutoff;

	protected Cobweb() {
		this(8, 12);
	}

	public Cobweb(double acuity, double cutoff) {
		setAcuity(acuity);
		setCutoff(cutoff);
	}

	@Override
	public IDistanceMeasure<NumericalFeatureVector> getDistanceMeasure() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initializeClusteringAlgorithm() {
		// Valid options are:
		//
		// -A <0-100>
		// Acuity.
		//
		// -C <0-100>
		// Cutoff.

		wekaClusterer = new weka.clusterers.Cobweb();

		String[] opts = new String[4];
		opts[0] = "-A";
		opts[1] = "" + getAcuity();
		opts[2] = "-C";
		opts[3] = "" + getCutoff();

		try {
			wekaClusterer.setOptions(opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getAcuity() {
		return acuity;
	}

	public void setAcuity(double acuity) {
		this.acuity = acuity;
	}

	public double getCutoff() {
		return cutoff;
	}

	public void setCutoff(double cutoff) {
		this.cutoff = cutoff;
	}

}
