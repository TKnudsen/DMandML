package main.java.com.github.TKnudsen.DMandML.data;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;

public class ClusterTools {

	/**
	 * converts the set of elements into a list. Just for convenient reasons.
	 * 
	 * @param cluster
	 * @return
	 */
	public static <T extends IDObject> List<T> getElementList(Cluster<T> cluster) {
		return new ArrayList<>(cluster.getElements());
	}
}
