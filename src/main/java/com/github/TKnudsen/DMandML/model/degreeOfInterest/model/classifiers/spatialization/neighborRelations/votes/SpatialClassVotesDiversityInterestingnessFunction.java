package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.votes;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.ClassificationBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.retrieval.KNN;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Spatial Class Diversity (LCD) (local class spatialization) assesses the
 * diversity of class predictions (winning labels) in the neighborhood of an
 * instance x. Thus, each instance needs to have a winning class y assigned by a
 * classifier. Given an instance x and a class label yi, we can compute the
 * portion pi of neighbors with the class prediction y0 = yi.
 * </p>
 * 
 * The local class diversity can then be estimated by a diversity measure div as
 * follows: LCD(x) = div(p), where p is the vector of all portions pi for the n
 * classes: p = (p1; ::; pn). The entropy of p is one possible choice for
 * function div.
 * </p>
 * 
 * TODO this type of DOIs does not reflect concrete distances of near neighbors
 * but simply iterates over the kNN. Adding weights w.r.t. the spatial proximity
 * might be another promising approach.
 * </p>
 * 
 * @version 1.03
 */
public abstract class SpatialClassVotesDiversityInterestingnessFunction<FV>
		extends ClassificationBasedInterestingnessFunction<FV> {

	private final int kNN;

	private final IDistanceMeasure<FV> distanceMeasure;

	private KNN<FV> kNNSupport;

	public SpatialClassVotesDiversityInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction, int kNN,
			IDistanceMeasure<FV> distanceMeasure) {
		this(probabilisticClassificationResultFunction, kNN, distanceMeasure, null);
	}

	public SpatialClassVotesDiversityInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction, int kNN,
			IDistanceMeasure<FV> distanceMeasure, String classifierName) {
		super(probabilisticClassificationResultFunction, classifierName);

		this.kNN = kNN;
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> featureVectors) {
		Objects.requireNonNull(featureVectors);
		if (featureVectors.isEmpty())
			return Collections.emptyMap();

		IClassificationResult<FV> classificationResult = computeClassificationResult(featureVectors);

		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();
		Collection<Number> values = new ArrayList<>();

		if (classificationResult == null || classificationResult.getClassDistributions() == null) {
			for (FV fv : featureVectors)
				interestingnessScores.put(fv, 0.0);

			return interestingnessScores;
		}

		Set<String> labelAlphabet = classificationResult.getLabelAlphabet();

		kNNSupport = new KNN<FV>(kNN, distanceMeasure, featureVectors);

		for (FV fv : featureVectors) {
			List<FV> nn = kNNSupport.getNearestNeighbors(fv);

			Map<String, Integer> wins = new LinkedHashMap<>();
			for (String label : labelAlphabet)
				wins.put(label, 0);

			for (FV neighbor : nn) {
				String label = classificationResult.getClass(neighbor);
				wins.put(label, wins.get(label) + 1);
			}

			double diversity = calculateDivsersity(wins.values());

			interestingnessScores.put(fv, diversity);
			values.add(diversity);
		}

		// post-processing
		NormalizationFunction normalizationFunction = new LinearNormalizationFunction(values);
		for (FV fv : interestingnessScores.keySet())
			interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

		// smallest values will become the highest.
		return interestingnessScores;
	}

	protected abstract double calculateDivsersity(Collection<Integer> votes);

	@Override
	public String getName() {
		return this.getClass().getSimpleName() + ": " + getClassifierName();
	}

	@Override
	public String getDescription() {
		return getName();
	}
}
