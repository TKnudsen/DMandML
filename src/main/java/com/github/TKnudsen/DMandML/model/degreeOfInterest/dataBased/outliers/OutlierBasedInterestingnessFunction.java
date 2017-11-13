package com.github.TKnudsen.DMandML.model.degreeOfInterest.dataBased.outliers;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.FeatureVectorSupplier;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.InterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.dataBased.IDataBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.IFeatureVectorOutlierAnalysisAlgorithm;

/**
 * <p>
 * Title: OutlierBasedInterestingnessFunction
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class OutlierBasedInterestingnessFunction<FV extends AbstractFeatureVector<?, ?>> extends InterestingnessFunction<FV> implements IDataBasedInterestingnessFunction<FV> {

	private IFeatureVectorOutlierAnalysisAlgorithm<FV> outlierAnalysisAlgorithm;

	public OutlierBasedInterestingnessFunction(FeatureVectorSupplier<FV> featureVectorSupplier, IFeatureVectorOutlierAnalysisAlgorithm<FV> outlierAnalysisAlgorithm) {
		super(featureVectorSupplier);

		if (outlierAnalysisAlgorithm == null)
			throw new NullPointerException("OutlierBasedInterestingnessFunction: outlier analysis algorithm is null");
		this.outlierAnalysisAlgorithm = outlierAnalysisAlgorithm;
	}

	@Override
	public String getDescription() {
		return outlierAnalysisAlgorithm.getName();
	}

	@Override
	public void run() {
		outlierAnalysisAlgorithm.setData(getFeatureVectorSupplier().get());
		outlierAnalysisAlgorithm.run();
	}

	public IFeatureVectorOutlierAnalysisAlgorithm<FV> getOutlierAnalysisAlgorithm() {
		return outlierAnalysisAlgorithm;
	}

	public void setOutlierAnalysisAlgorithm(IFeatureVectorOutlierAnalysisAlgorithm<FV> outlierAnalysisAlgorithm) {
		if (outlierAnalysisAlgorithm == null)
			throw new NullPointerException("OutlierBasedInterestingnessFunction: outlier analysis algorithm is null");
		this.outlierAnalysisAlgorithm = outlierAnalysisAlgorithm;

		resetInterestingnessScores();
	}

}
