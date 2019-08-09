package com.github.TKnudsen.DMandML.model.retrieval;

import java.util.Collection;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;
import com.github.TKnudsen.ComplexDataObject.data.ranking.Ranking;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

/**
 * <p>
 * Title: IRetrievalAlgorithm
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2019 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IRetrievalAlgorithm<FV> extends ISelfDescription {

	public IDistanceMeasure<? super FV> getDistanceMeasure();

	public Ranking<EntryWithComparableKey<Double, FV>> retrieveNeighbors(FV element);

	public Collection<FV> getElements();

}
