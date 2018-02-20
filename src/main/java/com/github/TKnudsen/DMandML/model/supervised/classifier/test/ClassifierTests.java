package com.github.TKnudsen.DMandML.model.supervised.classifier.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.IKeyValueProvider;
import com.github.TKnudsen.DMandML.model.supervised.classifier.IProbabilisticClassifier;
import com.github.TKnudsen.DMandML.model.supervised.classifier.numericalFeatures.RandomForest;


/**
 * Some basic tests for the classifiers, namely for
 * {@link IProbabilisticClassifier}
 */
public class ClassifierTests {

	public static void main(String[] args) {

		testAll(RandomForest.class);
	}
	
	/**
	 * Execute all tests
	 * 
	 * @param classifierClass The classifier class
	 * @return Whether the test passed
	 */
	private static boolean testAll(Class<? extends IProbabilisticClassifier<NumericalFeatureVector>> classifierClass)
	{
		boolean passed = true;
		passed &= testUntrainedClassifier(RandomForest.class);
		passed &= testTrainingUpdatesLabelAlphabet(RandomForest.class);
		passed &= testTrainingWithSingleClassDoesNotMessUpThings(RandomForest.class);
		passed &= testCaseWhereEverythingShouldWork(RandomForest.class);
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
			Class<? extends IProbabilisticClassifier<NumericalFeatureVector>> classifierClass) {
		System.out.println("Testing untrained classifier for " + classifierClass);

		IProbabilisticClassifier<NumericalFeatureVector> classifier = newInstanceUnchecked(classifierClass);

		String classAttribute = classifier.getClassAttribute();
		int numClasses = 5;
		List<NumericalFeatureVector> testingVectors = ClassifierTestUtils.createDefaultTestingVectors(classAttribute, numClasses);
		
		Map<String, Double> labelDistribution = classifier.getLabelDistribution(testingVectors.get(0));
		//System.out.println("labelDistribution: "+labelDistribution);
		
		List<String> winningLabels = classifier.test(testingVectors);
		//System.out.println("winningLabels: "+winningLabels);

		boolean passed = true;
		passed &= Collections.emptyMap().equals(labelDistribution);
		passed &= Collections.emptyList().equals(winningLabels);

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
			Class<? extends IProbabilisticClassifier<NumericalFeatureVector>> classifierClass) {
		System.out.println("Testing getLabelAlphabet before and after training for " + classifierClass);

		IProbabilisticClassifier<NumericalFeatureVector> classifier = newInstanceUnchecked(classifierClass);

		List<String> labelAlphabetBefore = new ArrayList<String>(classifier.getLabelAlphabet());

		String classAttribute = classifier.getClassAttribute();
		int numClasses = 5;
		List<NumericalFeatureVector> trainingVectors = ClassifierTestUtils.createDefaultTrainingVectors(classAttribute, numClasses);
		classifier.train(trainingVectors);

		List<String> labelAlphabetAfter = new ArrayList<String>(classifier.getLabelAlphabet());
		Set<Object> expectedLabelAlphabetAfter = new LinkedHashSet<Object>(extractAttributeValues(trainingVectors, classAttribute));
		
		//System.out.println("labelAlphabetAfter        : "+labelAlphabetAfter);
		//System.out.println("expectedLabelAlphabetAfter: "+expectedLabelAlphabetAfter);

		boolean passed = true;
		passed &= labelAlphabetBefore.equals(Collections.emptyList());
		passed &= containSameElementsDisregardingOrder(labelAlphabetAfter, expectedLabelAlphabetAfter);

		System.out.println("Testing getLabelAlphabet before and after training passed? " + passed);

		return passed;
	}
	
	
	/**
	 * Test that training the classifier with training vectors that only cover 
	 * a single class will not mess up things
	 * 
	 * @param classifierClass The classifier class
	 * @return Whether the test passed
	 */
	private static boolean testTrainingWithSingleClassDoesNotMessUpThings(
			Class<? extends IProbabilisticClassifier<NumericalFeatureVector>> classifierClass) {
		System.out.println("Testing training with single class for " + classifierClass);

		IProbabilisticClassifier<NumericalFeatureVector> classifier = newInstanceUnchecked(classifierClass);

		String classAttribute = classifier.getClassAttribute();
		int numClasses = 1;
		List<NumericalFeatureVector> trainingVectors = ClassifierTestUtils.createDefaultTrainingVectors(classAttribute, numClasses);
		classifier.train(trainingVectors);

		List<NumericalFeatureVector> testingVectors = ClassifierTestUtils.createDefaultTestingVectors(classAttribute, numClasses);
		
		Map<String, Double> labelDistribution = classifier.getLabelDistribution(testingVectors.get(0));
		//System.out.println("labelDistribution: "+labelDistribution);
		
		List<String> winningLabels = classifier.test(testingVectors);
		//System.out.println("winningLabels: "+winningLabels);

		boolean passed = true;
		passed &= Collections.emptyMap().equals(labelDistribution);
		passed &= Collections.emptyList().equals(winningLabels);

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
			Class<? extends IProbabilisticClassifier<NumericalFeatureVector>> classifierClass) {
		System.out.println("Testing case where everything should work for " + classifierClass);

		IProbabilisticClassifier<NumericalFeatureVector> classifier = newInstanceUnchecked(classifierClass);

		String classAttribute = classifier.getClassAttribute();
		int numClasses = 5;
		List<NumericalFeatureVector> trainingVectors = ClassifierTestUtils.createDefaultTrainingVectors(classAttribute, numClasses);
		classifier.train(trainingVectors);

		List<NumericalFeatureVector> testingVectors = ClassifierTestUtils.createDefaultTestingVectors(classAttribute, numClasses);
		
		printTestingResults(classifier, testingVectors);
		
		return true;
	}
	
	
	
	
	
	
	

	static <T extends IKeyValueProvider<Object>> void printTestingResults(IProbabilisticClassifier<T> classifier,
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

	private static boolean containSameElementsDisregardingOrder(Collection<?> c0, Collection<?> c1) {
		Map<Object, Long> frequenciesA = c0.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		Map<Object, Long> frequenciesB = c1.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		return frequenciesA.equals(frequenciesB);
	}

}
