package com.github.TKnudsen.DMandML.model.distanceMeasure.cluster;

import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.DMandML.data.cluster.ICluster;

/**
 * <p>
 * Copyright: (c) 2016-2020 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.05
 */
public class AverageLinkageDistanceMeasure<T> extends ClusterDistanceMeasure<T> {

	public AverageLinkageDistanceMeasure(ToDoubleBiFunction<? super T, ? super T> distanceMeasure) {
		super(distanceMeasure);
	}

	@Override
	public String getName() {
		return "Average Linkage";
	}

	@Override
	public String getDescription() {
		return "Average Linkage distance between two clusters";
	}

	@Override
	public String toString() {
		return "Average Linkage";
	}

	@Override
	public double getDistance(ICluster<T> c1, ICluster<T> c2) {
		double distance = Double.MAX_VALUE;

		if (c1 == null || c2 == null)
			return distance;

		for (T fv1 : c1.getElements())
			for (T fv2 : c2.getElements())
				distance += getDistanceMeasure().applyAsDouble(fv1, fv2);

		distance = distance / (c1.getElements().size() * c2.getElements().size());

		return distance;
	}

}
