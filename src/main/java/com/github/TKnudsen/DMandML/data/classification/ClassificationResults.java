package com.github.TKnudsen.DMandML.data.classification;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * <p>
 * Title: ClassificationResults
 * </p>
 * 
 * <p>
 * Description: ClassificationResult utility.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class ClassificationResults {

	protected static <X> Map<X, LabelDistribution> createLabelDistributionMap(
			Map<X, Map<String, Double>> labelDistributionMap) {
		Map<X, LabelDistribution> tmpLabelDistributionMap = new LinkedHashMap<>();

		for (X x : labelDistributionMap.keySet()) {
			Map<String, Double> labelDistribution = labelDistributionMap.get(x);

			if (labelDistribution == null)
				throw new IllegalArgumentException(
						"ClassificationResults.createLabelDistributionMap(...): label distribution for item "
								+ x.toString() + " was null");

			tmpLabelDistributionMap.put(x, new LabelDistribution(labelDistributionMap.get(x)));
		}

		return tmpLabelDistributionMap;
	}

	/**
	 * creates a LabelDistribution for every X, containing a distribution of 100% of
	 * the label and 0% of the remaining n-1 labels of the label alphabet.
	 * 
	 * @param labels
	 * @return
	 */
	protected static <X> Map<X, LabelDistribution> createDefaultLabelDistributionMap(Map<X, String> labels) {
		if (labels == null)
			return null;

		SortedSet<String> labelAlphabet = new TreeSet<>(labels.values());

		Map<X, LabelDistribution> labelDistributionMap = new LinkedHashMap<>();
		for (X x : labels.keySet()) {
			Map<String, Double> valueDistribution = new LinkedHashMap<>();
			for (String label : labelAlphabet)
				if (label.equals(labels.get(x)))
					valueDistribution.put(label, 1.0);
				else
					valueDistribution.put(label, 0.0);
			labelDistributionMap.put(x, new LabelDistribution(valueDistribution));
		}

		return labelDistributionMap;
	}

	// protected static <X> Map<X, String> createLabelsMap(Map<X, Map<String,
	// Double>> labelDistributionMap) {
	// Map<X, LabelDistribution> map =
	// createLabelDistributionMap(labelDistributionMap);
	//
	// Map<X, String> labelsMap = new LinkedHashMap<>();
	//
	// for (X x : map.keySet())
	// labelsMap.put(x, map.get(x).getRepresentant());
	//
	// return labelsMap;
	// }

	protected static <X> Map<X, String> createwinningLabelsMap(Map<X, LabelDistribution> labelDistributionMap) {
		Map<X, String> labelsMap = new LinkedHashMap<>();

		for (X x : labelDistributionMap.keySet())
			labelsMap.put(x, labelDistributionMap.get(x).getRepresentant());

		return labelsMap;
	}

}
