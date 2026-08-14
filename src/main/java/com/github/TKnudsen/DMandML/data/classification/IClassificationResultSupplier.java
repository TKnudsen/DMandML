package com.github.TKnudsen.DMandML.data.classification;

import java.util.function.Supplier;

/**
 * <p>
 * supplies downstream applications with a probablistic classisfication
 * result. Test FVs (X) already are included in the result. Example downstream
 * applications are active learners, predictors, or visual classificaiton
 * analysis tools.
 * </p>
 *
 * @version 1.03
 * @since 2016
 */
public interface IClassificationResultSupplier<X> extends Supplier<IClassificationResult<X>> {

}
