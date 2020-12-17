package com.github.TKnudsen.DMandML.model.unsupervised.outliers.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.tools.StatisticsSupport;
import com.github.TKnudsen.DMandML.data.outliers.OutlierAnalysisResult;
import com.github.TKnudsen.DMandML.model.retrieval.kNN.KNN;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.OutlierAnalysisAlgorithm;

/**
 * 
 * Outlier analysis algorithm using the principles from the median absolute
 * deviation (MAD).
 * 
 * MAD is a robust measure of the variability of a univariate sample of
 * quantitative data. It can also refer to the population parameter that is
 * estimated by the MAD calculated from a sample. In the notion of the outlier
 * analysis algorithm, samples are the distances to the kNN.
 * 
 * The implementation builds upon the following reference: Leys, C.; et al.
 * (2013). "Detecting outliers: Do not use standard deviation around the mean,
 * use absolute deviation around the median" (PDF). Journal of Experimental
 * Social Psychology. 49 (4): 764–766. doi:10.1016/j.jesp.2013.03.013.
 * 
 * <p>
 * Copyright: (c) 2020 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 * 
 * @param <FV>
 */
public class MedianAbsoluteDeviation<FV> extends OutlierAnalysisAlgorithm<FV> {

	/**
	 * constant linked to the assumption of normality of the data
	 */
	private static double b = 1.4826;

	/**
	 * stringency of the researcher's criteria. 3 (very conservative), 2.5
	 * (moderately conservative) or even 2 (poorly conservative)
	 */
	private double stringencyThreshold = 3.0;

	private final int kNN;

	private final IDistanceMeasure<FV> distanceMeasure;

	private KNN<FV> kNNRetrieval;

	public MedianAbsoluteDeviation(IDistanceMeasure<FV> distanceMeasure) {
		this(1, distanceMeasure);
	}

	public MedianAbsoluteDeviation(int kNN, IDistanceMeasure<FV> distanceMeasure) {
		this(kNN, distanceMeasure, 3);
	}

	public MedianAbsoluteDeviation(int kNN, IDistanceMeasure<FV> distanceMeasure, double stringencyThreshold,
			List<? extends FV> featureVectors) {
		this(kNN, distanceMeasure, stringencyThreshold);

		setFeatureVectors(featureVectors);
	}

	/**
	 * default constructor. FVs are usually set later.
	 * 
	 * @param kNN
	 * @param distanceMeasure
	 * @param stringencyThreshold
	 */
	public MedianAbsoluteDeviation(int kNN, IDistanceMeasure<FV> distanceMeasure, double stringencyThreshold) {
		if (kNN < 1)
			throw new IllegalArgumentException(getName() + ": kNN (" + kNN + ") must be greater than zero");
		this.kNN = kNN;

		this.distanceMeasure = distanceMeasure;

		if (stringencyThreshold < 2 || stringencyThreshold > 3)
			throw new IllegalArgumentException(
					getName() + ": stringencyThreshold (" + stringencyThreshold + ") must be [2...3]");
		this.stringencyThreshold = stringencyThreshold;
	}

	@Override
	public void setData(List<? extends FV> data) {
		this.setFeatureVectors(data);
	}

	@Override
	public void setFeatureVectors(List<? extends FV> featureVectors) {
		super.setFeatureVectors(featureVectors);

		kNNRetrieval = new KNN<>(kNN, distanceMeasure, getFeatureVectors());
	}

	@Override
	public void run() {
		List<FV> featureVectorList = getFeatureVectors();

		if (featureVectorList == null)
			throw new NullPointerException(getName() + ": cannot be run until feature vectors are set");

		// store kNN distances
		Map<FV, Double> distances = new LinkedHashMap<>();
		for (FV fv : featureVectorList) {
			List<Entry<FV, Double>> neighbors = kNNRetrieval.retrieveNeighbors(fv);

			distances.put(fv, neighbors.get(0).getValue());
		}

		// median of distances
		StatisticsSupport statistics = new StatisticsSupport(distances.values());
		double median = statistics.getMedian();

		// subtract median from each distance, store absolute value
		List<Double> values = new ArrayList<>();
		for (FV fv : featureVectorList)
			values.add(Math.abs(distances.get(fv) - median));

		// median of the intermediate result
		statistics = new StatisticsSupport(values);
		double MAD = statistics.getMedian() * b;

		// calculate outlier scores
		Map<FV, Double> outlierScores = new LinkedHashMap<>();
		for (FV fv : featureVectorList) {
			double dist = distances.get(fv);
			if (dist < median - stringencyThreshold * MAD)
				outlierScores.put(fv, 1.0);
			else if (dist > median + stringencyThreshold * MAD)
				outlierScores.put(fv, 1.0);
			else
				outlierScores.put(fv, 0.0);
		}

		this.outlierAnalysisResult = new OutlierAnalysisResult<FV>(outlierScores, getName());
	}

	@Override
	public String getDescription() {
		return "the mean absolute deviation (MAD) criterion applied on the distances of instances to their kth nearest neighbor where k is a user parameter (defaut:1)";
	}

	@Override
	public void calculateOutlierAnalysisResult() {
		run();
	}

	public double getStringencyThreshold() {
		return stringencyThreshold;
	}

	public void setStringencyThreshold(double stringencyThreshold) {
		this.stringencyThreshold = stringencyThreshold;
	}

}
