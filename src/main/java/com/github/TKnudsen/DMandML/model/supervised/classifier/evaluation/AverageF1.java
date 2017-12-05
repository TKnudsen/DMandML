package com.github.TKnudsen.DMandML.model.supervised.classifier.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.model.supervised.classifier.IClassifier;

/**
 * <p>
 * Title: AverageF1
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author Christian Ritter
 * @version 1.01
 */
public class AverageF1 implements IClassifierEvaluation<Double, NumericalFeatureVector, String> {

	@Override
	public double getQuality(IClassifier<Double, NumericalFeatureVector> model, List<NumericalFeatureVector> testData, String targetVariable) {
		Set<String> labels = testData.stream().map(x -> x.getAttribute(targetVariable).toString()).collect(Collectors.toSet());
		Map<String, Double> truepositives = new HashMap<>();
		Map<String, Double> positives = new HashMap<>();
		Map<String, Double> trues = new HashMap<>();
		for (String l : labels) {
			truepositives.put(l, 0.0);
			positives.put(l, 0.0);
			trues.put(l, 0.0);
		}

		List<String> test = model.test(testData);
		if (test == null || test.size() == 0)
			return Double.NaN;

		if (test.size() != testData.size())
			throw new IllegalArgumentException("input size != output size");

		for (int i = 0; i < testData.size(); i++) {
			if (testData.get(i) != null && testData.get(i).getAttribute(targetVariable) != null) {
				String trueLabel = testData.get(i).getAttribute(targetVariable).toString();
				String assignedLabel = test.get(i);
				if (trueLabel.equals(assignedLabel))
					truepositives.put(assignedLabel, truepositives.get(assignedLabel) + 1);
				positives.put(assignedLabel, positives.get(assignedLabel) + 1);
				trues.put(trueLabel, trues.get(trueLabel) + 1);
			}
		}

		List<Double> f1Values = new ArrayList<>();
		for (String s : truepositives.keySet()) {
			double precision = 0.0;
			if (positives.get(s) != 0)
				precision = truepositives.get(s) / positives.get(s);
			double recall = 0.0;
			if (trues.get(s) != 0)
				recall = truepositives.get(s) / trues.get(s);
			if (precision + recall != 0.0)
				f1Values.add(2 * precision * recall / (precision + recall));

		}

		if (f1Values.size() < 1)
			return 0.0;
		else
			return f1Values.stream().reduce(0.0, (x, y) -> x + y).doubleValue() / f1Values.size();

	}
}