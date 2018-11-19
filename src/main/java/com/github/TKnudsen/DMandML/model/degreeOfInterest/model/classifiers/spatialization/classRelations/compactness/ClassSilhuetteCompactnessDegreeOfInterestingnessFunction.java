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
 * Assigns interestingness scores depending on the dissimilarity of each
 * instance w.r.t. its class. I.e. it takes each class' compactness into
 * account. The higher the similarity for an element the higher its score.
 * </p>
 * 
 * Based on: Peter J. Rousseeuw (1987). "Silhouettes: a Graphical Aid to the
 * Interpretation and Validation of Cluster Analysis". Computational and Applied
 * Mathematics. 20: p. 53-65. doi:10.1016/0377-0427(87)90125-7.
 * </p>
 * 
 * Implementation of the Compactness Estimation (CE) DOI/building block published in: Juergen Bernard,
 * Matthias Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair:
 * Towards User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @author Christian Ritter
 * @author Juergen Bernard
 * 
 * @version 1.02
 */
public class ClassSilhuetteCompactnessDegreeOfInterestingnessFunction<FV>
		extends ClassBasedDegreeOfInterestingnessFunction<FV> {
	private final IDistanceMeasure<FV> distanceMeasure;

	public ClassSilhuetteCompactnessDegreeOfInterestingnessFunction(
			IClassificationApplicationFunction<FV> classificationApplicationFunction,
			IDistanceMeasure<FV> distanceMeasure) {
		super(classificationApplicationFunction);
		this.distanceMeasure = distanceMeasure;
	}

	public ClassSilhuetteCompactnessDegreeOfInterestingnessFunction(
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
					"ClassSilhuetteCompactnessDegreeOfInterestingnessFunction: Instance has to have an assigned class.");
		return -calculateSilhuetteValidityIntraClassDissimilarity(fv, label, result, distanceMeasure);
	}

	/**
	 * Dissimilarity of an element w.r.t. its class. Measure of class compactness.
	 * 
	 * For reference see Rousseeuw, 1987.
	 * 
	 * @param fv
	 * @param label
	 * @param result
	 * @param distanceMeasure
	 * @return
	 */
	private Double calculateSilhuetteValidityIntraClassDissimilarity(FV fv, String label,
			IClassificationResult<FV> result, IDistanceMeasure<FV> distanceMeasure2) {
		if (fv == null || label == null || result == null || distanceMeasure == null)
			throw new IllegalArgumentException(
					"ClassSilhuetteCompactnessDegreeOfInterestingnessFunction::calculateSilhuetteValidityIntraClassDissimilarity: Given values must not be null.");
		// calculate distance to all elements in class except the element
		// itself
		Double dissimilarity = result.getClassDistributions().get(label).stream().filter(x -> !x.equals(fv))
				.map(x -> distanceMeasure.applyAsDouble(x, fv)).reduce(0.0, (x, y) -> x + y)
				/ result.getClassDistributions().get(label).size();
		return dissimilarity;
	}

	@Override
	public String getName() {
		return "Class Silhuette Compactness [" + getClassifierName() + "]";
	}

	@Override
	public String getDescription() {
		return "Assigns interestingness scores depending on the dissimilarity of each instance w.r.t. its class.";
	}
}
