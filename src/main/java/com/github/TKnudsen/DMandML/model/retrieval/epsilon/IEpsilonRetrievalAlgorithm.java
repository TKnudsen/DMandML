package com.github.TKnudsen.DMandML.model.retrieval.epsilon;

import com.github.TKnudsen.DMandML.model.retrieval.IRetrievalAlgorithm;

/**
 * <p>
 * Copyright: (c) 2016-2019 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IEpsilonRetrievalAlgorithm<FV> extends IRetrievalAlgorithm<FV> {

	public void setEpsilon(double epsilon);

}
