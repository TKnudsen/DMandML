package com.github.TKnudsen.DMandML.data.clustering;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.DMandML.data.cluster.ClusteringResult;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;

/**
 * <p>
 * ClusterResult with an additional lookup of clusters for the baseline
 * elements.
 * </p>
 *
 * @version 1.02
 * @since 2017
 */
public class ClusterResultWithClusterLookupSupport<T, C extends ICluster<T>> extends ClusteringResult<T, C> {

	private Map<T, C> clusterLookup;

	public ClusterResultWithClusterLookupSupport(List<C> clusters) {
		this(clusters, null);
	}

	public ClusterResultWithClusterLookupSupport(List<C> clusters, String name) {
		super(clusters, name);

		initializeClusterLookup();
	}

	public ClusterResultWithClusterLookupSupport(IClusteringResult<T, ? extends ICluster<T>> clusteringResult) {
		this(clusteringResult, null);
	}

	@SuppressWarnings("unchecked")
	public ClusterResultWithClusterLookupSupport(IClusteringResult<T, ? extends ICluster<T>> clusteringResult,
			String name) {
		super((List<? extends C>) clusteringResult.getClusters(), name);

		initializeClusterLookup();
	}

	private void initializeClusterLookup() {
		clusterLookup = new HashMap<>();

		for (C c : getClusters())
			for (T t : c.getElements())
				clusterLookup.put(t, c);
	}

	@Override
	public C getCluster(T t) {
		if (t == null)
			return null;

		return clusterLookup.get(t);
	}

}
