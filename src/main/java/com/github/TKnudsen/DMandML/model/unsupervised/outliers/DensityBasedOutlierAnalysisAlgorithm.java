package com.github.TKnudsen.DMandML.model.unsupervised.outliers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.tools.StatisticsSupport;
import com.github.TKnudsen.DMandML.data.outliers.OutlierAnalysisResult;

/**
 * <p>
 * Title: DensityBasedOutlierAnalysisAlgorithm
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public class DensityBasedOutlierAnalysisAlgorithm<FV> extends OutlierAnalysisAlgorithm<FV> {

	private int nearestNeighborCount;

	private IDistanceMeasure<FV> distanceMeasure;

	public DensityBasedOutlierAnalysisAlgorithm(List<FV> featureVectors, int nearestNeighborCount,
			IDistanceMeasure<FV> distanceMeasure) {
		super(featureVectors);

		this.nearestNeighborCount = nearestNeighborCount;
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	public void run() {
		List<FV> featureVectorList = getFeatureVectors();

		if (featureVectorList == null)
			throw new NullPointerException(getName() + ": cannot be run until feature vectors are set");

		Map<FV, Double> outlierScores = new LinkedHashMap<>();
		List<Double> maxDistanceMeans = new ArrayList<>();

		for (FV fv : featureVectorList) {
			List<Double> distances = new ArrayList<>();

			for (FV fv2 : featureVectorList) {
				if (fv.equals(fv2))
					continue;

				distances.add(distanceMeasure.getDistance(fv, fv2));
			}

			Collections.sort(distances);
			distances = distances.subList(0, nearestNeighborCount);

			StatisticsSupport statistics = new StatisticsSupport(distances);

			outlierScores.put(fv, statistics.getMean());
			maxDistanceMeans.add(statistics.getMean());
		}

		// StatisticsSupport distanceMeansStatistics = new
		// StatisticsSupport(maxDistanceMeans);
		//
		// for (FV fv : featureVectorList)
		// outlierScores.put(fv,
		// MathFunctions.linearScale(0, distanceMeansStatistics.getMax(),
		// outlierScores.get(fv)));

		this.outlierAnalysisResult = new OutlierAnalysisResult<FV>(outlierScores);
	}

	@Override
	void calculateOutlierAnalysisResult() {
		run();
	}

	@Override
	public String getDescription() {
		return "Calculates outlier scores based on distances to nearest neighbors.";
	}

	@Override
	public void setData(List<? extends FV> data) {
		this.setFeatureVectors(data);
	}

}
