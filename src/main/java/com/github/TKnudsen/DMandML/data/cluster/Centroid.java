package com.github.TKnudsen.DMandML.data.cluster;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.IMasterProvider;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;
import com.github.TKnudsen.ComplexDataObject.model.tools.MathFunctions;

/**
 * <p>
 * Title: Centroid
 * </p>
 * 
 * <p>
 * Description: baseline data structure for modeling centroids.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */

public class Centroid<T> implements ISelfDescription, IMasterProvider {

	private long ID = MathFunctions.randomLong();

	private T data;
	protected Cluster<T> cluster;
	private int hash = -1;

	public Centroid(Cluster<T> cluster, T data) {
		this.cluster = cluster;
		this.data = data;
		hash = -1;
	}

	public Centroid<T> clone() {
		Centroid<T> c = new Centroid<T>(cluster, data);
		return c;
	}

	public T getData() {
		return data;
	}

	@Override
	public String toString() {
		return "Centroid, representing " + data.toString();
	}

	@Override
	public long getID() {
		if (data != null && data instanceof IDObject)
			return ((IDObject) data).getID();

		return ID;
	}

	@Override
	public Cluster<T> getMaster() {
		return cluster;
	}

	@Override
	public String getName() {
		if (data instanceof ISelfDescription)
			return ((ISelfDescription) data).getName();
		else
			return data.toString();
	}

	@Override
	public int hashCode() {
		if (hash == -1) {
			hash = 19;
			hash = 13 * hash + data.hashCode();
		}
		return hash;
	}

	@Override
	public String getDescription() {
		if (data instanceof ISelfDescription)
			return ((ISelfDescription) data).getDescription();
		else
			return data.toString();
	}
}