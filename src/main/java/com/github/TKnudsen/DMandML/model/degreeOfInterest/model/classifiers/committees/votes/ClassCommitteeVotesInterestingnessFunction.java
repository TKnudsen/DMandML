package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.votes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.MapUtils;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.ClassificationCommitteeBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2019 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Queries controversial instances/regions in the input space. Compares the
 * different votes (the winning label) of an ensemble.
 * </p>
 * 
 * @version 1.02
 */
public abstract class ClassCommitteeVotesInterestingnessFunction<FV>
		extends ClassificationCommitteeBasedInterestingnessFunction<FV> {

	public ClassCommitteeVotesInterestingnessFunction(
			List<IClassificationApplicationFunction<FV>> classificationResults) {
		super(classificationResults);
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> featureVectors) {

		Objects.requireNonNull(featureVectors);
		if (featureVectors.isEmpty()) {
			return Collections.emptyMap();
		}

		List<IClassificationResult<FV>> classificationResults = computeClassificationResults(featureVectors);

		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();

		// check whether results can be calculated
		for (IClassificationResult<FV> result : classificationResults)
			if (result == null) {
				for (FV fv : featureVectors)
					interestingnessScores.put(fv, 0.0);

				System.err.println(getName() + ": at least one classification result is null. returning 0.0 values");
				return interestingnessScores;
			}

		// calculate the interestingness values
		Map<FV, List<Double>> votesMap = computeVoteDistributions(classificationResults, featureVectors);

		for (FV fv : featureVectors) {
			double value = computeClassCommitteeVoteInterestingness(fv, votesMap.get(fv));

			interestingnessScores.put(fv, value);
		}

		// for validation purposes
		if (MapUtils.doiValidationMode) {
			MapUtils.checkForCriticalValue(interestingnessScores, null, true);
			MapUtils.checkForCriticalValue(interestingnessScores, Double.NaN, true);
			MapUtils.checkForCriticalValue(interestingnessScores, Double.NEGATIVE_INFINITY, true);
			MapUtils.checkForCriticalValue(interestingnessScores, Double.POSITIVE_INFINITY, true);
		}

		// normalize
		return MapUtils.normalizeValuesMinMax(interestingnessScores);
	}

	protected abstract double computeClassCommitteeVoteInterestingness(FV fv, List<Double> votesDistribution);

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

	@Override
	public String getName() {
		return "Class Committee Vote Entropy";
	}

}
