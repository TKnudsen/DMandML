package com.github.TKnudsen.DMandML.model.semiSupervised.activeLearning;

import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResultSupplier;

/**
 * <p>
 * Title: IActiveLearningModelClassification
 * </p>
 * 
 * <p>
 * Active learning for classification tasks.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.05
 */
public interface IActiveLearningModelClassification<FV> extends IActiveLearningModel<FV, String> {

	public IProbabilisticClassificationResultSupplier<FV> getClassificationResultSupplier();
}