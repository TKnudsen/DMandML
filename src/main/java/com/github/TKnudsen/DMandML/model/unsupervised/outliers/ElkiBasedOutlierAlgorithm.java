package com.github.TKnudsen.DMandML.model.unsupervised.outliers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.data.elki.ELKIDataWrapper;

import de.lmu.ifi.dbs.elki.algorithm.outlier.OutlierAlgorithm;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.database.ids.DBIDIter;
import de.lmu.ifi.dbs.elki.database.ids.DBIDs;
import de.lmu.ifi.dbs.elki.database.relation.DoubleRelation;
import de.lmu.ifi.dbs.elki.database.relation.Relation;
import de.lmu.ifi.dbs.elki.result.outlier.OutlierResult;

public abstract class ElkiBasedOutlierAlgorithm implements IOutlierAnalysisAlgorithm<NumericalFeatureVector> {

	private ELKIDataWrapper elkiDataWrapper;

	OutlierAlgorithm outlierAlgorithm;

	protected OutlierResult elkiOutlierResult;

	private Map<NumericalFeatureVector, Double> outlierAnalysisResult;

	@Override
	public void setData(List<? extends NumericalFeatureVector> featureVectors) {
		elkiDataWrapper = new ELKIDataWrapper(featureVectors);
	}

	abstract void initializeOutlierAlgorithm();

	private void calculateOutlierAnalysisResult() {
		DoubleRelation scores = elkiOutlierResult.getScores();

		DBIDs iDs = scores.getDBIDs();

		Relation<NumberVector> rel = elkiDataWrapper.getRelation();

		outlierAnalysisResult = new LinkedHashMap<>();

		List<NumericalFeatureVector> fvs = new ArrayList<>();
		for (DBIDIter it = iDs.iter(); it.valid(); it.advance()) {
			NumberVector v = rel.get(it);

			NumericalFeatureVector fv = elkiDataWrapper.getFeatureVector(v);

			if (fv == null)
				throw new NullPointerException(getName() + ".getClusteringResult(): feature vector lookup failed.");

			fvs.add(fv);
			outlierAnalysisResult.put(fv, scores.doubleValue(it));
		}
	}

	@Override
	public double getOutlierScore(NumericalFeatureVector object) {
		Map<NumericalFeatureVector, Double> outlierAnalysisResult2 = getOutlierAnalysisResult();

		if (outlierAnalysisResult2 == null)
			return Double.NaN;

		return outlierAnalysisResult2.get(object);
	}

	@Override
	public void resetScores() {
		elkiOutlierResult = null;
	}

	protected ELKIDataWrapper getElkiDataWrapper() {
		return elkiDataWrapper;
	}

	public OutlierResult getElkiOutlierResult() {
		return elkiOutlierResult;
	}

	public Map<NumericalFeatureVector, Double> getOutlierAnalysisResult() {
		if (outlierAnalysisResult == null)
			calculateOutlierAnalysisResult();

		return outlierAnalysisResult;
	}

}
