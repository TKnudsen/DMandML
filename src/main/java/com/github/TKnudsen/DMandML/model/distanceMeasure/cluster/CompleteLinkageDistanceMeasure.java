package com.github.TKnudsen.DMandML.model.distanceMeasure.cluster;

import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.DMandML.data.cluster.ICluster;

/**
 * @version 1.02
 * @since 2016
 */
public class CompleteLinkageDistanceMeasure<T> extends ClusterDistanceMeasure<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1623946820035406623L;

	public CompleteLinkageDistanceMeasure(ToDoubleBiFunction<? super T, ? super T> distanceMeasure) {
		super(distanceMeasure);
	}

	@Override
	public String getName() {
		return "Complete Linkage";
	}

	@Override
	public String getDescription() {
		return "Calculates the similarity of two clusters w.r.t. their most dissimilar elements";
	}

	@Override
	public String toString() {
		return "Complete Linkage Distance";
	}

	@Override
	/**
	 * TODO: identical with maximum distance. check if this is correct.
	 */
	public double getDistance(ICluster<T> c1, ICluster<T> c2) {
		double distance = Double.MAX_VALUE;
		for (T idObject1 : c1.getElements()) {
			for (T idObject2 : c1.getElements()) {
				distance = Math.max(distance, getDistanceMeasure().applyAsDouble(idObject1, idObject2));
			}
		}

		return distance;
	}
}
