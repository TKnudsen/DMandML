package com.github.TKnudsen.DMandML.data.classification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.model.tools.MathFunctions;

/**
 * <p>
 * Title: LabelDistributionTools
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2015-2017
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class LabelDistributionTools {

	/**
	 * merges a series of LabelDistributions.
	 * 
	 * @param labelDistributions
	 * @return
	 */
	public static LabelDistribution mergelabelDistributions(Collection<LabelDistribution> labelDistributions) {
		if (labelDistributions == null)
			return null;

		Map<String, List<Double>> valueDistributions = new LinkedHashMap<>();

		for (LabelDistribution labelDistribution : labelDistributions) {
			if (labelDistribution == null)
				continue;

			for (String label : labelDistribution.getLabelSet()) {
				if (valueDistributions.get(label) == null)
					valueDistributions.put(label, new ArrayList<>());

				valueDistributions.get(label).add(labelDistribution.getValueDistribution().get(label));
			}
		}

		Map<String, Double> valueDistribution = new LinkedHashMap<>();

		for (String label : valueDistributions.keySet())
			valueDistribution.put(label, MathFunctions.getMean(valueDistributions.get(label)));

		return new LabelDistribution(valueDistribution);
	}
}
