package com.github.TKnudsen.DMandML.model.distanceMeasure.Double;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.Double.DoubleDistanceMeasure;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

public class CosineDistance extends DoubleDistanceMeasure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1112922341874963975L;

	private net.sf.javaml.distance.CosineDistance cd = new net.sf.javaml.distance.CosineDistance();

	@Override
	public double getDistance(double[] o1, double[] o2) {
		if (o1 == null || o2 == null)
			throw new NullPointerException(getName() + ": given array was null");

		Instance instance = new DenseInstance(o1);
		Instance instance2 = new DenseInstance(o2);

		return cd.measure(instance, instance2);
	}

	@Override
	public String getName() {
		return "Cosine Distance";
	}

}
