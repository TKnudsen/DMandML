package com.github.TKnudsen.DMandML.model.distanceMeasure.Double;

import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.Double.DoubleDistanceMeasure;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

public class AngularDistance extends DoubleDistanceMeasure {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5013586463338851442L;

	private net.sf.javaml.distance.AngularDistance ad = new net.sf.javaml.distance.AngularDistance();

	@Override
	public double getDistance(double[] o1, double[] o2) {
		if (o1 == null || o2 == null)
			throw new NullPointerException(getName() + ": given array was null");

		Instance instance = new DenseInstance(o1);
		Instance instance2 = new DenseInstance(o2);

		return ad.measure(instance, instance2);
	}

	@Override
	public String getName() {
		return "Angular Distance";
	}

}
