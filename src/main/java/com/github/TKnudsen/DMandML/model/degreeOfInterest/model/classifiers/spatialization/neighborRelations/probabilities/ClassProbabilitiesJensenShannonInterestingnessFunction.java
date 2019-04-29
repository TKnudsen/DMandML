package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.neighborRelations.probabilities;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.Double.probabilities.JensenShannonDivergenceDistance;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2019 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Spatial Class Divergence measure. Uses the probability distributions of
 * instances in the vicinity and compares them with the probabilites of an
 * instance i. Divergence measures such as the Jenson Shannon divergence can
 * then be used to assess the local divergence.
 * 
 * Divergence measure: JensenShannon
 * </p>
 * 
 * Measure: Euclidean distance measure
 * </p>
 * 
 * Builds upon the Class Likelihood (CL) DOI/building block (probability
 * distribution per instance) published in: Juergen Bernard, Matthias
 * Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair: Towards
 * User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.03
 */
public class ClassProbabilitiesJensenShannonInterestingnessFunction<FV>
		extends ClassProbabilitiesDivergenceInterestingnessFunction<FV> {

	public ClassProbabilitiesJensenShannonInterestingnessFunction(
			IClassificationApplicationFunction<FV> probabilisticClassificationResultFunction, int kNN,
			IDistanceMeasure<FV> distanceMeasure, String classifierName) {

		super(probabilisticClassificationResultFunction, kNN, distanceMeasure, new JensenShannonDivergenceDistance(),
				classifierName);
	}

	@Override
	public String getName() {
		return "Class Probability Jensen Shannon [" + getClassifierName() + "]";
	}

}
