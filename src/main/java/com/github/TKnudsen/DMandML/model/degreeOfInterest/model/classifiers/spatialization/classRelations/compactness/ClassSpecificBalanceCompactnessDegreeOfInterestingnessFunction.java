package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.compactness;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.ClassBasedDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Assigns an interestingness score based on the count of instances in each
 * class. All elements in one class will be assigned the same score. Both,
 * higher and lower than given percentages, will result in a high score.
 * </p>
 * 
 * Implementation of the Compactness Estimation (CE) DOI/building block published in: Juergen Bernard,
 * Matthias Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair:
 * Towards User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.03
 */
public class ClassSpecificBalanceCompactnessDegreeOfInterestingnessFunction<FV>
		extends ClassBasedDegreeOfInterestingnessFunction<FV> {

	private Map<String, Double> interestingnessMapping;
	private Map<String, Double> targetValues;

	public ClassSpecificBalanceCompactnessDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction,
			Map<String, Double> targetValues) {
		this(classificationApplicationFunction, null, targetValues);
	}

	public ClassSpecificBalanceCompactnessDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction, String classifierName,
			Map<String, Double> targetValues) {
		super(classificationApplicationFunction, classifierName);
		this.targetValues = targetValues;
		if (targetValues == null) {
			throw new IllegalArgumentException(
					"ClassSpecificBalanceCompactnessDegreeOfInterestingnessFunction: target values must not be null.");
		}
	}

	private void buildInterestingnessMapping(IClassificationResult<FV> result) {
		interestingnessMapping = new LinkedHashMap<>();
		Map<String, List<FV>> classDistributions = result.getClassDistributions();
		// count elements per cluster and find min and max size
		for (String label : classDistributions.keySet()) {
			double d = 1.0 * classDistributions.get(label).size();
			interestingnessMapping.put(label, d);
		}
		int size = result.getFeatureVectors().size();
		// substract target and make all values positive
		for (String label : classDistributions.keySet()) {
			if (targetValues.get(label) == null)
				throw new IllegalArgumentException(
						"ClassSpecificBalanceCompactnessDegreeOfInterestingnessFunction: target value has to be given for every class.");
			interestingnessMapping.put(label,
					Math.abs(interestingnessMapping.get(label) / size - targetValues.get(label)));
		}
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv, IClassificationResult<FV> result) {
		if (interestingnessMapping == null)
			buildInterestingnessMapping(result);
		String label = result.getClass(fv);
		if (label == null)
			throw new IllegalArgumentException(
					"ClassSpecificBalanceCompactnessDegreeOfInterestingnessFunction: Instance has to have a label.");
		return interestingnessMapping.get(label);
	}

	@Override
	public String getName() {
		return "Class Specific Balance Compactness [" + getClassifierName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the amount of instances in a class compared to a given target amount.";
	}
}