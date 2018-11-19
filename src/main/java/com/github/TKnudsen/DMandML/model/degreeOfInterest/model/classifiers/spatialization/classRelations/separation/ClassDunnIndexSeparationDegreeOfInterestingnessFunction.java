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
 * Assigns interestingness scores depending on depending on the minimum distance
 * between an instance and all other instances outside of its class. I.e. it
 * takes each class' separation into account. The higher the minimum distance
 * for an element the higher its score.
 * 
 * Single instance extension of: Dunn, J. (1974). Well-separated clusters and
 * optimal fuzzy partitions. Cybernetics and Systems, 4(1), 95-104.
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
public class ClassDunnIndexSeparationDegreeOfInterestingnessFunction<FV>
		extends ClassBasedDegreeOfInterestingnessFunction<FV> {
	private final IDistanceMeasure<FV> distanceMeasure;

	public ClassDunnIndexSeparationDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction,
			IDistanceMeasure<FV> distanceMeasure) {
		super(classificationApplicationFunction);
		this.distanceMeasure = distanceMeasure;
	}

	public ClassDunnIndexSeparationDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction, String classifierName,
			IDistanceMeasure<FV> distanceMeasure) {
		super(classificationApplicationFunction, classifierName);
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv, IClassificationResult<FV> result) {
		Map<String, List<FV>> classDistributions = result.getClassDistributions();
		String label = result.getClass(fv);
		if (label == null)
			throw new IllegalArgumentException(
					"ClassDunnIndexCompactnessDegreeOfInterestingnessFunction: Instance has to have an assigned class.");
		double separation = Double.MAX_VALUE;
		for (String l : classDistributions.keySet()) {
			if (l.equals(label))
				continue;
			for (FV f : classDistributions.get(l)) {
				separation = Math.min(separation, distanceMeasure.applyAsDouble(fv, f));
			}
		}
		return separation;
	}

	@Override
	public String getName() {
		return "Class Dunn's Index Separation [" + getClassifierName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the minimum distance between an instance and all other instances outside of its class.";
	}
}
