package main.java.com.github.TKnudsen.DMandML.model.unsupervised.clustering;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

import main.java.com.github.TKnudsen.DMandML.data.cluster.Cluster;

/**
 * <p>
 * Title: IClusterSplittingAlgorithm
 * </p>
 * 
 * <p>
 * Description: splits/divides a cluster into *n* clusters
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public interface IClusterSplittingAlgorithm<O extends IDObject, C extends Cluster<O>> extends ISelfDescription {

	public void setSplitCount(int k);

	public IDistanceMeasure<O> getDistanceMeasure();

	public List<C> splitCluster(C cluster);
}