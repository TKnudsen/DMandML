package com.github.TKnudsen.DMandML.data.cluster;

import java.util.function.Supplier;

/**
 * <p>
 * supplies a clustering result. the supplier maintains the clustering result,
 * which may update according to the supplier's responsibility.
 * </p>
 *
 * @version 1.01
 * @since 2016
 */
public interface IClusteringResultSupplier<CR extends IClusteringResult<?, ?>> extends Supplier<CR> {

}