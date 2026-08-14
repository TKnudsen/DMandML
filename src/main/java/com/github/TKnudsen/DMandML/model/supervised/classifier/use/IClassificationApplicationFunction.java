package com.github.TKnudsen.DMandML.model.supervised.classifier.use;

import java.util.List;
import java.util.function.Function;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;

/**
 * @version 1.03
 * @since 2016
 */
public interface IClassificationApplicationFunction<X> extends Function<List<? extends X>, IClassificationResult<X>> {

}
