package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.compactness;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

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
 * Assigns interestingness scores depending on depending on the maximum distance
 * between an instance and all other instances within its class. I.e. it takes
 * each class' compactness into account. The lower the maximum distance for an
 * element the higher its score.
 * 
 * Single instance extension of: Dunn, J. (1974). Well-separated clusters and
 * optimal fuzzy partitions. Cybernetics and Systems, 4(1), 95-104.
 * </p>
 * 
 * Implementation of the Compactness Estimation (CE) DOI/building block
 * published in: Juergen Bernard, Matthias Zeppelzauer, Markus Lehmann, Martin
 * Mueller, and Michael Sedlmair: Towards User-Centered Active Learning
 * Algorithms. Eurographics Conference on Visualization (EuroVis), Computer
 * Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.02
 */
public class ClassDunnIndexCompactnessDegreeOfInterestingnessFunction<FV>
		extends ClassBasedDegreeOfInterestingnessFunction<FV> {
	private final IDistanceMeasure<FV> distanceMeasure;

	public ClassDunnIndexCompactnessDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction,
			IDistanceMeasure<FV> distanceMeasure) {
		super(classificationApplicationFunction);
		this.distanceMeasure = distanceMeasure;
	}

	public ClassDunnIndexCompactnessDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction, String classifierName,
			IDistanceMeasure<FV> distanceMeasure) {
		super(classificationApplicationFunction, classifierName);
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	protected Double calculateInterestingnessScore(FV fv, IClassificationResult<FV> result) {
		String label = result.getClass(fv);
		if (label == null)
			throw new IllegalArgumentException(
					"ClassDunnIndexCompactnessDegreeOfInterestingnessFunction: Instance has to have an assigned class.");
		double compactness = Double.MIN_VALUE;
		for (FV f : result.getClassDistributions().get(label)) {
			if (fv.equals(f))
				continue;
			compactness = Math.max(compactness, distanceMeasure.applyAsDouble(fv, f));
		}
		return -compactness;
	}

	@Override
	public String getName() {
		return "Class Dunn's Index Compactness [" + getClassifierName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the maximum distance between an instance and all other instances within its class.";
	}
}
