package com.github.TKnudsen.DMandML.model.distanceMeasure.cluster;

import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.DMandML.data.cluster.ICluster;

public class CentroidDistanceMeasure<T> extends ClusterDistanceMeasure<T> {

	public CentroidDistanceMeasure(ToDoubleBiFunction<? super T, ? super T> distanceMeasure) {
		super(distanceMeasure);
	}

	@Override
	public double getDistance(ICluster<T> o1, ICluster<T> o2) {
		return getDistanceMeasure().applyAsDouble(o1.getCentroid().getData(), o2.getCentroid().getData());
	}

	@Override
	public String getName() {
		return CentroidDistanceMeasure.class.getSimpleName();
	}

	@Override
	public String getDescription() {
		return "returns the distance between the centroids of two clusters";
	}

}
