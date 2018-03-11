package com.github.TKnudsen.DMandML.model.semiSupervised.activeLearning.uncertaintySampling;

import java.util.HashMap;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.ComplexDataObject.data.ranking.Ranking;
import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResultSupplier;
import com.github.TKnudsen.DMandML.model.semiSupervised.activeLearning.AbstractActiveLearningModel;

/**
 * <p>
 * Title: SmallestMarginActiveLearning
 * </p>
 * 
 * <p>
 * Description: a baseline active learning model seeking the smallest difference
 * between the first and second most probable class labels among all instances.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.04
 */
public class SmallestMarginActiveLearning<FV extends IFeatureVectorObject<?, ?>>
		extends AbstractActiveLearningModel<FV> {
	protected SmallestMarginActiveLearning() {
	}

	public SmallestMarginActiveLearning(IProbabilisticClassificationResultSupplier<FV> classificationResultSupplier) {
		super(classificationResultSupplier);
	}

	@Override
	protected void calculateRanking() {
		ranking = new Ranking<>();
		queryApplicabilities = new HashMap<>();
		remainingUncertainty = 0.0;

		for (FV fv : candidates) {
			double margin = calculateMargin(fv);
			ranking.add(new EntryWithComparableKey<Double, FV>(margin, fv));

			queryApplicabilities.put(fv, 1 - margin);
			remainingUncertainty += (1 - margin);
		}

		remainingUncertainty /= (double) candidates.size();
		System.out.println("SmallestMarginActiveLearning: remaining uncertainty = " + remainingUncertainty);
	}

	private double calculateMargin(FV fv) {
		IProbabilisticClassificationResultSupplier<FV> classificationResultSupplier = getClassificationResultSupplier();
		Map<String, Double> labelDistribution = null;
		labelDistribution = classificationResultSupplier.get().getLabelDistribution(fv).getValueDistribution();

		if (labelDistribution == null)
			return 0;

		double max = Double.MIN_VALUE;
		double second = Double.MIN_VALUE;
		for (double value : labelDistribution.values())
			if (max <= value) {
				second = max;
				max = value;
			} else if (second <= value)
				second = value;

		return max - second;
	}

	@Override
	public String getName() {
		return "Smallest Margin";
	}

	@Override
	public String getDescription() {
		return getName();
	}
}
