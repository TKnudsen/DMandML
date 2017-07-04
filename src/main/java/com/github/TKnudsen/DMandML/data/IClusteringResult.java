package main.java.com.github.TKnudsen.DMandML.data;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;

public interface IClusteringResult<T extends IDObject, C extends Cluster<T>> extends IDObject, ISelfDescription, Iterable<C> {

	boolean containsCluster(C c);

	List<C> getClusters();

	/**
	 * retrieves the index of the cluster, if a given object is assigned to one
	 * of the clusters.
	 * 
	 * @param object
	 * @return -1 if not assigned. else the cluster index w.r.t. the index of
	 *         the internal cluster list.
	 */
	int getClusterIndex(T object);

	/**
	 * Retrieves the cluster for a given object. Returns null if object was not
	 * subject to the clustering routine, though.
	 * 
	 * @param fv
	 * @return
	 */
	C getCluster(T fv);

	/**
	 * Retrieves the cluster for a given object. If getClusterMapping is null
	 * (if object was not part of the clustering routine) AND
	 * retrievNearestWhenUnassigned is set true, the method retrieves the
	 * nearest cluster for the object.
	 * 
	 * @param fv
	 * @param retrieveNearestWhenUnassigned
	 *            if a cluster is retrieved in case no assignment is exists.
	 * @return
	 */
	C retrieveCluster(T fv);

	int size();

	int hashCode();

	boolean equals(Object obj);

}