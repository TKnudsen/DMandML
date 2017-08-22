package main.java.com.github.TKnudsen.DMandML.data;

import java.util.Set;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

/**
 * <p>
 * Title: ICluster
 * </p>
 * 
 * <p>
 * Description: general behavior if a cluster data structure.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public interface ICluster<T extends IDObject> {

	/**
	 * self-explanation
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * number of elements in it
	 * 
	 * @return
	 */
	public int size();

	/**
	 * allows the assessment of centroid distances and related stuff
	 * 
	 * @return
	 */
	public IDistanceMeasure<T> getDistanceMeasure();

	/**
	 * in most cases clusters can be represented with a centroid
	 * 
	 * @return
	 */
	public Centroid<T> getCentroid();

	/**
	 * retrieves the distance of a given T to the centroid. to be discussed:
	 * shall an exception be thrown iff T is not contained in the cluster?!
	 * 
	 * @param element
	 * @return
	 */
	public double getCentroidDistance(T element);

	/**
	 * in some cases T's may be added to a cluster
	 * 
	 * @param element
	 */
	public boolean add(T element);

	/**
	 * in some cases T's may be removed from a cluster
	 * 
	 * @param element
	 */
	public boolean remove(T element);

	/**
	 * alternative to the iterator.
	 * 
	 * @return
	 */
	public Set<T> getElements();

	/**
	 * variance information produced by the elements of the cluster.
	 * 
	 * @return
	 */
	public double getVariance();

	/**
	 * we are interested in the T's that are contained in clusters. The
	 * constellation of these guys should also determine the equals function
	 * 
	 * @param o
	 * @return
	 */
	public boolean equals(Object o);

	public int hashCode();
}
