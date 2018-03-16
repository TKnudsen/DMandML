package com.github.TKnudsen.DMandML.model.unsupervised.outliers;

import com.github.TKnudsen.DMandML.data.elki.ELKIDataWrapper;

import de.lmu.ifi.dbs.elki.algorithm.outlier.distance.KNNOutlier;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.EuclideanDistanceFunction;

public class KNNOutlierAnalysisAlgorithm extends ElkiBasedOutlierAlgorithm {

	private final int kNN;

	public KNNOutlierAnalysisAlgorithm(int kNN) {
		this.kNN = kNN;
	}

	@Override
	void initializeOutlierAlgorithm() {
		this.outlierAlgorithm = new KNNOutlier<>(EuclideanDistanceFunction.STATIC, kNN);
	}

	@Override
	public void run() {
		if (outlierAlgorithm == null)
			initializeOutlierAlgorithm();

		ELKIDataWrapper elkiDataWrapper = getElkiDataWrapper();
		if (elkiDataWrapper == null)
			throw new NullPointerException(getName() + ": data not initialized");

		elkiOutlierResult = outlierAlgorithm.run(elkiDataWrapper.getDB());
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
