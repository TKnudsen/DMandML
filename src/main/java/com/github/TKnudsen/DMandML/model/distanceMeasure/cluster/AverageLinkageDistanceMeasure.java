package com.github.TKnudsen.DMandML.model.distanceMeasure.cluster;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;

/**
 * <p>
 * Title: AverageLinkageDistanceMeasure
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
 * @version 1.02
 */
public class AverageLinkageDistanceMeasure<T extends IDObject> extends ClusterDistanceMeasure<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1578903279068956264L;

	public AverageLinkageDistanceMeasure(IDistanceMeasure<T> distanceMeasure) {
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
	public double getDistance(Cluster<T> c1, Cluster<T> c2) {
		double distance = Double.MAX_VALUE;

		if (c1 == null || c2 == null)
			return distance;

		for (T fv1 : c1.getElements())
			for (T fv2 : c2.getElements())
				distance += getDistanceMeasure().getDistance(fv1, fv2);

		distance = distance / (c1.getElements().size() * c2.getElements().size());

		return distance;
	}
}
