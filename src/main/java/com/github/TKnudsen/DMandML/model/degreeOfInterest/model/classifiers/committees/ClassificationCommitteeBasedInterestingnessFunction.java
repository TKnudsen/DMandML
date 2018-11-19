package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees;

import com.github.TKnudsen.ComplexDataObject.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.tools.DataConversion;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.data.classification.LabelDistribution;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores of elements with respect to the uncertainty
 * of pre-given ClusteringResults
 * </p>
 * 
 * @version 1.01
 */
public abstract class ClassificationCommitteeBasedInterestingnessFunction<FV> implements IDegreeOfInterestFunction<FV> {

	private final List<IClassificationApplicationFunction<FV>> classificationResults;

	private final IDistanceMeasure<double[]> probabilityDistributionDistanceMeasure;

	public ClassificationCommitteeBasedInterestingnessFunction(
			List<IClassificationApplicationFunction<FV>> classificationResults,
			IDistanceMeasure<double[]> probabilityDistributionDistanceMeasure) {

		this.classificationResults = Objects.requireNonNull(classificationResults,
				"The classificationResults may not be null");

		this.probabilityDistributionDistanceMeasure = Objects.requireNonNull(probabilityDistributionDistanceMeasure,
				"The distance measure (usable for probability comparisons) may not be null");
	}

	protected final List<IClassificationResult<FV>> computeClassificationResults(List<? extends FV> featureVectors) {
		List<IClassificationResult<FV>> list = new ArrayList<>();
		for (IClassificationApplicationFunction<FV> classificationResult : classificationResults)
			list.add(classificationResult.apply(featureVectors));

		return list;
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> featureVectors) {

		Objects.requireNonNull(featureVectors);
		if (featureVectors.isEmpty()) {
			return Collections.emptyMap();
		}

		List<IClassificationResult<FV>> computeClassificationResult = computeClassificationResults(featureVectors);

		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();
		Collection<Number> values = new ArrayList<>();

		// check whether results can be calculated
		for (IClassificationResult<FV> result : computeClassificationResult)
			if (result == null) {
				for (FV fv : featureVectors)
					interestingnessScores.put(fv, 0.0);

				System.err.println(getName() + ": at least one classification result is null. returning 0.0 values");
				return interestingnessScores;
			}

		// create list of different votes (probabilities)
		Map<FV, List<List<Double>>> votePropabilities = computePropabilityDistributions(computeClassificationResult,
				featureVectors);

		for (FV fv : featureVectors) {
			List<List<Double>> probabilityDistributions = votePropabilities.get(fv);

			double ksValues = calculateEnsembleDisagreement(probabilityDistributions,
					probabilityDistributionDistanceMeasure);

			interestingnessScores.put(fv, ksValues);
			values.add(ksValues);
		}

		// post-processing
		NormalizationFunction normalizationFunction = new LinearNormalizationFunction(values);
		for (FV fv : interestingnessScores.keySet())
			interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

		return interestingnessScores;
	}

	protected final Map<FV, List<List<Double>>> computePropabilityDistributions(
			List<IClassificationResult<FV>> classificationResults, List<? extends FV> featureVectors) {

		Objects.requireNonNull(classificationResults);

		Map<FV, List<List<Double>>> voteProbabillitiesMap = new LinkedHashMap<>();
		for (FV fv : featureVectors) {

			// create list of different votes (probabilities)
			List<LabelDistribution> votes = new ArrayList<>();
			SortedSet<String> labelAlphabet = new TreeSet<>();

			for (IClassificationResult<FV> classificationResult : classificationResults) {
				LabelDistribution labelDistribution = classificationResult.getLabelDistribution(fv);
				votes.add(labelDistribution);
				labelAlphabet.addAll(labelDistribution.keySet());
			}

			// create list of vote probabilities
			List<List<Double>> probabilityDistributions = new ArrayList<>();
			for (LabelDistribution labelDistribution : votes) {
				// throws a NullPointerException if one of the labels does not exist in the
				// particular classification result.
				probabilityDistributions.add(labelDistribution.values(labelAlphabet));
			}

			voteProbabillitiesMap.put(fv, probabilityDistributions);
		}

		return voteProbabillitiesMap;
	}

	protected final Map<FV, List<Double>> computeVoteDistributions(
			List<IClassificationResult<FV>> classificationResults, List<? extends FV> featureVectors) {

		Objects.requireNonNull(classificationResults);

		SortedSet<String> labelAlphabet = new TreeSet<>();
		for (IClassificationResult<FV> classificationResult : classificationResults)
			labelAlphabet.addAll(classificationResult.getClassDistributions().keySet());

		Map<FV, List<Double>> voteProbabillitiesMap = new LinkedHashMap<>();

		// create votes
		for (FV fv : featureVectors) {
			Map<String, Double> wins = new LinkedHashMap<>();
			for (String label : labelAlphabet)
				wins.put(label, 0.0);

			for (IClassificationResult<FV> classificationResult : classificationResults)
				wins.put(classificationResult.getClass(fv), wins.get(classificationResult.getClass(fv)) + 1);

			voteProbabillitiesMap.put(fv, new ArrayList<>(wins.values()));
		}

		return voteProbabillitiesMap;
	}

	/**
	 * averages different probability distributions to yield a "probability
	 * distribution centroid".
	 * 
	 * @param distributions
	 * @return
	 */
	protected final List<Double> calculateConsensusProbabilities(List<List<Double>> distributions) {
		int distributionSize = distributions.get(0).size();

		List<Double> consensusDistribution = new ArrayList<>();
		for (int i = 0; i < distributionSize; i++)
			consensusDistribution.add(0d);

		for (List<Double> learnerDistribution : distributions) {
			for (int i = 0; i < distributionSize; i++) {
				double d = consensusDistribution.get(i) + learnerDistribution.get(i);
				consensusDistribution.set(i, d);
			}
		}

		// normalize
		for (int i = 0; i < distributionSize; i++) {
			double d = consensusDistribution.get(i) / distributions.size();
			consensusDistribution.set(i, d);
		}

		return consensusDistribution;
	}

	/**
	 * calcualtes the distance between the probability distributions. First a
	 * probability distribution centroid is calculated, referred to as the empirical
	 * means of all distributions. Then, each individual distribution is compared to
	 * the empirical means according to a distance measure.
	 * 
	 * Note: make sure that the distane measure is useful for comparing probability
	 * distributions!
	 * 
	 * @param distributions
	 * @param probabilityDistributionDistanceMeasure
	 * @return
	 */
	protected final double calculateEnsembleDisagreement(List<List<Double>> distributions,
			IDistanceMeasure<double[]> probabilityDistributionDistanceMeasure) {
		double[] empiricalDistribution = DataConversion.toPrimitives(calculateConsensusProbabilities(distributions));

		int learnerCounts = distributions.size();
		double result = 0;
		for (int i = 0; i < learnerCounts; i++) {
			double[] individualDistribution = DataConversion.toPrimitives(distributions.get(i));

			result += probabilityDistributionDistanceMeasure.getDistance(empiricalDistribution, individualDistribution);
		}

		return result;
	}

	@Override
	public String getDescription() {
		return getName();
	}

	@Override
	public String toString() {
		return getName();
	}
}
