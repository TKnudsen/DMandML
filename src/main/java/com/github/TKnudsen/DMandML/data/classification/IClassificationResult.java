package com.github.TKnudsen.DMandML.data.classification;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
 * @version 1.02
 */
public interface IClassificationResult<X> {

	public Collection<X> getFeatures();

	public String getClass(X featureVector);

	public Map<String, List<X>> getClassDistributions();
}
