package com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.WekaClusteringAlgorithm;

/**
 * <p>
 * Title: HierarchicalClustering
 * </p>
 * 
 * <p>
 * Description: implementation is based on WEKAs HierarchicalClustering.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class HierarchicalClustering extends WekaClusteringAlgorithm {

	/**
	 * number of cluster to be calculated.
	 */
	private int k;

	// TODO add link type: Link type (Single, Complete, Average, Mean, Centroid,
	// Ward, Adjusted complete, Neighbor Joining)
	// [SINGLE|COMPLETE|AVERAGE|MEAN|CENTROID|WARD|ADJCOMPLETE|NEIGHBOR_JOINING]

	protected HierarchicalClustering() {
		this(3);
	}

	public HierarchicalClustering(int k) {
		setK(k);
	}

	@Override
	public IDistanceMeasure<NumericalFeatureVector> getDistanceMeasure() {
		return new EuclideanDistanceMeasure();
	}

	@Override
	public String getName() {
		return "Hierarchical Clustering";
	}

	@Override
	protected void initializeClusteringAlgorithm() {
		// -N
		// number of clusters
		//
		//
		// -L
		// Link type (Single, Complete, Average, Mean, Centroid, Ward, Adjusted
		// complete, Neighbor Joining)
		// [SINGLE|COMPLETE|AVERAGE|MEAN|CENTROID|WARD|ADJCOMPLETE|NEIGHBOR_JOINING]
		//
		//
		// -A
		// Distance function to use. (default: weka.core.EuclideanDistance)
		//
		//
		// -P
		// Print hierarchy in Newick format, which can be used for display in
		// other programs.
		//
		//
		// -D
		// If set, classifier is run in debug mode and may output additional
		// info to the console.
		//
		//
		// -B
		// \If set, distance is interpreted as branch length, otherwise it is
		// node height.

		wekaClusterer = new weka.clusterers.HierarchicalClusterer();

		String[] opts = new String[2];
		opts[0] = "-N";
		opts[1] = "" + getK();

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

}
