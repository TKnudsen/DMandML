package com.github.TKnudsen.DMandML.model.distanceMeasure.cluster;

import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;

/**
 * <p>
 * Copyright: (c) 2016-2020 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.07
 */
public abstract class ClusterDistanceMeasure<T> implements IDistanceMeasure<ICluster<T>> {

	private ToDoubleBiFunction<? super T, ? super T> distanceMeasure;

	public ClusterDistanceMeasure(ToDoubleBiFunction<? super T, ? super T> distanceMeasure) {
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	public double applyAsDouble(ICluster<T> t, ICluster<T> u) {
		return getDistance(t, u);
	}

	public ToDoubleBiFunction<? super T, ? super T> getDistanceMeasure() {
		return distanceMeasure;
	}

	public void setDistanceMeasure(IDistanceMeasure<T> distanceMeasure) {
		this.distanceMeasure = distanceMeasure;
	}
}
