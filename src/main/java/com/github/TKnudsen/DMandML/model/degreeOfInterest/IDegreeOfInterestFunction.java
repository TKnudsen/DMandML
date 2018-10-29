package com.github.TKnudsen.DMandML.model.degreeOfInterest;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * A function that computes an "interestingness" for a collection of values.<br>
 * <br>
 * The default value domain is [0...1] as this allows an easy combination of
 * several (weighted) interestingness functions.<br>
 * 
 * @version 1.02
 */

public interface IDegreeOfInterestFunction<FV> extends Function<List<? extends FV>, Map<FV, Double>>, ISelfDescription {
	// No additional methods
}
