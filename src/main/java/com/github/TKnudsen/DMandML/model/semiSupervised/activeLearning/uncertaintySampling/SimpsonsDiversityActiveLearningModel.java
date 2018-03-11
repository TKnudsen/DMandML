package com.github.TKnudsen.DMandML.model.semiSupervised.activeLearning.uncertaintySampling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.ComplexDataObject.data.ranking.Ranking;
import com.github.TKnudsen.ComplexDataObject.model.statistics.SimpsonsIndex;
import com.github.TKnudsen.ComplexDataObject.model.tools.DataConversion;
import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResultSupplier;
import com.github.TKnudsen.DMandML.model.semiSupervised.activeLearning.AbstractActiveLearningModel;

/**
 * <p>
 * Title: SimpsonsDiversityActiveLearningModel
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.04
 */
public class SimpsonsDiversityActiveLearningModel<FV extends IFeatureVectorObject<?, ?>>
		extends AbstractActiveLearningModel<FV> {
	protected SimpsonsDiversityActiveLearningModel() {
	}

	public SimpsonsDiversityActiveLearningModel(
			IProbabilisticClassificationResultSupplier<FV> classificationResultSupplier) {
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
			double v1 = getLabelProbabilityDiversity(fv);

			ranking.add(new EntryWithComparableKey<Double, FV>(v1, fv));

			queryApplicabilities.put(fv, 1 - v1);
			remainingUncertainty += (1 - v1);
		}

		remainingUncertainty /= (double) candidates.size();
		System.out.println("SimpsonsDiveristyActiveLearningModel: remaining uncertainty = " + remainingUncertainty);
	}

	/**
	 * anticipates the Simpson's Diversity index. Challenge: a probability
	 * distribution has to be abstracted to an array of integer-like values, all
	 * >=1.
	 * 
	 * @param labelDistribution
	 * @return
	 */
	public double getLabelProbabilityDiversity(FV fv) {
		IProbabilisticClassificationResultSupplier<FV> classificationResultSupplier = getClassificationResultSupplier();

		Map<String, Double> labelDistribution = null;
		labelDistribution = classificationResultSupplier.get().getLabelDistribution(fv).getValueDistribution();

		if (labelDistribution == null)
			return 0;

		double[] histogram = DataConversion.toPrimitives(new ArrayList<>(labelDistribution.values()));

		// convert a double distribution to an int distribution
		// afterwards the lowest double value will have the value 1
		int[] distribution = SimpsonsIndex.transformToIntDistribution(histogram);

		return SimpsonsIndex.calculateSimpsonsIndexOfDiversity(distribution);
	}

	@Override
	public String getName() {
		return "Simpsons Diversity";
	}

	@Override
	public String getDescription() {
		return getName();
	}
}
