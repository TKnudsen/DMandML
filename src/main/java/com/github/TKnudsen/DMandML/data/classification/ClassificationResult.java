package com.github.TKnudsen.DMandML.data.classification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: ClassificationResult
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
public class ClassificationResult<X> implements IClassificationResult<X> {

	private final Map<X, String> labels;

	Map<String, List<X>> classDistributions;

	private final Map<X, LabelDistribution> labelDistributionMap;

	public ClassificationResult(List<X> featureVectors, List<String> labels) {
		this(ClassificationResults.createDefaultLabelDistributionMap(zip(featureVectors, labels)));
	}

	// @Deprecated
	// public ClassificationResult(Map<X, String> labelsMap) {
	// this(ClassificationResults.createDefaultLabelDistributionMap(labelsMap));
	// this.labels = Collections.unmodifiableMap(labelsMap);
	//
	// this.labelDistributionMap =
	// ClassificationResults.createDefaultLabelDistributionMap(labelsMap);
	// }

	// /**
	// * constructor stores a reference on the object.
	// *
	// * @param labelDistributionMap
	// */
	// public ClassificationResult(Map<X, Map<String, Double>> labelDistributionMap)
	// {
	// this.labels =
	// Collections.unmodifiableMap(ClassificationResults.createLabelsMap(labelDistributionMap));
	//
	// this.labelDistributionMap =
	// ClassificationResults.createLabelDistributionMap(labelDistributionMap);
	// }

	/**
	 * constructor stores a reference on the object.
	 * 
	 * @param labelDistributionMap
	 */
	public ClassificationResult(Map<X, LabelDistribution> labelDistributionMap) {
		this.labelDistributionMap = Collections.unmodifiableMap(labelDistributionMap);

		this.labels = ClassificationResults.createwinningLabelsMap(labelDistributionMap);
	}

	@Override
	public Collection<X> getFeatureVectors() {
		return labels.keySet();
	}

	@Override
	public String getClass(X featureVector) {
		return labels.get(featureVector);
	}

	protected static <X, Y> Map<X, Y> zip(List<X> xs, List<Y> ys) {
		Map<X, Y> map = new LinkedHashMap<X, Y>();

		for (int i = 0; i < xs.size(); i++) {
			map.put(xs.get(i), ys.get(i));
		}

		return map;
	}

	@Override
	public Map<String, List<X>> getClassDistributions() {
		if (classDistributions == null)
			calculateClassDistributions();

		return classDistributions;
	}

	@Override
	public LabelDistribution getLabelDistribution(X x) {
		return labelDistributionMap.get(x);
	}

	private void calculateClassDistributions() {
		classDistributions = new LinkedHashMap<>();

		for (X x : labels.keySet()) {
			if (classDistributions.get(labels.get(x)) == null)
				classDistributions.put(labels.get(x), new ArrayList<>());

			classDistributions.get(labels.get(x)).add(x);
		}
	}

}
