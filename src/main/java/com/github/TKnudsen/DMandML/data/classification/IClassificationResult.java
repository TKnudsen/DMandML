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
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 */
public interface IClassificationResult<X> {

	public Collection<X> getFeatureVectors();

	public String getClass(X featureVector);

	public Map<String, List<X>> getClassDistributions();
}
