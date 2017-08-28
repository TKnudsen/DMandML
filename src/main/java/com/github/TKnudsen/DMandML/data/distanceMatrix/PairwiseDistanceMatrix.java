package main.java.com.github.TKnudsen.DMandML.data.distanceMatrix;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrix;

/**
 * <p>
 * Title: PairwiseDistanceMatrix
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard
 * </p>
 * 
 * @author Christian Ritter
 * @version 1.01
 */
public class PairwiseDistanceMatrix<T> extends DistanceMatrix<T> {

	public PairwiseDistanceMatrix(List<T> objects, double[][] pairwiseDistances) {
		super(objects, null);
		this.distanceMatrix = pairwiseDistances;
		initializeObjectIndex();
	}

}
