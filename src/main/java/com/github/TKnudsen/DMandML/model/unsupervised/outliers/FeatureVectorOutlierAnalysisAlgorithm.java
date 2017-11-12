package com.github.TKnudsen.DMandML.model.unsupervised.outliers;

import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;

/**
 * <p>
 * Title: FeatureVectorOutlierAnalysisAlgorithm
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public abstract class FeatureVectorOutlierAnalysisAlgorithm<FV extends AbstractFeatureVector<?, ?>> implements IFeatureVectorOutlierAnalysisAlgorithm<FV> {

	private List<FV> featureVectors;

	protected Map<FV, Double> outlierScores;

	public FeatureVectorOutlierAnalysisAlgorithm(List<FV> featureVectors) {
		this.featureVectors = featureVectors;
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public double getOutlierScore(FV featureVector) {
		if (outlierScores == null) {
			System.err.println(getName() + ": Outlier scores not calculated yet");
			return Double.NaN;
		}

		return outlierScores.get(featureVector);
	}

	@Override
	public void resetScores() {
		outlierScores = null;
	}

	public List<FV> getFeatureVectors() {
		return featureVectors;
	}

	public void setFeatureVectors(List<FV> featureVectors) {
		this.featureVectors = featureVectors;
		
		resetScores();
	}
}
