package com.github.TKnudsen.DMandML.model.distanceMeasure.cluster;

import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.DMandML.data.cluster.ICluster;

/**
 * <p>
 * Copyright: (c) 2016-2020 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 */
public class SingleLinkageDistanceMeasure<T> extends ClusterDistanceMeasure<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2706781830339589800L;

	public SingleLinkageDistanceMeasure(ToDoubleBiFunction<? super T, ? super T> distanceMeasure) {
		super(distanceMeasure);
	}

	@Override
	public String getName() {
		return "Single Linkage";
	}

	@Override
	public String getDescription() {
		return "Calculates similarity of two clusters w.r.t. their two most similar elements";
	}

	@Override
	public String toString() {
		return "Single Linkage";
	}

	@Override
	public double getDistance(ICluster<T> c1, ICluster<T> c2) {
		double distance = Double.MAX_VALUE;
		for (T dp1 : c1.getElements())
			for (T dp2 : c2.getElements())
				distance = Math.min(distance, getDistanceMeasure().applyAsDouble(dp1, dp2));

		return distance;
	}
}
