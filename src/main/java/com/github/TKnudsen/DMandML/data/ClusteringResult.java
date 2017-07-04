package main.java.com.github.TKnudsen.DMandML.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IDObject;
import com.github.TKnudsen.ComplexDataObject.model.tools.MathFunctions;

/**
 * <p>
 * Title: ClusteringResult
 * </p>
 * 
 * <p>
 * Description: data structure for clustering results.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.02
 */
public class ClusteringResult<T extends IDObject, C extends Cluster<T>> implements IClusteringResult<T, C> {

	private long ID;

	private int hash = -1;

	List<C> clusters = new ArrayList<>();
	Map<Long, C> clusterDataStore = new HashMap<>();

	/**
	 * @param clusters
	 */
	public ClusteringResult(List<C> clusters) {
		this.clusters = new ArrayList<>(clusters);

		for (C c : clusters)
			if (c != null)
				clusterDataStore.put(c.getID(), c);

		this.ID = MathFunctions.randomLong();
		hash = -1;
	}

	@SuppressWarnings("unchecked")
	public Object clone() {
		List<C> cls = new ArrayList<C>();
		for (int i = 0; i < cls.size(); i++) {
			cls.add((C) clusters.get(i).clone());
		}
		IClusteringResult<T, C> asdf = new ClusteringResult<T, C>(cls);
		return asdf;
	}

	@Override
	public boolean containsCluster(C c) {
		if (clusterDataStore.get(c.getID()) != null)
			return true;
		return false;
	}

	/**
	 * adding a cluster means that elements already contained in other clusters
	 * are removed from the old place and only associated with the new cluster.
	 * This may require different implementations of cluster, e.g., in fuzzy
	 * clustering situations.
	 * 
	 * @param cluster
	 */
	public void addCluster(C cluster) {
		for (Cluster<T> c : clusters) {
			for (T element : cluster.getElements()) {
				c.remove(element);
			}
		}

		clusters.add(cluster);
		clusterDataStore.put(cluster.getID(), cluster);

		hash = -1;
	}

	public boolean removeCluster(C cluster) {
		if (clusters.contains(cluster)) {
			clusters.remove(cluster);
			clusterDataStore.remove(cluster.getID());

			hash = -1;
			return true;
		}
		return false;
	}

	@Override
	public List<C> getClusters() {
		return clusters;
	}

	public Map<Long, C> getClustersAsMap() {
		return clusterDataStore;
	}

	@Override
	public int getClusterIndex(T object) {
		C cluster = getCluster(object);
		if (cluster == null)
			return -1;

		for (int i = 0; i < clusters.size(); i++)
			if (clusters.get(i).equals(cluster))
				return i;

		return -1;
	}

	/**
	 * retrieves a cluster by its name.
	 * 
	 * @param name
	 * @return
	 */
	public C getCluster(String name) {
		for (int i = 0; i < this.clusters.size(); i++)
			if (this.clusters.get(i).getName().equals(name))
				return this.clusters.get(i);
		return null;
	}

	@Override
	public C getCluster(T fv) {
		if (fv == null)
			return null;

		for (int i = 0; i < this.clusters.size(); i++)
			if (this.clusters.get(i).contains(fv))
				return this.clusters.get(i);
		return null;
	}

	@Override
	public C retrieveCluster(T fv) {
		if (fv == null)
			return null;

		C c = getCluster(fv);

		if (c != null)
			return c;

		double dist = Double.POSITIVE_INFINITY - 1;
		for (C cluster : clusters) {
			double d = cluster.getCentroidDistance(fv);
			if (d < dist) {
				c = cluster;
				dist = d;
			}
		}
		return c;
	}

	@Override
	public int size() {
		return clusters.size();
	}

	@Override
	public String toString() {
		String ret = "ClusteringResult. Clusters: \n ";
		if (clusters != null)
			for (int i = 0; i < clusters.size(); i++)
				ret += clusters.get(i).toString() + "\n ";
		return ret;
	}

	@Override
	public String getName() {
		return "Cluster Result Set";
	}

	@Override
	public String getDescription() {
		return "Maintains a set of clusters. Mostly used to represent the result of a clustering.";
	}

	@Override
	public long getID() {
		return ID;
	}

	@Override
	public int hashCode() {
		if (hash == -1) {
			hash = clusters.hashCode();
		}

		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof IClusteringResult<?, ?>))
			return false;

		@SuppressWarnings("unchecked")
		IClusteringResult<?, ?> other = (IClusteringResult<?, ?>) obj;

		return clusters.equals(other.getClusters());
	}

	@Override
	public Iterator<C> iterator() {
		return clusters.iterator();
	}
}
