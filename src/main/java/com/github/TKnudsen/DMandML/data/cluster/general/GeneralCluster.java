package com.github.TKnudsen.DMandML.data.cluster.general;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;

public class GeneralCluster extends Cluster<IDObject> {

	public GeneralCluster(Collection<? extends IDObject> elements, IDistanceMeasure<IDObject> distanceMeasure, String name, String description) {
		super(elements, distanceMeasure, name, description);
	}

	@Override
	public double getVariance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cluster<IDObject> clone() {
		return new GeneralCluster(new LinkedHashSet<>(getElements()), distanceMeasure, getName(), getDescription());
	}

}
