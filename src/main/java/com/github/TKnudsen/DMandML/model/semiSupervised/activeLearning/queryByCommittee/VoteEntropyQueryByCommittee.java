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
import com.github.TKnudsen.ComplexDataObject.model.statistics.Entropy;
import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResultSupplier;

/**
 * <p>
 * Title: VoteEntropyQueryByCommittee
 * </p>
 * 
 * <p>
 * Description: queries controversial instances/regions in the input space.
 * Compares the label distributions of every candidate for a given set of
 * models. The winning candidate poses those label distributions where the
 * committee disagrees most.
 * 
 * Measure: Vote Entropy
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.06
 */
public class VoteEntropyQueryByCommittee<FV extends IFeatureVectorObject<?, ?>>
		extends AbstractQueryByCommitteeActiveLearning<FV> {
	protected VoteEntropyQueryByCommittee() {
	}

	public VoteEntropyQueryByCommittee(
			List<IProbabilisticClassificationResultSupplier<FV>> classificationResultSuppliers) {

		super(classificationResultSuppliers);
	}

	@Override
	public String getComparisonMethod() {
		return "Uses entropy to identify instances where models disagree";
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

			if (distributions != null && distributions.size() > 0) {
				Map<String, Double> winningLabels = new HashMap();
				for (IProbabilisticClassificationResultSupplier<FV> result : classificationResultSuppliers) {
					String label = result.get().getLabelDistribution(fv).getRepresentant();
					if (!winningLabels.containsKey(label))
						winningLabels.put(label, 1.0);
					else
						winningLabels.put(label, winningLabels.get(label) + 1.0);

				}

				for (String label : winningLabels.keySet())
					winningLabels.put(label, winningLabels.get(label) / (double) classificationResultSuppliers.size());

				dist = Entropy.calculateEntropy(winningLabels);
			} else

				dist = 1;

			// update ranking

			ranking.add(new EntryWithComparableKey<Double, FV>(1 - dist, fv));

			queryApplicabilities.put(fv, dist);
			remainingUncertainty += dist;
		}

		remainingUncertainty /= (double) candidates.size();
		System.out.println("VoteEntropyQueryByCommittee: remaining uncertainty = " + remainingUncertainty);
	}

	@Override
	public String getName() {
		return "Vote Entropy QBC";
	}

	@Override
	public String getDescription() {
		return getName();
	}
}
