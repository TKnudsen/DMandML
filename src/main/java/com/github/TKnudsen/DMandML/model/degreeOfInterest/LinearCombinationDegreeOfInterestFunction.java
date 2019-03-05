package com.github.TKnudsen.DMandML.model.degreeOfInterest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.github.TKnudsen.ComplexDataObject.model.degreeOfInterest.IDegreeOfInterestFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * A combination of degree of interest functions.
 * 
 */
public class LinearCombinationDegreeOfInterestFunction<FV> implements IDegreeOfInterestFunction<FV> {

	private final List<IDegreeOfInterestFunction<FV>> dois;
	private final List<Double> weights;
	private String name;

	public LinearCombinationDegreeOfInterestFunction(Collection<? extends IDegreeOfInterestFunction<FV>> dois) {
		this.dois = new ArrayList<IDegreeOfInterestFunction<FV>>(dois);
		this.weights = new ArrayList<Double>(Collections.nCopies(dois.size(), 0.0));
		this.name = getClass().getSimpleName();
	}

	public List<IDegreeOfInterestFunction<FV>> getDegreeOfInterestFunctions() {
		return Collections.unmodifiableList(dois);
	}

	public double getWeight(int index) {
		return weights.get(index);
	}

	public void setWeight(int index, double weight) {
		weights.set(index, weight);
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> featureVectors) {
		Objects.requireNonNull(featureVectors);
		Map<FV, Double> interestingnessScores = new LinkedHashMap<>();
		if (featureVectors.isEmpty()) {
			return interestingnessScores;
		}

		// Initialize all values to 0.0
		for (FV fv : featureVectors) {
			interestingnessScores.put(fv, 0.0);
		}

		// Add the values from all DOIs
		for (int i = 0; i < dois.size(); i++) {
			IDegreeOfInterestFunction<FV> doi = dois.get(i);
			double weight = weights.get(i);

			if (weight > 0.0) {
				Map<FV, Double> singleResult = doi.apply(featureVectors);
				for (Entry<FV, Double> entry : singleResult.entrySet()) {
					FV fv = entry.getKey();
					Double singleValue = entry.getValue().isNaN() ? 0.0 : entry.getValue();
					interestingnessScores.compute(fv, (k, v) -> v + singleValue * weight);
				}
			}
		}

		// System.out.println(this+": "+interestingnessScores);

		// Normalize all values by dividing by the number of DOIS
		interestingnessScores = MapUtils.affineTransformValues(interestingnessScores, 1.0 / dois.size(), 0.0);
		return MapUtils.normalizeValuesMinMax(interestingnessScores);
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName() + "[");
		for (int i = 0; i < dois.size(); i++) {
			IDegreeOfInterestFunction<FV> doi = dois.get(i);
			double weight = weights.get(i);
			if (i > 0) {
				sb.append(",");
			}
			sb.append(doi.getName() + "*" + weight);
		}
		sb.append("]");
		return sb.toString();
	}

}
