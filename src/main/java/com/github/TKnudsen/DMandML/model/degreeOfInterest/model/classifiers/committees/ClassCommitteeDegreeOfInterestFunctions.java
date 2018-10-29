package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.probabilities.ClassCommitteeJensenShannonDivergenceInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.probabilities.ClassCommitteeKolmogorovSmirnovInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.probabilities.ClassCommitteeKullbackLeiblerInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.probabilities.ClassCommitteeProbabilityDistanceInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.votes.ClassCommitteeVoteCardinality;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.votes.ClassCommitteeVoteDiversityInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.committees.votes.ClassCommitteeVoteEntropyInterestingnessFunction;
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
 * Creates instances of different class committee
 * {@link IDegreeOfInterestFunction} implementations.
 * 
 * Class committee DOIs require a list of
 * {@link IClassificationApplicationFunction}.
 * </p>
 * 
 * @version 1.01
 */
public class ClassCommitteeDegreeOfInterestFunctions {

	/**
	 * creates an instance of the particular class committee
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link IClassifier} is used to instantiate the DOI with a
	 * list of {@link IClassificationApplicationFunction}.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassCommitteeJensenShannonDivergence(
			List<? extends IClassifier<NumericalFeatureVector>> classifiers) {

		List<IClassificationApplicationFunction<NumericalFeatureVector>> classificationResults = new ArrayList<>();
		for (IClassifier<NumericalFeatureVector> classifier : classifiers)
			classificationResults.add(classifier::createClassificationResult);

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassCommitteeJensenShannonDivergenceInterestingnessFunction<NumericalFeatureVector>(
				classificationResults);
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular class committee
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link IClassifier} is used to instantiate the DOI with a
	 * list of {@link IClassificationApplicationFunction}.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassCommitteeKolmogorovSmirnov(
			List<? extends IClassifier<NumericalFeatureVector>> classifiers) {

		List<IClassificationApplicationFunction<NumericalFeatureVector>> classificationResults = new ArrayList<>();
		for (IClassifier<NumericalFeatureVector> classifier : classifiers)
			classificationResults.add(classifier::createClassificationResult);

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassCommitteeKolmogorovSmirnovInterestingnessFunction<NumericalFeatureVector>(
				classificationResults);
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular class committee
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link IClassifier} is used to instantiate the DOI with a
	 * list of {@link IClassificationApplicationFunction}.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassCommitteeKullbackLeiblerDivergence(
			List<? extends IClassifier<NumericalFeatureVector>> classifiers) {

		List<IClassificationApplicationFunction<NumericalFeatureVector>> classificationResults = new ArrayList<>();
		for (IClassifier<NumericalFeatureVector> classifier : classifiers)
			classificationResults.add(classifier::createClassificationResult);

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassCommitteeKullbackLeiblerInterestingnessFunction<NumericalFeatureVector>(
				classificationResults);
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular class committee
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link IClassifier} is used to instantiate the DOI with a
	 * list of {@link IClassificationApplicationFunction}.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassCommitteeProbabilityDistance(
			List<? extends IClassifier<NumericalFeatureVector>> classifiers) {

		List<IClassificationApplicationFunction<NumericalFeatureVector>> classificationResults = new ArrayList<>();
		for (IClassifier<NumericalFeatureVector> classifier : classifiers)
			classificationResults.add(classifier::createClassificationResult);

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassCommitteeProbabilityDistanceInterestingnessFunction<NumericalFeatureVector>(
				classificationResults);
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular class committee
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link IClassifier} is used to instantiate the DOI with a
	 * list of {@link IClassificationApplicationFunction}.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassCommitteeVoteCardinality(
			List<? extends IClassifier<NumericalFeatureVector>> classifiers) {

		List<IClassificationApplicationFunction<NumericalFeatureVector>> classificationResults = new ArrayList<>();
		for (IClassifier<NumericalFeatureVector> classifier : classifiers)
			classificationResults.add(classifier::createClassificationResult);

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassCommitteeVoteCardinality<NumericalFeatureVector>(
				classificationResults);
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular class committee
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link IClassifier} is used to instantiate the DOI with a
	 * list of {@link IClassificationApplicationFunction}.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassCommitteeVoteDiversity(
			List<? extends IClassifier<NumericalFeatureVector>> classifiers) {

		List<IClassificationApplicationFunction<NumericalFeatureVector>> classificationResults = new ArrayList<>();
		for (IClassifier<NumericalFeatureVector> classifier : classifiers)
			classificationResults.add(classifier::createClassificationResult);

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassCommitteeVoteDiversityInterestingnessFunction<NumericalFeatureVector>(
				classificationResults);
		return degreeOfInterestFunction;
	}

	/**
	 * creates an instance of the particular class committee
	 * {@link IDegreeOfInterestFunction}.
	 * 
	 * The given list of {@link IClassifier} is used to instantiate the DOI with a
	 * list of {@link IClassificationApplicationFunction}.
	 * 
	 * @param classifiers
	 * @return degree of interest function
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createClassCommitteeVoteEntropy(
			List<? extends IClassifier<NumericalFeatureVector>> classifiers) {

		List<IClassificationApplicationFunction<NumericalFeatureVector>> classificationResults = new ArrayList<>();
		for (IClassifier<NumericalFeatureVector> classifier : classifiers)
			classificationResults.add(classifier::createClassificationResult);

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new ClassCommitteeVoteEntropyInterestingnessFunction<NumericalFeatureVector>(
				classificationResults);
		return degreeOfInterestFunction;
	}
}
