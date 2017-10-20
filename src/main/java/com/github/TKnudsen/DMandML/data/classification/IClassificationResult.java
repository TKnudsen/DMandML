package com.github.TKnudsen.DMandML.data.classification;

import java.util.Collection;

/**
 * <p>
 * Title: IClassificationResult
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IClassificationResult<X> {

	public Collection<X> getFeatures();

	public String getClass(X featureVector);
}
