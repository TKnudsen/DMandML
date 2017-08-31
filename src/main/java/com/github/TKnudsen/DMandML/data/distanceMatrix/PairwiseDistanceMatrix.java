package main.java.com.github.TKnudsen.DMandML.data.distanceMatrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.IDistanceMatrix;

/**
 * <p>
 * Title: PairwiseDistanceMatrix
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author Christian Ritter
 * @version 1.02
 */
public class PairwiseDistanceMatrix<T> implements IDistanceMatrix<T> {

	private double[][] distanceMatrix;
	private Map<T, Integer> objectMapping;
	private List<T> elements;
	private double minDistance;
	private double maxDistance;

	public PairwiseDistanceMatrix(List<T> objects, double[][] pairwiseDistances) {
		this.distanceMatrix = pairwiseDistances;
		this.elements = objects;
		initializeObjectMapping();
		calculateMinMaxDistance();
	}

	private void calculateMinMaxDistance() {
		minDistance = Double.MAX_VALUE;
		maxDistance = Double.MIN_VALUE;
		for (int i = 0; i < distanceMatrix.length; i++) {
			for (int j = i; j < distanceMatrix.length; j++) {
				minDistance = Math.min(minDistance, distanceMatrix[i][j]);
				maxDistance = Math.max(maxDistance, distanceMatrix[i][j]);
			}
		}
	}

	private void initializeObjectMapping() {
		objectMapping = new HashMap<>();
		int i = 0;
		for (T t : getElements()) {
			objectMapping.put(t, i++);
		}
	}

	@Override
	public double getDistance(T o1, T o2) {
		Integer i = objectMapping.get(o1);
		Integer j = objectMapping.get(o2);
		if (i == null || j == null) {
			return Double.NaN;
		}
		return getDistanceMatrix()[i][j];
	}

	@Override
	public double applyAsDouble(T t, T u) {
		return getDistance(t, u);
	}

	@Override
	public String getName() {
		return "PairwiseDistanceMatrix";
	}

	@Override
	public String getDescription() {
		return "IDistanceMatrix Wrapper for pairwise distances represented as 2D double array";
	}

	@Override
	public double[][] getDistanceMatrix() {
		return distanceMatrix;
	}

	@Override
	public List<T> getElements() {
		return elements;
	}

	@Override
	public double getMinDistance() {
		return minDistance;
	}

	@Override
	public double getMaxDistance() {
		return maxDistance;
	}

}
