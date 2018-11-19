package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.separation;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

import java.util.List;
import java.util.Map;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.ClassBasedDegreeOfInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Assigns interestingness scores depending on the dissimilarity of each
 * instance w.r.t. the nearest class (that it is not part of). I.e. it takes the
 * classes' separation into account. The higher the dissimilarity for an element
 * the higher its score.
 * </p>
 * 
 * Based on: Peter J. Rousseeuw (1987). "Silhouettes: a Graphical Aid to the
 * Interpretation and Validation of Cluster Analysis". Computational and Applied
 * Mathematics. 20: p. 53-65. doi:10.1016/0377-0427(87)90125-7.
 * </p>
 * 
 * Builds upon the Local Class Separation (LCS) principle (estimates how well
 * the predicted classes around a given instance are separated from each other).
 * </p>
 * 
 * Published in: Juergen Bernard, Matthias Zeppelzauer, Markus Lehmann, Martin
 * Mueller, and Michael Sedlmair: Towards User-Centered Active Learning
 * Algorithms. Eurographics Conference on Visualization (EuroVis), Computer
 * Graphics Forum (CGF), 2018.
 * </p>
 * 
 * Measure: Euclidean distance measure
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.02
 */
public class ClassSilhuetteSeparationDegreeOfInterestingnessFunction<FV>
		extends ClassBasedDegreeOfInterestingnessFunction<FV> {
	private final IDistanceMeasure<FV> distanceMeasure;

	public ClassSilhuetteSeparationDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction,
			IDistanceMeasure<FV> distanceMeasure) {
		super(classificationApplicationFunction);
		this.distanceMeasure = distanceMeasure;
	}

	public ClassSilhuetteSeparationDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction, String classifierName,
			IDistanceMeasure<FV> distanceMeasure) {
		super(classificationApplicationFunction, classifierName);
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv, IClassificationResult<FV> result) {
		return calculateSilhuetteValidityInterClassDissimilarity(fv, result, distanceMeasure);
	}

	/**
	 * Dissimilarity of an element w.r.t. the nearest class (that it is not part
	 * of). Measure of class separation.
	 * 
	 * @param fv
	 * @param result
	 * @param distanceMeasure
	 * @return
	 */
	private Double calculateSilhuetteValidityInterClassDissimilarity(FV fv, IClassificationResult<FV> result,
			IDistanceMeasure<FV> distanceMeasure2) {
		if (fv == null || result == null || distanceMeasure == null)
			throw new IllegalArgumentException(
					"ClassSilhuetteSeparationDegreeOfInterestingnessFunction::calculateSilhuetteValidityInterClassDissimilarity: Given values must not be null.");

		String classLabel = result.getClass(fv);
		Map<String, List<FV>> classDistributions = result.getClassDistributions();

		// Find distance to nearest class to fv
		double minDistance = Double.MAX_VALUE;
		for (String label : classDistributions.keySet()) {
			if (label.equals(classLabel))
				continue;
			minDistance = Math.min(minDistance,
					classDistributions.get(label).stream().map(x -> distanceMeasure.applyAsDouble(fv, x)).reduce(0.0,
							(x, y) -> x + y) / classDistributions.get(label).size());
		}
		return minDistance;
	}

	@Override
	public String getName() {
		return "Class Silhuette Separation [" + getClassifierName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the dissimilarity between each instance and the nearest class (that it is not part of).";
	}
}
