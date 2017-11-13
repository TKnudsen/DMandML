package com.github.TKnudsen.DMandML.model.degreeOfInterest.classBased.labels;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.FeatureVectorSupplier;
import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResultSupplier;

/**
 * <p>
 * Title: LabelDistributionBasedInterestingnessFunction
 * </p>
 * 
 * <p>
 * Description: Compares an observed class distribution with a pre-given class
 * distribution. Accordingly, interestingness values for FVs are set to balance
 * the deviance.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public class PredictedLabelDistributionBasedInterestingnessFunction<FV extends AbstractFeatureVector<?, ?>> extends LabelDistributionBasedInterestingnessFunction<FV> {

	public PredictedLabelDistributionBasedInterestingnessFunction(FeatureVectorSupplier<FV> featureVectorSupplier, IProbabilisticClassificationResultSupplier<FV> classificationResultSupplier, Map<String, Double> targetLabelDistribution) {
		super(featureVectorSupplier, classificationResultSupplier, targetLabelDistribution);
	}

	@Override
	protected Map<String, Double> calculateObservedLabelDistribution() {
		// gather observed distribution
		Map<String, Double> observedLabelDistribution = new LinkedHashMap<>();
		for (String label : getTargetLabelDistribution().keySet())
			observedLabelDistribution.put(label, (double) observedLabelDistribution.size());

		return observedLabelDistribution;
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores to FVs according to a targeted label distribution";
	}
}
