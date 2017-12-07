package com.github.TKnudsen.DMandML.model.supervised.classifier.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.tools.StatisticsSupport;
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
 * mean accuracy value. Thus: it is applicable for unbalanced label
 * distribution.
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

		Set<String> labelAlphabet = testData.stream().map(x -> x.getAttribute(targetVariable).toString()).collect(Collectors.toSet());
		Map<String, List<Integer>> result = new HashMap<>();
		for (String l : labelAlphabet)
			result.put(l, new ArrayList<>());

		for (int i = 0; i < testData.size(); i++) {
			NumericalFeatureVector fv = testData.get(i);
			String predictedLabel = test.get(i);
			String trueLabel = fv.getAttribute(targetVariable).toString();
			if (predictedLabel.equals(trueLabel))
				result.get(trueLabel).add(1);
			else
				result.get(trueLabel).add(0);
		}

		List<Double> accuracies = new ArrayList<>();

		for (String classLabel : result.keySet()) {
			double accuracy = 0;
			if (result.get(classLabel).size() != 0) {
				Double sum = result.get(classLabel).stream().reduce(0, (x, y) -> x + y).doubleValue();
				accuracy = sum / (double) result.get(classLabel).size();
			}

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
