package com.github.TKnudsen.DMandML.data.classification;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.uncertainty.string.LabelUncertainty;

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
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public class ProbabilisticClassificationResult<X> extends ClassificationResult<X> implements IProbabilisticClassificationResult<X> {

	Map<X, LabelUncertainty> labelDistributionMap;

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
	public LabelUncertainty getLabelDistribution(X x) {
		return labelDistributionMap.get(x);
	}

	protected static <X> Map<X, LabelUncertainty> createLabelDistributionMap(Map<X, Map<String, Double>> labelDistributionMap) {
		Map<X, LabelUncertainty> tmpLabelDistributionMap = new LinkedHashMap<>();

		for (X x : labelDistributionMap.keySet())
			tmpLabelDistributionMap.put(x, new LabelUncertainty(labelDistributionMap.get(x)));

		return tmpLabelDistributionMap;
	}

	protected static <X> Map<X, String> createLabelsMap(Map<X, Map<String, Double>> labelDistributionMap) {
		Map<X, LabelUncertainty> map = createLabelDistributionMap(labelDistributionMap);

		Map<X, String> labelsMap = new LinkedHashMap<>();

		for (X x : map.keySet())
			labelsMap.put(x, map.get(x).getRepresentant());

		return labelsMap;
	}

}
