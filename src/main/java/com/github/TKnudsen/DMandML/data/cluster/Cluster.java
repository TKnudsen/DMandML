package com.github.TKnudsen.DMandML.data.cluster;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.tools.MathFunctions;

/**
 * <p>
 * Title: Cluster
 * </p>
 * 
 * <p>
 * Description: data structure for clusters.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.05
 */
public abstract class Cluster<T> implements ICluster<T>, IDObject {

	/**
	 * object ID
	 */
	protected long ID;

	/**
	 * name of the cluster
	 */
	private String name;

	/**
	 * detailed description of the cluster
	 */
	private String description;

	/**
	 * data objects contained by the cluster
	 */
	protected final Set<? extends T> elements;

	/**
	 * distance measure for the calculation of centroid distances, etc.
	 */
	protected final IDistanceMeasure<T> distanceMeasure;

	/**
	 * centroid of the cluster should never be calculated lazy, because it is simply
	 * returned with the getCentroid() method.
	 */
	protected Centroid<T> centroid;

	/**
	 * hash code
	 */
	protected int hash = -1;

	/**
	 * number of dimensions
	 */
	protected int dimensionality = -1;

	/**
	 * cluster variance. lazy implementation.
	 */
	protected double variance = Double.NaN;

	public Cluster(Collection<? extends T> elements, IDistanceMeasure<T> distanceMeasure) {
		this(elements, distanceMeasure, null, null);
	}

	public Cluster(Collection<? extends T> elements, IDistanceMeasure<T> distanceMeasure, String name,
			String description) {
		this.ID = MathFunctions.randomLong();
		this.elements = new LinkedHashSet<>(elements);

		this.distanceMeasure = Objects.requireNonNull(distanceMeasure, "distance measure may not be null");

		this.name = (name == null) ? "Cluster with " + elements.size() + " elements" : name;
		this.description = (description == null) ? "Cluster with " + elements.size() + " elements" : description;

		calculateCentroid();
	}

	/**
	 * determine the T representing the Cluster best.
	 */
	protected void calculateCentroid() {
		centroid = Clusters.calculateCentroidLikeElement(this, distanceMeasure);

		// reset variance.
		this.variance = Double.NaN;
	}

	@Override
	public double getVariance() {
		if (Double.isNaN(variance)) {
			calculateCentroid();
			double acc = 0;
			for (T cps : getElements())
				acc += Math.pow(getCentroidDistance(cps), 2);

			variance = acc / (double) size();
		}

		return variance;
	}

	@Override
	public long getID() {
		return ID;
	}

	@Override
	public String toString() {
		return "Cluster " + getName() + ", size: " + size() + ". Centroid: " + getCentroid().toString();
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int size() {
		if (elements != null)
			return elements.size();
		return 0;
	}

	@Override
	public IDistanceMeasure<T> getDistanceMeasure() {
		return distanceMeasure;
	}

	@Override
	public final Centroid<T> getCentroid() {
		return centroid;
	}

	@Override
	public double getCentroidDistance(T element) {
		return distanceMeasure.getDistance(element, centroid.getData());
	}

	@Override
	public boolean add(T element) {
		boolean modified = getElements().add(element);

		if (modified) {
			dimensionality = -1;
			hash = -1;
		}

		return modified;
	}

	@Override
	public boolean remove(Object element) {
		boolean modified = getElements().remove(element);

		if (modified) {
			dimensionality = -1;
			hash = -1;
		}

		return modified;
	}

	@Override
	public boolean contains(Object element) {
		return getElements().contains(element);
	}

	public abstract Cluster<T> clone();

	@Override
	public int hashCode() {
		if (hash == -1) {
			hash = getElements().hashCode();
		}

		return hash;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Cluster))
			return false;

		@SuppressWarnings("unchecked")
		Cluster<T> other = (Cluster<T>) obj;

		return getElements().equals(other.getElements());
	}

	@Override
	public Set<T> getElements() {
		return Collections.unmodifiableSet(elements);
	}

	@Override
	public Iterator<T> iterator() {
		return getElements().iterator();
	}
}
