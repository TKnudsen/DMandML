package com.github.TKnudsen.DMandML.model.supervised.classifier.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.IKeyValueProvider;
import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.supervised.classifier.IClassifier;
import com.github.TKnudsen.DMandML.model.supervised.classifier.impl.numericalFeatures.BayesNet;
import com.github.TKnudsen.DMandML.model.supervised.classifier.impl.numericalFeatures.NaiveBayesMultinomial;
import com.github.TKnudsen.DMandML.model.supervised.classifier.impl.numericalFeatures.RandomForest;

/**
 * Some basic tests for the classifiers, namely for {@link IClassifier}.
 * 
 * TODO: This is supposed to become a set of unit tests, maybe, one day, by
 * replacing the passed lines with assertions.
 */
public class ClassifierTests {

	public static void main(String[] args) {

		testAll(RandomForest.class);
		testAll(BayesNet.class);
		testAll(NaiveBayesMultinomial.class);
		// testAll(GaussianProcesses.class);
		// testAll(SMOSVN.class);
		// testAll(SVMLinearClassifier.class);
		// testAll(SVMPolynomialClassifier.class);

		// testUntrainedClassifier(RandomForest.class);
	}

	/**
	 * Execute all tests
	 * 
	 * @param classifierClass The classifier class
	 * @return Whether the test passed
	 */
	private static boolean testAll(Class<? extends IClassifier<NumericalFeatureVector>> classifierClass) {
		boolean passed = true;
		passed &= testUntrainedClassifier(classifierClass);
		passed &= testTrainingUpdatesLabelAlphabet(classifierClass);
		passed &= testTrainingWithSingleClassDoesNotMessUpThings(classifierClass);
		passed &= testCaseWhereEverythingShouldWork(classifierClass);
		passed &= testTrainingResetsClassifier(classifierClass);
		System.out.println("All tests passed? " + passed);
		return passed;
	}

	/**
	 * Test that an untrained classifier returns sensible data
	 * 
	 * @param classifierClass The classifier class
	 * @return Whether the test passed
	 */
	private static boolean testUntrainedClassifier(
			Class<? extends IClassifier<NumericalFeatureVector>> classifierClass) {
		System.out.println("Testing untrained classifier for " + classifierClass);

		IClassifier<NumericalFeatureVector> classifier = newInstanceUnchecked(classifierClass);

		String classAttribute = classifier.getClassAttribute();
		int numClasses = 5;
		List<NumericalFeatureVector> testingVectors = ClassifierTestUtils.createDefaultTestingVectors(classAttribute,
				numClasses);

		Map<String, Double> labelDistribution = classifier.getLabelDistribution(testingVectors.get(0));
		// System.out.println("labelDistribution: "+labelDistribution);

		List<String> winningLabels = classifier.test(testingVectors);
		// System.out.println("winningLabels: "+winningLabels);

		IClassificationResult<NumericalFeatureVector> classificationResult = classifier
				.createClassificationResult(testingVectors);
		// System.out.println("classificationResult: "+classificationResult);

		boolean passed = true;
		passed &= Collections.emptyList().equals(classifier.getLabelAlphabet());
		passed &= Collections.emptyMap().equals(labelDistribution);
		passed &= Collections.nCopies(testingVectors.size(), null).equals(winningLabels);
//		passed &= containSameElementsDisregardingOrder(classificationResult.getFeatureVectors(), testingVectors);
		passed &= Collections.emptyMap()
				.equals(classificationResult.getLabelDistribution(testingVectors.get(0)).getProbabilityDistribution());

		System.out.println("Testing untrained classifier passed? " + passed);

		return passed;
	}

	/**
	 * Test that the getLabelAlphabet method returns an empty list before the
	 * training, and the proper label alphabet afterwards
	 * 
	 * @param classifierClass The classifier class
	 * @return Whether the test passed
	 */
	private static boolean testTrainingUpdatesLabelAlphabet(
			Class<? extends IClassifier<NumericalFeatureVector>> classifierClass) {
		System.out.println("Testing getLabelAlphabet before and after training for " + classifierClass);

		IClassifier<NumericalFeatureVector> classifier = newInstanceUnchecked(classifierClass);

		List<String> labelAlphabetBefore = new ArrayList<String>(classifier.getLabelAlphabet());

		String classAttribute = classifier.getClassAttribute();
		int numClasses = 5;
		List<NumericalFeatureVector> trainingVectors = ClassifierTestUtils.createDefaultTrainingVectors(classAttribute,
				numClasses);
		classifier.train(trainingVectors);

		List<String> labelAlphabetAfter = new ArrayList<String>(classifier.getLabelAlphabet());
		Set<Object> expectedLabelAlphabetAfter = new LinkedHashSet<Object>(
				extractAttributeValues(trainingVectors, classAttribute));

		// System.out.println("labelAlphabetAfter : "+labelAlphabetAfter);
		// System.out.println("expectedLabelAlphabetAfter:
		// "+expectedLabelAlphabetAfter);

		boolean passed = true;
		passed &= labelAlphabetBefore.equals(Collections.emptyList());
//		passed &= containSameElementsDisregardingOrder(labelAlphabetAfter, expectedLabelAlphabetAfter);

		System.out.println("Testing getLabelAlphabet before and after training passed? " + passed);

		return passed;
	}

	/**
	 * Test that training the classifier with training vectors that only cover a
	 * single class will not mess up things
	 * 
	 * @param classifierClass The classifier class
	 * @return Whether the test passed
	 */
	private static boolean testTrainingWithSingleClassDoesNotMessUpThings(
			Class<? extends IClassifier<NumericalFeatureVector>> classifierClass) {
		System.out.println("Testing training with single class for " + classifierClass);

		IClassifier<NumericalFeatureVector> classifier = newInstanceUnchecked(classifierClass);

		String classAttribute = classifier.getClassAttribute();
		int numClasses = 1;
		List<NumericalFeatureVector> trainingVectors = ClassifierTestUtils.createDefaultTrainingVectors(classAttribute,
				numClasses);

		String classValue = String.valueOf(extractAttributeValues(trainingVectors, classAttribute).get(0));

		classifier.train(trainingVectors);

		List<NumericalFeatureVector> testingVectors = ClassifierTestUtils.createDefaultTestingVectors(classAttribute,
				numClasses);

		Map<String, Double> labelDistribution = classifier.getLabelDistribution(testingVectors.get(0));
		// System.out.println("labelDistribution: "+labelDistribution);

		List<String> winningLabels = classifier.test(testingVectors);
		// System.out.println("winningLabels: "+winningLabels);

		IClassificationResult<NumericalFeatureVector> classificationResult = classifier
				.createClassificationResult(testingVectors);
		// System.out.println("classificationResult: "+classificationResult);

		boolean passed = true;
		passed &= Collections.singletonMap(classValue, 1.0).equals(labelDistribution);
		passed &= Collections.nCopies(testingVectors.size(), classValue).equals(winningLabels);
		passed &= classificationResult.getClass(testingVectors.get(0)).equals(classValue);
//		passed &= containSameElementsDisregardingOrder(classificationResult.getClassDistributions().get(classValue),
//				testingVectors);
		passed &= Collections.singletonMap(classValue, 1.0)
				.equals(classificationResult.getLabelDistribution(testingVectors.get(0)).getProbabilityDistribution());
		passed &= (null == classificationResult.getLabelDistribution(trainingVectors.get(0)));

		System.out.println("Testing training with single class passed? " + passed);

		return passed;
	}

	/**
	 * A general test of the default usage of the classifier
	 * 
	 * @param classifierClass The classifier class
	 * @return Whether the test passed
	 */
	private static boolean testCaseWhereEverythingShouldWork(
			Class<? extends IClassifier<NumericalFeatureVector>> classifierClass) {
		System.out.println("Testing case where everything should work for " + classifierClass);

		IClassifier<NumericalFeatureVector> classifier = newInstanceUnchecked(classifierClass);

		String classAttribute = classifier.getClassAttribute();
		int numClasses = 5;
		List<NumericalFeatureVector> trainingVectors = ClassifierTestUtils.createDefaultTrainingVectors(classAttribute,
				numClasses);
		classifier.train(trainingVectors);

		List<NumericalFeatureVector> testingVectors = ClassifierTestUtils.createDefaultTestingVectors(classAttribute,
				numClasses);

		printTestingResults(classifier, testingVectors);

		return true;
	}

	/**
	 * Test whether performing a training and then performing a training with an
	 * empty set "resets" the classifier so that it behaves as if it was never
	 * trained at all
	 * 
	 * @param classifierClass The classifier class
	 * @return Whether the test passed
	 */
	private static boolean testTrainingResetsClassifier(
			Class<? extends IClassifier<NumericalFeatureVector>> classifierClass) {
		System.out.println("Testing whether an empty training resets a classifier for " + classifierClass);

		IClassifier<NumericalFeatureVector> classifier = newInstanceUnchecked(classifierClass);

		String classAttribute = classifier.getClassAttribute();
		int numClasses = 5;
		List<NumericalFeatureVector> trainingVectors = ClassifierTestUtils.createDefaultTrainingVectors(classAttribute,
				numClasses);
		classifier.train(trainingVectors);

		List<NumericalFeatureVector> testingVectors = ClassifierTestUtils.createDefaultTestingVectors(classAttribute,
				numClasses);

		// Perform a training with an empty list, expecting the same
		// behavior as if no training was performed at all
		classifier.train(Collections.emptyList());

		Map<String, Double> labelDistribution = classifier.getLabelDistribution(testingVectors.get(0));
		// System.out.println("labelDistribution: "+labelDistribution);

		List<String> winningLabels = classifier.test(testingVectors);
		// System.out.println("winningLabels: "+winningLabels);

		IClassificationResult<NumericalFeatureVector> classificationResult = classifier
				.createClassificationResult(testingVectors);
		// System.out.println("classificationResult: "+classificationResult);

		boolean passed = true;
		passed &= Collections.emptyList().equals(classifier.getLabelAlphabet());
		passed &= Collections.emptyMap().equals(labelDistribution);
		passed &= Collections.nCopies(testingVectors.size(), null).equals(winningLabels);
//		passed &= containSameElementsDisregardingOrder(classificationResult.getFeatureVectors(), testingVectors);
		passed &= Collections.emptyMap()
				.equals(classificationResult.getLabelDistribution(testingVectors.get(0)).getProbabilityDistribution());

		return passed;
	}

	static <T extends IKeyValueProvider<Object>> void printTestingResults(IClassifier<T> classifier,
			List<T> testingVectors) {
		List<String> winningLabels = classifier.test(testingVectors);
		for (int i = 0; i < testingVectors.size(); i++) {
			T testingVector = testingVectors.get(i);
			System.out.println("Vector " + i + " is " + testingVector);

			Map<String, Double> labelDistribution = classifier.getLabelDistribution(testingVector);
			System.out.println("  Label Distribution: " + labelDistribution);

			String winningLabel = winningLabels.get(i);
			System.out.println("  Winning label: " + winningLabel);
		}

		IClassificationResult<T> classificationResult = classifier.createClassificationResult(testingVectors);
		printClassificationResult(classificationResult);
	}

	private static <T> void printClassificationResult(IClassificationResult<T> classificationResult) {
		System.out.println("ClassificationResult:");

		Map<String, List<T>> classDistributions = classificationResult.getClassDistributions();
		System.out.println("  Class distributions: ");
		for (Entry<String, List<T>> entry : classDistributions.entrySet()) {
			System.out.println("    class " + entry.getKey() + ": " + entry.getValue());
		}

		Collection<T> featureVectors = classificationResult.getFeatureVectors();
		for (T featureVector : featureVectors) {
			System.out.println("  FeatureVector " + featureVector);
			System.out.println("    class            : " + classificationResult.getClass(featureVector));
			System.out.println("    labelDistribution: " + classificationResult.getLabelDistribution(featureVector));
		}

	}

	private static <T> T newInstanceUnchecked(Class<? extends T> c) {
		try {
			return c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private static List<Object> extractAttributeValues(List<? extends IKeyValueProvider<?>> keyValueProviders,
			String attributeName) {
		List<Object> attributeValues = new ArrayList<Object>();
		for (IKeyValueProvider<?> k : keyValueProviders) {
			Object attributeValue = k.getAttribute(attributeName);
			attributeValues.add(attributeValue);
		}
		return attributeValues;
	}

//	private static boolean containSameElementsDisregardingOrder(Collection<?> c0, Collection<?> c1) {
//		Map<Object, Long> frequenciesA = c0.stream()
//				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//		Map<Object, Long> frequenciesB = c1.stream()
//				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//		return frequenciesA.equals(frequenciesB);
//	}

}
