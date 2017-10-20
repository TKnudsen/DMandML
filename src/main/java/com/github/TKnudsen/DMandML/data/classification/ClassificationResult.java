package com.github.TKnudsen.DMandML.data.classification;

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
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public class ClassificationResult<X> implements IClassificationResult<X> {

	private final Map<X, String> labels;

	public ClassificationResult(List<X> featureVectors, List<String> labels) {
		this(zip(featureVectors, labels));
	}

	public ClassificationResult(Map<X, String> labelsMap) {
		this.labels = Collections.unmodifiableMap(labelsMap);
	}

	@Override
	public Collection<X> getFeatures() {
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

}
