package com.github.TKnudsen.DMandML.data.classification;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Title: LabelDistribution
 * </p>
 * 
 * <p>
 * Description: data model for distributions of labels.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2015-2017
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 */
public class LabelDistribution {

	private Map<String, Double> valueDistribution;

	private String representant;

	/**
	 * constructor for reflection-based and jackson-based access.
	 */
	@SuppressWarnings("unused")
	private LabelDistribution() {
		this(null, null);
	}

	public LabelDistribution(Map<String, Double> valueDistribution) {
		this(valueDistribution, null);
	}

	public LabelDistribution(Map<String, Double> valueDistribution, String representant) {
		this.valueDistribution = valueDistribution;
		this.representant = representant;

		if (representant == null)
			this.representant = calculateRepresentant();
	}

	private String calculateRepresentant() {
		String rep = null;
		Double repRatio = 0.0;

		if (valueDistribution == null)
			return null;

		for (String value : valueDistribution.keySet())
			if (valueDistribution.get(value) > repRatio) {
				rep = value;
				repRatio = valueDistribution.get(value);
			}

		return rep;
	}

	public String getRepresentant() {
		return representant;
	}

	public Map<String, Double> getValueDistribution() {
		return valueDistribution;
	}

	public Set<String> getLabelSet() {
		return valueDistribution.keySet();
	}
}
