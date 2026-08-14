package com.github.TKnudsen.DMandML.model.retrieval.kNN;

import com.github.TKnudsen.DMandML.model.retrieval.IRetrievalAlgorithm;

/**
 * @version 1.01
 * @since 2016
 */
public interface IkNNRetrievalAlgorithm<FV> extends IRetrievalAlgorithm<FV> {

	public void setKNN(int kNN);

}
