package com.github.TKnudsen.DMandML.data.classification;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * Title: ProbabilisticClassificationResult
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
 * @version 1.03
 */
public class ProbabilisticClassificationResult<X> extends ClassificationResult<X>
		implements IProbabilisticClassificationResult<X> {

	Map<X, LabelDistribution> labelDistributionMap;

	/**
	 * constructor stores a reference on the object.
	 * 
	 * @param labelDistributionMap
	 */
	public ProbabilisticClassificationResult(Map<X, Map<String, Double>> labelDistributionMap) {
		super(createLabelsMap(labelDistributionMap));

		this.labelDistributionMap = createLabelDistributionMap(labelDistributionMap);
	}

	@Override
	public LabelDistribution getLabelDistribution(X x) {
		return labelDistributionMap.get(x);
	}

	protected static <X> Map<X, LabelDistribution> createLabelDistributionMap(
			Map<X, Map<String, Double>> labelDistributionMap) {
		Map<X, LabelDistribution> tmpLabelDistributionMap = new LinkedHashMap<>();

		for (X x : labelDistributionMap.keySet()) {
			Map<String, Double> labelDistribution = labelDistributionMap.get(x);

			if (labelDistribution == null)
				throw new IllegalArgumentException(
						"ProbabilisticClassificationResult.createLabelDistributionMap(...): label distribution for item "
								+ x.toString() + " was null");

			tmpLabelDistributionMap.put(x, new LabelDistribution(labelDistributionMap.get(x)));
		}

		return tmpLabelDistributionMap;
	}

	protected static <X> Map<X, String> createLabelsMap(Map<X, Map<String, Double>> labelDistributionMap) {
		Map<X, LabelDistribution> map = createLabelDistributionMap(labelDistributionMap);

		Map<X, String> labelsMap = new LinkedHashMap<>();

		for (X x : map.keySet())
			labelsMap.put(x, map.get(x).getRepresentant());

		return labelsMap;
	}

}
