package com.github.TKnudsen.DMandML.model.semiSupervised.activeLearning.queryByCommittee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.ComplexDataObject.data.ranking.Ranking;
import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResultSupplier;

/**
 * <p>
 * Title: ProbabilityDistanceBasedQueryByCommittee
 * </p>
 * 
 * <p>
 * Description: queries controversial instances/regions in the input space.
 * Compares the label distributions of every candidate for a given set of
 * models. The winning candidate poses those label distributions where the
 * committee disagrees most.
 * 
 * Measure: Euclidean distances of probability distributions
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.06
 */
public class ProbabilityDistanceBasedQueryByCommittee<FV extends IFeatureVectorObject<?, ?>>
		extends AbstractQueryByCommitteeActiveLearning<FV> {

	protected ProbabilityDistanceBasedQueryByCommittee() {
	}

	public ProbabilityDistanceBasedQueryByCommittee(
			List<IProbabilisticClassificationResultSupplier<FV>> classificationResultSuppliers) {
		super(classificationResultSuppliers);
	}

	@Override
	public String getComparisonMethod() {
		return "Measures the distances between the label distributions using the Euclidean distance.";
	}

	@Override
	protected void calculateRanking() {

		List<IProbabilisticClassificationResultSupplier<FV>> classificationResultSuppliers = getClassificationResultSuppliers();

		ranking = new Ranking<>();
		queryApplicabilities = new HashMap<>();
		remainingUncertainty = 0.0;

		// calculate overall score
		for (FV fv : candidates) {
			List<Map<String, Double>> labelDistributions = new ArrayList<>();
			for (IProbabilisticClassificationResultSupplier<FV> result : classificationResultSuppliers)
				labelDistributions.add(result.get().getLabelDistribution(fv).getValueDistribution());

			// create unified distribution arrays
			Set<String> labelSet = new HashSet<>();
			for (Map<String, Double> map : labelDistributions)
				if (map != null)
					for (String s : map.keySet())
						labelSet.add(s);

			List<List<Double>> distributions = new ArrayList<>();
			for (Map<String, Double> map : labelDistributions) {
				if (map == null)
					continue;
				List<Double> values = new ArrayList<>();
				for (String s : labelSet)
					if (map.get(s) != null)
						values.add(map.get(s));
					else
						values.add(0.0);
				distributions.add(values);
			}

			double dist = 0;

			// calculate pairwise distances
			for (int i = 0; i < distributions.size() - 1; i++)
				for (int j = i + 1; j < distributions.size(); j++)
					dist += calculateDistance(distributions.get(i), distributions.get(j));
			if (labelSet.size() > 0)
				dist /= ((distributions.size() - 1) * (distributions.size() + 1 - 1) * 0.5);
			else
				dist = 1;
			dist = (Math.max(0, Math.min(dist, 1)));
			// update ranking

			ranking.add(new EntryWithComparableKey<Double, FV>(1 - dist, fv));

			queryApplicabilities.put(fv, dist);
			remainingUncertainty += dist;
		}

		remainingUncertainty /= (double) candidates.size();
		System.out.println("ProbabilityDistanceBasedQueryByCommittee: remaining uncertainty = " + remainingUncertainty);

	}

	private double calculateDistance(List<Double> v1, List<Double> v2) {
		double d = 0;
		for (int i = 0; i < Math.min(v1.size(), v2.size()); i++)
			d += Math.pow(v1.get(i).doubleValue() - v2.get(i).doubleValue(), 2);
		d = Math.sqrt(d);

		return d;
	}

	@Override
	public String getName() {
		return "Probability Distance QBC";
	}

	@Override
	public String getDescription() {
		return getName();
	}
}
