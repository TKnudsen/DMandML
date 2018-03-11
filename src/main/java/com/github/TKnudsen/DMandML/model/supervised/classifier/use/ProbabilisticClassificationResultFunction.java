package com.github.TKnudsen.DMandML.model.supervised.classifier.use;

import java.util.List;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.supervised.classifier.IProbabilisticClassifier;

/**
 * <p>
 * Title: ProbabilisticClassificationResultFunction
 * </p>
 * 
 * <p>
 * Description: Provides a classification result for a given list of data. hides
 * the classifier used. does not allow training the classifier. is better
 * maintainable than classification result suppliers.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 * 
 */
public class ProbabilisticClassificationResultFunction<X> implements IClassificationResultFunction<X> {

	private IProbabilisticClassifier<X> classifier;

	public ProbabilisticClassificationResultFunction(IProbabilisticClassifier<X> classifier) {
		this.classifier = classifier;
	}

	@Override
	public IClassificationResult<X> apply(List<? extends X> t) {
		if (classifier == null)
			throw new NullPointerException(
					"ProbabilisticClassificationResultFunction: classifier is null and cannot be used to create classification results.");

		return classifier.createClassificationResult(t);
	}

}
