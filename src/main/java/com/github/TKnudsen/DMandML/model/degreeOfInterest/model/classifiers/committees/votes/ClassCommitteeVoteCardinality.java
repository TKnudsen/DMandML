package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.votes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.Double.EuclideanDistanceMeasure;
import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.MapUtils;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.ClassificationCommitteeBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Queries controversial instances/regions in the input space. Compares the
 * label distributions of every candidate for a given set of models. The winning
 * candidate poses those label distributions where the committee disagrees most.
 * 
 * Measure: Vote cardinality. Ratio of different votes divided by the number of
 * votes. Measure is discrete as the nr. of committee members limits the number
 * of different result nevieaus.
 * </p>
 * 
 * @version 1.03
 */
public class ClassCommitteeVoteCardinality<FV> extends ClassificationCommitteeBasedInterestingnessFunction<FV> {

	public ClassCommitteeVoteCardinality(List<IClassificationApplicationFunction<FV>> classificationResults) {
		super(classificationResults, new EuclideanDistanceMeasure());
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> featureVectors) {

		Objects.requireNonNull(featureVectors);
		if (featureVectors.isEmpty()) {
			return Collections.emptyMap();
		}

		List<IClassificationResult<FV>> classificationResults = computeClassificationResults(featureVectors);

		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();
		Collection<Number> values = new ArrayList<>();

		// check whether results can be calculated
		for (IClassificationResult<FV> result : classificationResults)
			if (result == null) {
				for (FV fv : featureVectors)
					interestingnessScores.put(fv, 0.0);

				System.err.println(getName() + ": at least one classification result is null. returning 0.0 values");
				return interestingnessScores;
			}

		// create list of different votes (probabilities)
		Map<FV, List<Double>> votesMap = computeVoteDistributions(classificationResults, featureVectors);

		for (FV fv : featureVectors) {
			List<Double> votes = votesMap.get(fv);

			double nrOfWinners = 0.0;
			for (Double d : votes)
				if (d > 0)
					nrOfWinners++;

			double ratio = (nrOfWinners - 1) / (double) votes.size();

			interestingnessScores.put(fv, ratio);
			values.add(ratio);
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

	@Override
	public String getName() {
		return "Class Committee Vote Cardinality";
	}
}