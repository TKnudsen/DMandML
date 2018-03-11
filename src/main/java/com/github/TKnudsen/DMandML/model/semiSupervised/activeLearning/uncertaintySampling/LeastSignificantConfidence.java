package com.github.TKnudsen.DMandML.model.semiSupervised.activeLearning.uncertaintySampling;

import java.util.HashMap;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.ComplexDataObject.data.ranking.Ranking;
import com.github.TKnudsen.ComplexDataObject.model.tools.MathFunctions;
import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResultSupplier;
import com.github.TKnudsen.DMandML.model.semiSupervised.activeLearning.AbstractActiveLearningModel;

/**
 * <p>
 * Title: LeastSignificantConfidence
 * </p>
 * 
 * <p>
 * Description: a baseline active learning model seeking the lowest maximum
 * likelihood among all instances.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.05
 */
public class LeastSignificantConfidence<FV extends IFeatureVectorObject<?, ?>> extends AbstractActiveLearningModel<FV> {
	protected LeastSignificantConfidence() {
	}

	public LeastSignificantConfidence(IProbabilisticClassificationResultSupplier<FV> classificationResultSupplier) {
		super(classificationResultSupplier);
	}

	@Override
	protected void calculateRanking() {
		IProbabilisticClassificationResultSupplier<FV> classificationResultSupplier = getClassificationResultSupplier();

		ranking = new Ranking<>();
		queryApplicabilities = new HashMap<>();
		remainingUncertainty = 0.0;

		// calculate overall score
		for (FV fv : candidates) {
			double likelihood = calculateMaxProbability(fv);
			ranking.add(new EntryWithComparableKey<Double, FV>(likelihood, fv));
			queryApplicabilities.put(fv, 1 - likelihood);
			remainingUncertainty += (1 - likelihood);
		}

		remainingUncertainty /= (double) candidates.size();
		System.out.println("LastSignificantConfidence: remaining uncertainty = " + remainingUncertainty);
	}

	private double calculateMaxProbability(FV fv) {
		Map<String, Double> labelDistribution = null;

		IProbabilisticClassificationResultSupplier<FV> classificationResultSupplier = getClassificationResultSupplier();
		labelDistribution = classificationResultSupplier.get().getLabelDistribution(fv).getValueDistribution();

		if (labelDistribution == null)
			return 0;

		Double[] array = labelDistribution.values().toArray(new Double[0]);
		return MathFunctions.getMax(array);
	}

	@Override
	public String getName() {
		return "Least Significant Confidence";
	}

	@Override
	public String getDescription() {
		return "Focus on instances with weakest winning labels.";
	}

}