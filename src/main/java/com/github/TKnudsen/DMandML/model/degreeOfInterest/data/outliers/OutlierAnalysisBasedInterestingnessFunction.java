package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.outliers;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.ranking.Ranking;
import com.github.TKnudsen.ComplexDataObject.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.TKnudsen.DMandML.data.outliers.IOutlierAnalysisResult;
import com.github.TKnudsen.DMandML.model.retrieval.KNN;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores of elements on the basis of an outlier
 * analysis result. Outliers receive highest values.
 * </p>
 * 
 * In contrast to density estimation, outlier detection tries to find instances
 * in sparsely populated regions. It can be used to select instances with
 * untypical characteristics and helps to better sample the variability that
 * exists in the data and its classes, respectively. Outliers can be identified
 * by using different outlier scoring functions.
 * </p>
 * 
 * Outlier Detection (OUT) DOI/building block published in: Juergen Bernard,
 * Matthias Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair:
 * Towards User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.03
 */
public class OutlierAnalysisBasedInterestingnessFunction<FV> implements IDegreeOfInterestFunction<FV> {

	IOutlierAnalysisResult<FV> outlierAnalysisResult;

	private final boolean approximateScoresForMissingElements;

	private KNN<FV> kNNRetrieval;

	private int kNN;

	private IDistanceMeasure<FV> distanceMeasure;

	/**
	 * Constructor that does not allow the DOI to retrieve instances not contained
	 * in the outlier analysis result.
	 * 
	 * @param outlierAnalysisResult
	 */
	public OutlierAnalysisBasedInterestingnessFunction(IOutlierAnalysisResult<FV> outlierAnalysisResult) {
		this(outlierAnalysisResult, false, 3, null);
	}

	public OutlierAnalysisBasedInterestingnessFunction(IOutlierAnalysisResult<FV> outlierAnalysisResult,
			boolean approximateScoresForMissingElements, IDistanceMeasure<FV> distanceMeasure) {

		this(outlierAnalysisResult, approximateScoresForMissingElements, 3, distanceMeasure);
	}

	public OutlierAnalysisBasedInterestingnessFunction(IOutlierAnalysisResult<FV> outlierAnalysisResult,
			boolean approximateScoresForMissingElements, int kNN, IDistanceMeasure<FV> distanceMeasure) {

		this.outlierAnalysisResult = outlierAnalysisResult;
		this.approximateScoresForMissingElements = approximateScoresForMissingElements;
		this.kNN = kNN;
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> featureVectors) {

		Objects.requireNonNull(featureVectors);

		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();

		if (featureVectors.size() == 0)
			return interestingnessScores;

		for (FV fv : featureVectors) {

			if (outlierAnalysisResult.containsElement(fv)) {
				interestingnessScores.put(fv, outlierAnalysisResult.getOutlierScore(fv));
			} else {

				if (approximateScoresForMissingElements) {
					KNN<FV> knnRetrieval = getKNNRetrieval();
					Ranking<EntryWithComparableKey<Double, FV>> nearestNeighborsWithScores = knnRetrieval
							.getNearestNeighborsWithScores(fv);

					Double max = nearestNeighborsWithScores.getLast().getKey();
					NormalizationFunction normalizationFunction = new LinearNormalizationFunction(max, 0);

					double interestingnessScore = 0.0;
					double weight = 0.0;
					for (EntryWithComparableKey<Double, FV> entry : nearestNeighborsWithScores) {
						Number relativeWeight = normalizationFunction.apply(entry.getKey());
						interestingnessScore += (outlierAnalysisResult.getOutlierScore(entry.getValue())
								* relativeWeight.doubleValue());
						weight += relativeWeight.doubleValue();
					}

					if (weight == 0)
						interestingnessScores.put(fv, 0.0);
					else {
						interestingnessScores.put(fv, interestingnessScore / weight);
					}
				} else {
					System.err.println(getName() + ": unknown FV. set interestingness score to 0.0");
					interestingnessScores.put(fv, 0.0);
				}
			}
		}

		NormalizationFunction normalizationFunction = new LinearNormalizationFunction(interestingnessScores.values());
		for (FV fv : interestingnessScores.keySet())
			interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

		// large distance = outlier affine. no inversion of the map needed.
		return interestingnessScores;
	}

	private KNN<FV> getKNNRetrieval() {
		Objects.requireNonNull(distanceMeasure, getName() + ": distance measure was not set.");

		if (kNNRetrieval == null) {
			Collection<FV> elements = new ArrayList<>();

			Iterator<FV> iterator = outlierAnalysisResult.iterator();
			while (iterator.hasNext())
				elements.add(iterator.next());

			// +1 is used as a normalization trick later on
			kNNRetrieval = new KNN<FV>(kNN + 1, distanceMeasure, elements);
		}

		return kNNRetrieval;
	}

	@Override
	public String getName() {
		return "Outlier Analysis [" + outlierAnalysisResult.getName() + "]";
	}

	@Override
	public String getDescription() {
		return getName();
	}

	public boolean isApproximateScoresForMissingElements() {
		return approximateScoresForMissingElements;
	}

}
