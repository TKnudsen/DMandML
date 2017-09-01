package com.github.TKnudsen.DMandML.model.supervised.classifier.evaluation;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;
import com.github.TKnudsen.DMandML.model.supervised.classifier.IClassifier;

public interface IClassifierEvaluation<O, FV extends AbstractFeatureVector<O, ? extends Feature<O>>, S extends String> {

	public double getQuality(IClassifier<O, FV> model, List<FV> testData, S targetVariable);
}
