package main.java.com.github.TKnudsen.DMandML.data.distanceMatrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrix;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

/**
 * <p>
 * Title: SparseDistanceMatrix
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Christian Ritter
 * @version 1.01
 */
public class SparseDistanceMatrix<T> extends DistanceMatrix<T> {

	private Map<T, Map<T, Double>> distanceMapping;
	private double threshold;

	public SparseDistanceMatrix(List<T> objects, IDistanceMeasure<T> distanceMeasure, double threshold) {
		super(objects, distanceMeasure);
		this.threshold = threshold;
		initializeDistanceMatrix();
	}

	@Override
	public double applyAsDouble(T t, T u) {
		return getDistance(t, u);
	}

	@Override
	public String getDescription() {
		return "Removes all distances that are greater than or equal to a threshold to save memory.";
	}

	@Override
	public double getDistance(T o1, T o2) {
		if (o1 == null || o2 == null)
			return threshold;
		else if (o1.equals(o2))
			return 0.0;
		else if (objects.indexOf(o1) < objects.indexOf(o1)) {
			Double dist = distanceMapping.get(o1).get(o2);
			if (dist == null)
				return threshold;
			else
				return dist;
		} else if (objects.indexOf(o2) < objects.indexOf(o1)) {
			Double dist = distanceMapping.get(o2).get(o1);
			if (dist == null)
				return threshold;
			else
				return dist;
		}
		return 0;
	}

	@Override
	public double[][] getDistanceMatrix() {
		int s = objects.size();
		double[][] res = new double[s][s];
		for (int i = 0; i < s; i++) {
			T o1 = objects.get(i);
			res[i][i] = 0.0;
			for (int j = i + 1; j < s; j++) {
				T o2 = objects.get(j);
				Double dist = distanceMapping.get(o1).get(o2);
				if (dist == null) {
					res[i][j] = threshold;
					res[j][i] = threshold;
				} else {
					res[i][j] = dist;
					res[j][i] = dist;
				}
			}
		}
		return res;
	}

	@Override
	public String getName() {
		return "SparseDistanceMatrix";
	}

	@Override
	protected void initializeDistanceMatrix() {
		updateMinDistance(Double.MAX_VALUE, null, null);
		updateMaxDistance(Double.MIN_VALUE, null, null);
		distanceMapping = new HashMap<>();
		for (int i = 0; i < objects.size(); i++) {
			T o1 = objects.get(i);
			distanceMapping.put(o1, new HashMap<>());
			for (int j = i + 1; j < objects.size(); j++) {
				T o2 = objects.get(j);
				double dist = distanceMeasure.getDistance(o1, o2);
				if (dist < threshold) {
					distanceMapping.get(o1).put(o2, dist);
					if (getMin() > dist)
						updateMinDistance(dist, o1, o2);
				} else
					updateMaxDistance(threshold, null, null);
			}
		}
	}

}