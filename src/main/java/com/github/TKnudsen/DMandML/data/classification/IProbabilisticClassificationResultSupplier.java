package com.github.TKnudsen.DMandML.data.classification;

import java.util.function.Supplier;

/**
 * <p>
 * Title: IProbabilisticClassificationResultSupplier
 * </p>
 * 
 * <p>
 * Description: supplies downstream applications with a probablistic
 * classisfication result. Test FVs (X) already are included in the result.
 * Example downstream applications are active learners, predictors, or visual
 * classificaiton analysis tools.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IProbabilisticClassificationResultSupplier<X> extends Supplier<IProbabilisticClassificationResult<X>> {

}
