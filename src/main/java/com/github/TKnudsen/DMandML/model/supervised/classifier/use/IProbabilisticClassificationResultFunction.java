package com.github.TKnudsen.DMandML.model.supervised.classifier.use;

import java.util.List;
import java.util.function.Function;

import com.github.TKnudsen.DMandML.data.classification.IProbabilisticClassificationResult;

/**
 * <p>
 * Title: IProbabilisticClassificationResultFunction
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IProbabilisticClassificationResultFunction<X> extends Function<List<? extends X>, IProbabilisticClassificationResult<X>> {
	// No additional methods
}
