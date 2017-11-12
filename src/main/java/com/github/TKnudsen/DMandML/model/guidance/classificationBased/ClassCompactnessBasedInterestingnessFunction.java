package com.github.TKnudsen.DMandML.model.guidance.classificationBased;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.FeatureVectorSupplier;
import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResultSupplier;

public class ClassCompactnessBasedInterestingnessFunction<FV extends AbstractFeatureVector<?, ?>> extends ClassificationBasedInterestingnessFunction<FV> {

	
	
	public ClassCompactnessBasedInterestingnessFunction(FeatureVectorSupplier<FV> featureVectorSupplier, IProbabilisticClassificationResultSupplier<FV> classificationResultSupplier) {
		super(featureVectorSupplier, classificationResultSupplier);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
