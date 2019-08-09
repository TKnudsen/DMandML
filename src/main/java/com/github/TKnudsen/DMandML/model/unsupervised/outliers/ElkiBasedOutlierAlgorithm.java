package com.github.TKnudsen.DMandML.model.unsupervised.outliers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.data.elki.ELKIDataWrapper;
import com.github.TKnudsen.DMandML.data.outliers.IOutlierAnalysisResult;
import com.github.TKnudsen.DMandML.data.outliers.OutlierAnalysisResult;

import de.lmu.ifi.dbs.elki.algorithm.outlier.OutlierAlgorithm;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.database.ids.DBIDIter;
import de.lmu.ifi.dbs.elki.database.ids.DBIDs;
import de.lmu.ifi.dbs.elki.database.relation.DoubleRelation;
import de.lmu.ifi.dbs.elki.database.relation.Relation;
import de.lmu.ifi.dbs.elki.result.outlier.OutlierResult;

/**
 * <p>
 * Title: ElkiBasedOutlierAlgorithm
 * </p>
 * 
 * <p>
 * Description: basic class for outlier algorithms from the elki framework.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017-2019 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.04
 */
public abstract class ElkiBasedOutlierAlgorithm extends OutlierAnalysisAlgorithm<NumericalFeatureVector> {

	private ELKIDataWrapper elkiDataWrapper;

	protected OutlierAlgorithm outlierAlgorithm;

	protected OutlierResult elkiOutlierResult;

	public ElkiBasedOutlierAlgorithm() {
	}

	public ElkiBasedOutlierAlgorithm(List<? extends NumericalFeatureVector> featureVectors) {
		super(featureVectors);
	}

	@Override
	/**
	 * does all three steps in a row: set data, build model, and create result. does
	 * not necessarily use the three individual methods, as these routines maybe
	 * deprecated some day.
	 */
	public IOutlierAnalysisResult<NumericalFeatureVector> runOutlierAnalysis(
			List<? extends NumericalFeatureVector> featureVectors) {

		// setData
		elkiDataWrapper = new ELKIDataWrapper(featureVectors);

		elkiOutlierResult = outlierAlgorithm.run(elkiDataWrapper.getDB());

		calculateOutlierAnalysisResult();

		return getOutlierAnalysisResult();
	}

	@Override
	public void setData(List<? extends NumericalFeatureVector> featureVectors) {
		elkiDataWrapper = new ELKIDataWrapper(featureVectors);
	}

	protected abstract void initializeOutlierAlgorithm();

	@Override
	public void run() {
		if (outlierAlgorithm == null)
			initializeOutlierAlgorithm();

		ELKIDataWrapper elkiDataWrapper = getElkiDataWrapper();
		if (elkiDataWrapper == null)
			throw new NullPointerException(getName() + ": data not initialized");

		elkiOutlierResult = outlierAlgorithm.run(elkiDataWrapper.getDB());
	}

	@Override
	public void calculateOutlierAnalysisResult() {
		if (elkiOutlierResult == null)
			throw new NullPointerException(getName() + ": no result available. Has not been run yet.");

		DoubleRelation scores = elkiOutlierResult.getScores();
		DBIDs iDs = scores.getDBIDs();
		Relation<NumberVector> rel = elkiDataWrapper.getRelation();

		Map<NumericalFeatureVector, Double> outlierAnalysisResultMap = new LinkedHashMap<>();

		for (DBIDIter it = iDs.iter(); it.valid(); it.advance()) {
			NumberVector v = rel.get(it);
			NumericalFeatureVector fv = elkiDataWrapper.getFeatureVector(v);

			if (fv == null)
				throw new NullPointerException(getName() + ".getClusteringResult(): feature vector lookup failed.");

			outlierAnalysisResultMap.put(fv, scores.doubleValue(it));
		}

		this.outlierAnalysisResult = new OutlierAnalysisResult<>(outlierAnalysisResultMap, getName());
	}

	@Override
	public double getOutlierScore(NumericalFeatureVector fv) {
		IOutlierAnalysisResult<NumericalFeatureVector> outlierAnalysisResult = getOutlierAnalysisResult();

		if (outlierAnalysisResult == null)
			return Double.NaN;

		return outlierAnalysisResult.getOutlierScore(fv);
	}

	@Override
	public void resetScores() {
		elkiOutlierResult = null;
		outlierAnalysisResult = null;
	}

	protected ELKIDataWrapper getElkiDataWrapper() {
		return elkiDataWrapper;
	}

	public OutlierResult getElkiOutlierResult() {
		return elkiOutlierResult;
	}

}
