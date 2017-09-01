package main.java.com.github.TKnudsen.DMandML.data.distanceMatrix;

import java.util.HashMap;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.distanceMatrix.DistanceMatrix;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;

import main.java.com.github.TKnudsen.DMandML.data.cluster.Cluster;
import main.java.com.github.TKnudsen.DMandML.data.cluster.numerical.NumericalFeatureVectorClusterResult;
import main.java.com.github.TKnudsen.DMandML.data.clustering.ClusterResultWithClusterLookupSupport;
import main.java.com.github.TKnudsen.DMandML.model.unsupervised.clustering.impl.KMeans;

/**
 * <p>
 * Title: AggregationBasedDistanceMatrix
 * </p>
 * 
 * <p>
 * Description: Distance matrix that uses aggregation to avoid space allocation
 * problems. The aggregation level is a user parameter.
 * 
 * Please note that aggregation reduces the precision of distance computation.
 * Depending on the aggregation level the distance results can be inaccurate by
 * several percent.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class AggregationBasedDistanceMatrix extends DistanceMatrix<NumericalFeatureVector> {

	private int aggregationLevel = 1000;
	KMeans kmeans;

	ClusterResultWithClusterLookupSupport<NumericalFeatureVector, Cluster<NumericalFeatureVector>> clusteringResult;

	public AggregationBasedDistanceMatrix(List<NumericalFeatureVector> objects, IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {
		super(objects, distanceMeasure);
	}

	public AggregationBasedDistanceMatrix(List<NumericalFeatureVector> objects, IDistanceMeasure<NumericalFeatureVector> distanceMeasure, int aggregationLevel) {
		super(objects, distanceMeasure);

		throw new IllegalArgumentException("AggregationBasedDistanceMatrix: design pitfall. initialize is executed before aggregationLevel is set.");

		// this.aggregationLevel = aggregationLevel;
	}

	protected void aggregateData() {
		kmeans = new KMeans(aggregationLevel);
		kmeans.setFeatureVectors((List<NumericalFeatureVector>) getElements());

		kmeans.calculateClustering();

		NumericalFeatureVectorClusterResult clusterResultSet = kmeans.getClusterResultSet();
		this.clusteringResult = new ClusterResultWithClusterLookupSupport(clusterResultSet.getClusters());
	}

	protected ClusterResultWithClusterLookupSupport<NumericalFeatureVector, Cluster<NumericalFeatureVector>> getClusterResult() {
		if (clusteringResult == null)
			aggregateData();

		return clusteringResult;
	}

	protected NumericalFeatureVector getElementAggregate(NumericalFeatureVector t) {
		// make sure that no cluster element is queried.
		// if so a cluster is the lookup of itself.
		if (objectIndex.containsKey(t))
			return t;

		ClusterResultWithClusterLookupSupport<NumericalFeatureVector, Cluster<NumericalFeatureVector>> clusterResult = getClusterResult();
		Cluster<NumericalFeatureVector> cluster = clusterResult.getCluster(t);
		return cluster.getCentroid().getData();
	}

	@Override
	protected void initializeObjectIndex() {
		// create index
		objectIndex = new HashMap<>();

		ClusterResultWithClusterLookupSupport<NumericalFeatureVector, Cluster<NumericalFeatureVector>> clusterResult = getClusterResult();
		for (int i = 0; i < clusterResult.getClusters().size(); i++) {
			NumericalFeatureVector t = clusterResult.getClusters().get(i).getCentroid().getData();
			objectIndex.put(t, i);
		}
	}

	@Override
	protected Integer getObjectIndex(NumericalFeatureVector object) {
		if (objectIndex == null)
			initializeDistanceMatrix();

		return objectIndex.get(getElementAggregate(object));
	}

	public int getAggregationLevel() {
		return aggregationLevel;
	}

	public void setAggregationLevel(int aggregationLevel) {
		this.aggregationLevel = aggregationLevel;

		kmeans = null;
		clusteringResult = null;

		initializeDistanceMatrix();
	}

}
