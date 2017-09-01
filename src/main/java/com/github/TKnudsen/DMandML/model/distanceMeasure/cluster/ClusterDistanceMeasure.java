package main.java.com.github.TKnudsen.DMandML.model.distanceMeasure.cluster;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IIDObjectDistanceMeasure;

import main.java.com.github.TKnudsen.DMandML.data.cluster.Cluster;

/**
 * <p>
 * Title: ClusterDistanceMeasure
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.03
 */
public abstract class ClusterDistanceMeasure<T extends IDObject> implements IIDObjectDistanceMeasure<Cluster<T>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5464839439359706042L;

	private IDistanceMeasure<T> distanceMeasure;

	public ClusterDistanceMeasure(IDistanceMeasure<T> distanceMeasure) {
		this.distanceMeasure = distanceMeasure;
	}

	@Override
	public double applyAsDouble(Cluster<T> t, Cluster<T> u) {
		return getDistance(t, u);
	}

	public IDistanceMeasure<T> getDistanceMeasure() {
		return distanceMeasure;
	}

	public void setDistanceMeasure(IDistanceMeasure<T> distanceMeasure) {
		this.distanceMeasure = distanceMeasure;
	}
}
