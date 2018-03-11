package com.github.TKnudsen.DMandML.model.supervised.classifier.use;

import java.util.List;
import java.util.function.Function;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;

/**
 * <p>
 * Title: IClassificationResultFunction
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
public interface IClassificationResultFunction<X>  extends Function<List<X>, IClassificationResult<X>>{


}
