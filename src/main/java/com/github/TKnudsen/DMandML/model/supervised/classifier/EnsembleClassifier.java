package com.github.TKnudsen.DMandML.model.supervised.classifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.TKnudsen.ComplexDataObject.data.probability.ProbabilityDistribution;
import com.github.TKnudsen.ComplexDataObject.model.tools.MathFunctions;
import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResult;
import com.github.TKnudsen.DMandML.data.classification.LabelDistribution;
import com.github.TKnudsen.DMandML.data.classification.LabelDistributionTools;
import com.github.TKnudsen.DMandML.data.classification.ProbabilisticClassificationResult;

public class EnsembleClassifier<FV> implements IProbabilisticClassifier<FV> {

	private final List<IProbabilisticClassifier<FV>> classifiers;
	private final String classAttribute;

	public EnsembleClassifier(Collection<? extends IProbabilisticClassifier<FV>> classifiers) {
		this.classAttribute = extractClassAttribute(classifiers);
		this.classifiers = new ArrayList<IProbabilisticClassifier<FV>>(classifiers);
	}

	private static String extractClassAttribute(Iterable<? extends IClassifier<?>> classifiers) {
		Set<String> classAttributes = new HashSet<String>();
		for (IClassifier<?> classifier : classifiers) {
			classAttributes.add(classifier.getClassAttribute());
		}
		if (classAttributes.size() != 1) {
			throw new IllegalArgumentException("The classifiers have different class attributes: " + classAttributes);
		}
		return classAttributes.iterator().next();
	}

	@Override
	public String getClassAttribute() {
		return classAttribute;
	}

	@Override
	public void train(List<FV> featureVectors) {

		// TODO Implementations do this. Consider it as part of the contract.
		if (featureVectors.size() < 2) {
			throw new IllegalArgumentException("at least two training instances required");
		}

		for (IProbabilisticClassifier<FV> classifier : classifiers) {
			try {
				classifier.train(featureVectors);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<String> test(List<FV> featureVectors) {
		List<String> labels = new ArrayList<String>();
		for (FV fv : featureVectors) {
			Map<String, Double> labelDistribution = getLabelDistribution(fv);
			Entry<String, Double> entryWithHighestProbability = Collections.max(labelDistribution.entrySet(),
					Map.Entry.comparingByValue());
			String label = entryWithHighestProbability.getKey();
			labels.add(label);
		}
		return labels;
	}

	@Override
	public Map<String, Double> getLabelDistribution(FV featureVector) {

		Map<String, List<Double>> labelDistributions = new HashMap<>();

		for (IProbabilisticClassifier<FV> classifier : classifiers) {
			Map<String, Double> labelDistribution = classifier.getLabelDistribution(featureVector);

			if (labelDistribution == null) {
				System.err.println(
						getName() + ": label distribution was null. Ignoring information for the ensemble result...");
				continue;
			}

			if (!ProbabilityDistribution.checkProbabilitySumMatchesHundredPercent(labelDistribution.values(),
					ProbabilityDistribution.EPSILON, true)) {
				System.err.println(getName() + ": sum of given label probabilites (" + classifier.getName()
						+ " classifier) was != 100% (" + MathFunctions.getSum(labelDistribution.values(), true)
						+ "). Ignoring information for the ensemble result...");
				continue;
			}

			for (String label : labelDistribution.keySet()) {
				List<Double> values = labelDistributions.computeIfAbsent(label, l -> new ArrayList<>());
				values.add(labelDistribution.get(label));
			}
		}

		Map<String, Double> labelDistribution = new LinkedHashMap<>();

		List<String> labelAlphabet = getLabelAlphabet();
		for (String label : labelAlphabet) {
			List<Double> values = labelDistributions.get(label);
			double probability = MathFunctions.getMean(values);
			labelDistribution.put(label, probability);
		}

		return labelDistribution;
	}

	@Override
	public IProbabilisticClassificationResult<FV> createClassificationResult(List<? extends FV> featureVectors) {

		Map<FV, Collection<LabelDistribution>> labelDistributions = new LinkedHashMap<>();

		for (IProbabilisticClassifier<FV> classifier : classifiers) {
			IProbabilisticClassificationResult<FV> classificationResult = classifier
					.createClassificationResult(featureVectors);

			if (classificationResult != null)
				for (FV fv : featureVectors) {
					Collection<LabelDistribution> collection = labelDistributions.computeIfAbsent(fv,
							v -> new ArrayList<>());
					collection.add(classificationResult.getLabelDistribution(fv));
				}
			else
				System.err.println(getName() + ": one of the classifier results was null and will be ignored.");
		}

		Map<FV, Map<String, Double>> labelDistributionMap = new LinkedHashMap<>();
		for (Entry<FV, Collection<LabelDistribution>> entry : labelDistributions.entrySet()) {
			FV fv = entry.getKey();
			Collection<LabelDistribution> labelDistribution = entry.getValue();
			LabelDistribution mergedLabelDistribution = LabelDistributionTools
					.mergeLabelDistributions(labelDistribution);
			labelDistributionMap.put(fv, mergedLabelDistribution.getValueDistribution());
		}
		return new ProbabilisticClassificationResult<>(labelDistributionMap);
	}

	@Override
	public List<String> getLabelAlphabet() {
		Set<String> labelAlphabet = new LinkedHashSet<String>();
		for (IProbabilisticClassifier<FV> classifier : classifiers) {
			labelAlphabet.addAll(classifier.getLabelAlphabet());
		}
		return new ArrayList<String>(labelAlphabet);
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String getDescription() {
		return this.getClass().getCanonicalName();
	}

	@Override
	public String toString() {
		return getName();
	}

}
