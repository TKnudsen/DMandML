package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2019 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores of elements with respect to the (dis-)
 * agreement of a committee.
 * </p>
 * 
 * @version 1.04
 */
public abstract class ClassificationCommitteeBasedInterestingnessFunction<FV> implements IDegreeOfInterestFunction<FV> {

	private final List<IClassificationApplicationFunction<FV>> classificationResults;

	public ClassificationCommitteeBasedInterestingnessFunction(
			List<IClassificationApplicationFunction<FV>> classificationResults) {

		this.classificationResults = Objects.requireNonNull(classificationResults,
				"The classificationResults may not be null");
	}

	protected final List<IClassificationResult<FV>> computeClassificationResults(List<? extends FV> featureVectors) {
		List<IClassificationResult<FV>> list = new ArrayList<>();
		for (IClassificationApplicationFunction<FV> classificationResult : classificationResults)
			list.add(classificationResult.apply(featureVectors));

		return list;
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
