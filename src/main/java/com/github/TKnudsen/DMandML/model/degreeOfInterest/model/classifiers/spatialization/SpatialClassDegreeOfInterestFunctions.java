package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization;

import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.characteristics.ClassesSizeDeviationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.characteristics.SpatialClassesMarginInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.compactness.ClassDunnIndexCompactnessDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.compactness.ClassSilhuetteCompactnessDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.compactness.ClassSpecificBalanceCompactnessDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.compactness.SpatialClassCentroidSimilarityInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.separation.ClassDunnIndexSeparationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.separation.ClassSilhuetteSeparationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.separation.SpatialClassCentroidsDistancesInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.separation.SpatialClassesSeparationBasedInterestingnessMeasure;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.probabilities.SpatialClassProbabilitiesDistanceInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.probabilities.SpatialClassProbabilitiesJensenShannonInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.probabilities.SpatialClassProbabilitiesKolmogorovSmirnovInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.probabilities.SpatialClassProbabilitiesKullbackLeiblerInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes.SpatialClassVotesCardinalityInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes.SpatialClassVotesEntropyInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes.SpatialClassVotesSimpsonsInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes.SpatialClassVotesWinnerConfidenceInterestingnessFunction;
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
 * Creates instances of different {@link IDegreeOfInterestFunction} using local
 * spatial characteristics (neighborhoods) in combination with (probabilistic)
 * predictions of a classifier.
 * 
 * Spatial class DOIs require a list of
 * {@link IClassificationApplicationFunction}.
 * 
 * @author Juergen Bernard
 * 
 * @version 1.01
 */
public class SpatialClassDegreeOfInterestFunctions {

	// CLASS CENTROIDS BASED DOIS

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
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassCentroidsDistances(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new SpatialClassCentroidsDistancesInterestingnessFunction(
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
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassCentroidsWinnerSimilarity(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new SpatialClassCentroidSimilarityInterestingnessFunction(
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
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassMargin(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new SpatialClassesMarginInterestingnessFunction(
				classifier::createClassificationResult, classifier.getName());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassSeparation(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new SpatialClassesSeparationBasedInterestingnessMeasure(
				classifier::createClassificationResult, classifier.getName());
		return degreeOfInterestFunction;
	}

	// CLASS BASED DOIs

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexClassCompactness(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassDunnIndexCompactnessDegreeOfInterestingnessFunction<>(
				classifier::createClassificationResult, classifier.getName(), new EuclideanDistanceMeasure());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteClassCompactness(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassSilhuetteCompactnessDegreeOfInterestingnessFunction<>(
				classifier::createClassificationResult, classifier.getName(), new EuclideanDistanceMeasure());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassesCountCompactness(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassesSizeDeviationDegreeOfInterestingnessFunction<>(
				classifier::createClassificationResult, classifier.getName());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createDunnIndexClassSeparation(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassDunnIndexSeparationDegreeOfInterestingnessFunction<>(
				classifier::createClassificationResult, classifier.getName(), new EuclideanDistanceMeasure());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSilhuetteClassSeparation(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassSilhuetteSeparationDegreeOfInterestingnessFunction<>(
				classifier::createClassificationResult, classifier.getName(), new EuclideanDistanceMeasure());
		return degreeOfInterestFunction;
	}

	// LOCAL NEIGHBORHOOD RELATIONS

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassProbabilitiesDistance(
			IClassifier<NumericalFeatureVector> classifier, int kNN,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new SpatialClassProbabilitiesDistanceInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, kNN, distanceMeasure, classifier.getName());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassProbabilitiesJensenShannon(
			IClassifier<NumericalFeatureVector> classifier, int kNN,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new SpatialClassProbabilitiesJensenShannonInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, kNN, distanceMeasure, classifier.getName());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassProbabilitiesKolmogorovSmirnov(
			IClassifier<NumericalFeatureVector> classifier, int kNN,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new SpatialClassProbabilitiesKolmogorovSmirnovInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, kNN, distanceMeasure, classifier.getName());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassProbabilitiesKullbackLeibler(
			IClassifier<NumericalFeatureVector> classifier, int kNN,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new SpatialClassProbabilitiesKullbackLeiblerInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, kNN, distanceMeasure, classifier.getName());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassVotesEntropy(
			IClassifier<NumericalFeatureVector> classifier, int kNN,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new SpatialClassVotesEntropyInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, kNN, distanceMeasure, classifier.getName());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassVotesSimpsonsDiversity(
			IClassifier<NumericalFeatureVector> classifier, int kNN,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new SpatialClassVotesSimpsonsInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, kNN, distanceMeasure, classifier.getName());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassVotesWinnerConformity(
			IClassifier<NumericalFeatureVector> classifier, int kNN,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new SpatialClassVotesWinnerConfidenceInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, kNN, distanceMeasure, classifier.getName());
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular spatial class
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given {@link IClassifier} is used to instantiate the DOI with a
	 * {@link IClassificationApplicationFunction}. To assess spatial proximity a kNN
	 * retrieval algorithm is applied that needs the parameters kNN and a distance
	 * measure.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassVotesCardinality(
			IClassifier<NumericalFeatureVector> classifier, int kNN,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new SpatialClassVotesCardinalityInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, kNN, distanceMeasure, classifier.getName());
		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassSpecificBalance(
			IClassifier<NumericalFeatureVector> classifier, Map<String, Double> targetValues) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassSpecificBalanceCompactnessDegreeOfInterestingnessFunction<>(
				classifier::createClassificationResult, classifier.getName(), targetValues);
		return degreeOfInterestFunction;
	}
}
