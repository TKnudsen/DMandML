package com.github.TKnudsen.DMandML.data.cluster;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;

/**
 * <p>
 * Title: IClusteringResult
 * </p>
 * 
 * <p>
 * Description: interface for clustering results.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.04
 */
public interface IClusteringResult<T, C extends ICluster<T>> extends IDObject, ISelfDescription, Iterable<C> {

	boolean containsCluster(C c);

	List<C> getClusters();

	/**
	 * retrieves the index of the cluster, if a given object is assigned to one of
	 * the clusters.
	 * 
	 * @deprecated this should never be necessary. try to avoid and use cluster
	 *             objects instead
	 * @param object the query object
	 * @return -1 if not assigned. else the cluster index w.r.t. the index of the
	 *         internal cluster list.
	 */
	int getClusterIndex(T object);

	/**
	 * Retrieves the cluster for a given object. Returns null if object was not
	 * subject to the clustering routine, though.
	 * 
	 * @param object the query object
	 * @return the cluster of the query object
	 */
	C getCluster(T object);

	/**
	 * Retrieves the cluster for a given object. If getClusterMapping is null (if
	 * object was not part of the clustering routine) AND
	 * retrievNearestWhenUnassigned is set true, the method retrieves the nearest
	 * cluster for the object.
	 * 
	 * @param object the query feature vector object
	 * @return the cluster that is retrieved for the feature vector object, if any
	 */
	C retrieveCluster(T object);

	int size();

	int hashCode();

	boolean equals(Object obj);

}