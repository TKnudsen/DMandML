package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.probabilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.ranking.Ranking;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.tools.DataConversion;
import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.data.classification.LabelDistribution;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.MapUtils;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.ClassificationBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.retrieval.KNN;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Spatial Class Divergence measure. Uses the probability distributions of
 * instances in the vicinity and compares them with the probabilities of an
 * instance i. Divergence measures such as the Kullback Leibler divergence can
 * then be used to assess the local divergence.
 * 
 * The more divergent the lower the interestingness score (max-min
 * normalization).
 * </p>
 * 
 * Measure: Euclidean distance measure
 * </p>
 * 
 * Builds upon the Class Likelihood (CL) DOI/building block (probability
 * distribution per instance) published in: Juergen Bernard, Matthias
 * Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair: Towards
 * User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.04
 */
public abstract class SpatialClassProbabilitiesDivergenceInterestingnessFunction<FV>
		extends ClassificationBasedInterestingnessFunction<FV> {

	private final int kNN;

	private final IDistanceMeasure<FV> distanceMeasure;

	private KNN<FV> kNNRetrieval;

	private final IDistanceMeasure<double[]> divergenceDistanceMeasure;

	public SpatialClassProbabilitiesDivergenceInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction, int kNN,
			IDistanceMeasure<FV> distanceMeasure, IDistanceMeasure<double[]> divergenceDistanceMeasure) {
		this(probabilisticClassificationResultFunction, kNN, distanceMeasure, divergenceDistanceMeasure, null);
	}

	public SpatialClassProbabilitiesDivergenceInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction, int kNN,
			IDistanceMeasure<FV> distanceMeasure, IDistanceMeasure<double[]> divergenceDistanceMeasure,
			String classifierName) {
		super(probabilisticClassificationResultFunction, classifierName);

		this.kNN = kNN;
		this.distanceMeasure = distanceMeasure;
		this.divergenceDistanceMeasure = divergenceDistanceMeasure;
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> featureVectors) {
		Objects.requireNonNull(featureVectors);
		if (featureVectors.isEmpty())
			return Collections.emptyMap();

		IClassificationResult<FV> classificationResult = computeClassificationResult(featureVectors);

		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();
		Collection<Number> values = new ArrayList<>();

		if (classificationResult == null || classificationResult.getClassDistributions() == null) {
			for (FV fv : featureVectors)
				interestingnessScores.put(fv, 0.0);

			return interestingnessScores;
		}

		Set<String> labelAlphabet = classificationResult.getLabelAlphabet();

		// the last NN will receive weight 0.
		kNNRetrieval = new KNN<FV>(kNN + 1, distanceMeasure, featureVectors);

		for (FV referenceFV : featureVectors) {
			LabelDistribution referenceLabelDistribution = classificationResult.getLabelDistribution(referenceFV);
			List<Double> probabilities = referenceLabelDistribution.values(labelAlphabet);
			double[] instanceVector = DataConversion.toPrimitives(probabilities);

			Ranking<EntryWithComparableKey<Double, FV>> nearestNeighborsWithScores = kNNRetrieval
					.getNearestNeighborsWithScores(referenceFV);

			double[] empiricalVector = new double[instanceVector.length];
			double weights = 0.0;

			for (EntryWithComparableKey<Double, FV> element : nearestNeighborsWithScores) {
				FV nearFV = element.getValue();
				LabelDistribution labelDistribution = classificationResult.getLabelDistribution(nearFV);
				double[] v = DataConversion.toPrimitives(labelDistribution.values(labelAlphabet));
				for (int i = 0; i < v.length; i++)
					empiricalVector[i] += v[i] * element.getKey();
				weights += element.getKey();
			}

			for (int i = 0; i < empiricalVector.length; i++)
				empiricalVector[i] /= weights;

			double value = divergenceDistanceMeasure.getDistance(empiricalVector, instanceVector);

			// for validation purposes
			if (Double.isInfinite(value)) {
				System.err.println(getName() + ": Infinite value detected.");
				divergenceDistanceMeasure.getDistance(empiricalVector, instanceVector);
			}

			if (weights == 0)
				interestingnessScores.put(referenceFV, 0.0);
			else {
				interestingnessScores.put(referenceFV, value);
				values.add(value);
			}
		}

		// for validation purposes
		if (MapUtils.doiValidationMode) {
			MapUtils.checkForCriticalValue(interestingnessScores, null, true);
			MapUtils.checkForCriticalValue(interestingnessScores, Double.NaN, true);
			MapUtils.checkForCriticalValue(interestingnessScores, Double.NEGATIVE_INFINITY, true);
			MapUtils.checkForCriticalValue(interestingnessScores, Double.POSITIVE_INFINITY, true);
		}

		// normalization: [max-min], highest divergence will have zero interestingness
		return MapUtils.normalizeValuesMaxMin(interestingnessScores);
	}

	@Override
	public String getName() {
		return "Spatial Class Probabilities Divergence: " + getClassifierName();
	}

	@Override
	public String getDescription() {
		return getName();
	}

}
