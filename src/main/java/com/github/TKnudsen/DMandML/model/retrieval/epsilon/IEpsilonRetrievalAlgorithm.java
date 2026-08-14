package com.github.TKnudsen.DMandML.model.retrieval.epsilon;

import com.github.TKnudsen.DMandML.model.retrieval.IRetrievalAlgorithm;

/**
 * @version 1.01
 * @since 2016
 */
public interface IEpsilonRetrievalAlgorithm<FV> extends IRetrievalAlgorithm<FV> {

	public void setEpsilon(double epsilon);

}
