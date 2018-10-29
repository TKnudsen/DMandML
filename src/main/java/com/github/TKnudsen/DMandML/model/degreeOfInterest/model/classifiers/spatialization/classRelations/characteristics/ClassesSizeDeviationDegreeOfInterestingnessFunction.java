package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.characteristics;

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
 * higher and lower than average counts, will result in a high score.
 * </p>
 * 
 * @version 1.02
 */
public class ClassesSizeDeviationDegreeOfInterestingnessFunction<FV>
		extends ClassBasedDegreeOfInterestingnessFunction<FV> {

	Map<String, Double> interestingnessMapping;

	public ClassesSizeDeviationDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction) {
		super(classificationApplicationFunction);
	}

	public ClassesSizeDeviationDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction, String classifierName) {
		super(classificationApplicationFunction, classifierName);
	}

	private void buildInterestingnessMapping(IClassificationResult<FV> result) {
		interestingnessMapping = new LinkedHashMap<>();
		Map<String, List<FV>> classDistributions = result.getClassDistributions();
		// count elements per cluster and find min and max size
		double mean = 0.0;
		for (String label : classDistributions.keySet()) {
			double d = 1.0 * classDistributions.get(label).size();
			interestingnessMapping.put(label, d);
			mean += d;
		}
		// substract mean and make all values positive
		mean /= classDistributions.size();
		for (String label : classDistributions.keySet()) {
			interestingnessMapping.put(label, Math.abs(interestingnessMapping.get(label) - mean));
		}
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv, IClassificationResult<FV> result) {
		if (interestingnessMapping == null)
			buildInterestingnessMapping(result);
		String label = result.getClass(fv);
		if (label == null)
			throw new IllegalArgumentException(
					"ClassesCountCompactnessDegreeOfInterestingnessFunction: Instance has to have a label.");
		return interestingnessMapping.get(label);
	}

	@Override
	public String getName() {
		return "Class Size Deviation [" + getClassifierName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the amount of instances in a class.";
	}
}
