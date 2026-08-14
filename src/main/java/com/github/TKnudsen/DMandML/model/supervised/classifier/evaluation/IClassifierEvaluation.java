package com.github.TKnudsen.DMandML.model.supervised.classifier.evaluation;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;
import com.github.TKnudsen.DMandML.model.supervised.classifier.IClassifier;

/**
 * <p>
 * Interface for classification quality assessment
 * </p>
 *
 * @version 1.02
 * @since 2017
 */
public interface IClassifierEvaluation<FV> extends ISelfDescription {

	public double getQuality(IClassifier<FV> model, List<FV> testData, String targetVariable);

}
