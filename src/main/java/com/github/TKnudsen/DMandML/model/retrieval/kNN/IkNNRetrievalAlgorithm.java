package com.github.TKnudsen.DMandML.model.retrieval.kNN;

import com.github.TKnudsen.DMandML.model.retrieval.IRetrievalAlgorithm;

/**
 * <p>
 * Copyright: (c) 2016-2019 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IkNNRetrievalAlgorithm<FV> extends IRetrievalAlgorithm<FV> {

	public void setKNN(int kNN);

}
