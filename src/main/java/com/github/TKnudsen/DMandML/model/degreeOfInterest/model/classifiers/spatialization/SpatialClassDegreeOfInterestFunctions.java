package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization;

import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.characteristics.ClassBordersInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.characteristics.ClassesMarginSpatializationInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.characteristics.ClassesSizeDeviationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.characteristics.ClassesSmallesMarginSpatializationInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.compactness.ClassCentroidProximityInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.compactness.ClassDunnIndexCompactnessDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.compactness.ClassSilhuetteCompactnessDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.compactness.ClassSpecificBalanceCompactnessDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.separation.ClassCentroidsDistancesInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.separation.ClassDunnIndexSeparationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.separation.ClassSilhuetteSeparationDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.separation.ClassesSeparationBasedInterestingnessMeasure;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.probabilities.ClassProbabilitiesDistanceInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.probabilities.ClassProbabilitiesJensenShannonInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.probabilities.ClassProbabilitiesKolmogorovSmirnovInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.probabilities.ClassProbabilitiesKullbackLeiblerInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes.ClassVotesCardinalityInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes.ClassVotesEntropyInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes.ClassVotesSimpsonsInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes.ClassVotesWinnerConfidenceInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.IClassifier;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.IOutlierAnalysisAlgorithm;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.impl.KNNOutlierAnalysis;

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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassCentroidsDistancesInterestingnessFunction(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassCentroidProximityInterestingnessFunction(
				classifier::createClassificationResult, classifier.getName());
		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassBordersInterestingnessFunction(
			IClassifier<NumericalFeatureVector> classifier, int kNN) {

		IOutlierAnalysisAlgorithm<NumericalFeatureVector> outlierAnalysisAlgorithm = new KNNOutlierAnalysis(kNN);

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassBordersInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, outlierAnalysisAlgorithm, classifier.getName());

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
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassesMarginSpatialization(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassesMarginSpatializationInterestingnessFunction(
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
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassesSmallestMarginSpatialization(
			IClassifier<NumericalFeatureVector> classifier) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassesSmallesMarginSpatializationInterestingnessFunction(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassesSeparationBasedInterestingnessMeasure(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassDunnIndexCompactnessDegreeOfInterestingnessFunction<NumericalFeatureVector>(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassSilhuetteCompactnessDegreeOfInterestingnessFunction<NumericalFeatureVector>(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassesSizeDeviationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassDunnIndexSeparationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassSilhuetteSeparationDegreeOfInterestingnessFunction<NumericalFeatureVector>(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassProbabilitiesDistanceInterestingnessFunction<NumericalFeatureVector>(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassProbabilitiesJensenShannonInterestingnessFunction<NumericalFeatureVector>(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassProbabilitiesKolmogorovSmirnovInterestingnessFunction<NumericalFeatureVector>(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassProbabilitiesKullbackLeiblerInterestingnessFunction<NumericalFeatureVector>(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassVotesEntropyInterestingnessFunction<NumericalFeatureVector>(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassVotesSimpsonsInterestingnessFunction<NumericalFeatureVector>(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassVotesWinnerConfidenceInterestingnessFunction<NumericalFeatureVector>(
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

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassVotesCardinalityInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, kNN, distanceMeasure, classifier.getName());
		return degreeOfInterestFunction;
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> createSpatialClassSpecificBalance(
			IClassifier<NumericalFeatureVector> classifier, Map<String, Double> targetValues) {

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassSpecificBalanceCompactnessDegreeOfInterestingnessFunction<NumericalFeatureVector>(
				classifier::createClassificationResult, classifier.getName(), targetValues);
		return degreeOfInterestFunction;
	}
}
