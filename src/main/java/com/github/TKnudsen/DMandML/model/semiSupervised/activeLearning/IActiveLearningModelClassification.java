package com.github.TKnudsen.DMandML.model.semiSupervised.activeLearning;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResultSupplier;

/**
 * @version 1.06
 * @since 2016
 */
public interface IActiveLearningModelClassification<FV> extends IActiveLearningModel<FV, String> {

	@Deprecated
	public IClassificationResultSupplier<FV> getClassificationResultSupplier();
}