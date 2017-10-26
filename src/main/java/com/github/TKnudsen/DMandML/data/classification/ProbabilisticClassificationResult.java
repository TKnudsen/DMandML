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
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public class ProbabilisticClassificationResult<X> extends ClassificationResult<X> implements IProbabilisticClassificationResult<X> {

	Map<X, LabelDistribution> labelDistributionMap;

	/**
	 * constructor stores a refernce on the object.
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

	protected static <X> Map<X, LabelDistribution> createLabelDistributionMap(Map<X, Map<String, Double>> labelDistributionMap) {
		Map<X, LabelDistribution> tmpLabelDistributionMap = new LinkedHashMap<>();

		for (X x : labelDistributionMap.keySet())
			tmpLabelDistributionMap.put(x, new LabelDistribution(labelDistributionMap.get(x)));

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
