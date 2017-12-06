package com.github.TKnudsen.DMandML.model.supervised.classifier.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.tools.StatisticsSupport;
import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.supervised.classifier.IClassifier;

/**
 * <p>
 * Title: AverageAccuracyPerClass
 * </p>
 * 
 * <p>
 * Description: Accuracy measure for a series of test instances. Accuracy is
 * defined by the number of objects predicted correctly divided by the number of
 * all tested objects.
 * 
 * This measures calculates one accuracy score per class and then returns the
 * mean accuracy value. Thus: it is applicable for unbalanced label distribution.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class AverageAccuracyPerClass implements IClassifierEvaluation<Double, NumericalFeatureVector, String> {

	@Override
	public double getQuality(IClassifier<Double, NumericalFeatureVector> model, List<NumericalFeatureVector> testData, String targetVariable) {

		List<String> test = model.test(testData);
		if (test == null || test.size() == 0)
			return Double.NaN;

		if (test.size() != testData.size())
			throw new IllegalArgumentException("input size != output size");

		IClassificationResult<NumericalFeatureVector> classificationResult = model.createClassificationResult(testData);
		Map<String, List<NumericalFeatureVector>> classDistributions = classificationResult.getClassDistributions();

		List<Double> accuracies = new ArrayList<>();

		for (String classLabel : classDistributions.keySet()) {
			List<NumericalFeatureVector> instances = classDistributions.get(classLabel);
			if (instances.size() == 0)
				continue;

			double count = 0;
			double correct = 0;

			for (int i = 0; i < instances.size(); i++) {
				NumericalFeatureVector fv = instances.get(i);
				if (fv != null && fv.getAttribute(targetVariable) != null) {
					String label = fv.getAttribute(targetVariable).toString();
					if (label.equals(classLabel))
						correct++;
					count++;
				}
			}

			double accuracy = correct / count;
			if (!Double.isNaN(accuracy))
				accuracies.add(accuracy);
		}

		return new StatisticsSupport(accuracies).getMean();
	}

	@Override
	public String getName() {
		return "AccuracyPerClass";
	}

	@Override
	public String getDescription() {
		return "assesses the average accuracy of all classes";
	}

}
