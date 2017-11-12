package com.github.TKnudsen.DMandML.model.unsupervised.outliers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.IFeatureVectorDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.tools.MathFunctions;
import com.github.TKnudsen.ComplexDataObject.model.tools.StatisticsSupport;

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
 * Copyright: (c) 2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class DensityBasedOutlierAnalysisAlgorithm<FV extends AbstractFeatureVector<?, ?>> extends FeatureVectorOutlierAnalysisAlgorithm<FV> {

	private int nearestNeighborCount;

	private IFeatureVectorDistanceMeasure<FV> distanceMeasure;

	public DensityBasedOutlierAnalysisAlgorithm(List<FV> featureVectors, int nearestNeighborCount, IFeatureVectorDistanceMeasure<FV> distanceMeasure) {
		super(featureVectors);

		this.nearestNeighborCount = nearestNeighborCount;
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	public void run() {
		List<FV> featureVectorList = getFeatureVectors();

		outlierScores = new LinkedHashMap<>();
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

		StatisticsSupport distanceMeansStatistics = new StatisticsSupport(maxDistanceMeans);

		for (FV fv : featureVectorList)
			outlierScores.put(fv, MathFunctions.linearScale(0, distanceMeansStatistics.getMax(), outlierScores.get(fv)));
	}

	@Override
	public String getDescription() {
		return "Calculates outlier scores based on distances to nearest neighbors.";
	}

	@Override
	public void setData(List<FV> data) {
		// TODO Auto-generated method stub
		
	}

}
