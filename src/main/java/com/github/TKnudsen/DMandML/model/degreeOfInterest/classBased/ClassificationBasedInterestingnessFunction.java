package com.github.TKnudsen.DMandML.model.degreeOfInterest.classBased;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.IFeatureVectorSupplier;
import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResultSupplier;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.DegreeOfInterestFunction;

/**
 * <p>
 * Title: ClassificationBasedInterestingnessFunction
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public abstract class ClassificationBasedInterestingnessFunction<FV extends AbstractFeatureVector<?, ?>> extends DegreeOfInterestFunction<FV> implements IClassificationBasedInterestingnessFunction<FV> {

	IProbabilisticClassificationResultSupplier<FV> classificationResultSupplier;

	public ClassificationBasedInterestingnessFunction(IFeatureVectorSupplier<FV> featureVectorSupplier, IProbabilisticClassificationResultSupplier<FV> classificationResultSupplier) {
		super(featureVectorSupplier);

		this.classificationResultSupplier = classificationResultSupplier;
	}

	@Override
	public IProbabilisticClassificationResultSupplier<FV> getClassificationResultSupplier() {
		return classificationResultSupplier;
	}

}
