package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.uncertainty;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.relevance.ClassRelevanceMostSignificantConfidenceInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.IClassifier;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Description: creates instances of different {@link IDegreeOfInterestFunction}
 * using the probability distribitions of classifier predictions. The classifier
 * information is provided with a classification result
 * {@link IClassificationApplicationFunction}, i.e., a function that applies a
 * list of given instances to receive a {@link IClassificationResult}.
 * 
 * @version 1.02
 */
public class ClassUncertaintyDegreeOfInterestFunctions {

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassUncertaintyEntropy(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassUncertaintyEntropyBasedInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, classifier.getName());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassUncertaintyLeastSignificantConfidence(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassUncertaintyLeastSignificantConfidenceInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, classifier.getName());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassUncertaintySmallestMargin(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassUncertaintySmallestMarginBasedInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, classifier.getName());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassUncertaintyMostSignificantConfidence(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassRelevanceMostSignificantConfidenceInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, classifier.getName());
		return degreeOfInterestFunction;
	}
}
