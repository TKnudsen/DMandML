package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering;

import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores of elements on the basis of
 * {@link IClusteringResult}s.
 * </p>
 * 
 * Partitions a set of instances into disjoint groups or clusters C1,...,Cn of
 * similar instances. Clustering provides a meta-structure on the original data
 * and is a common building block for several degree-of-interest functions where
 * it facilitates the priorization of instances at e.g. cluster centroids,
 * cluster border areas, or at spatially close clusters.
 * </p>
 * 
 * Clustering (CLU) DOI/building block published in: Juergen Bernard, Matthias
 * Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair: Towards
 * User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.03
 */
public abstract class ClusteringBasedDegreeOfInterestingnessFunction<FV>
		implements IClusteringBasedDegreeOfInterestingnessFunction<FV> {

	private final IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult;

	private boolean retrieveNearestClusterForUnassignedElements = true;

	public ClusteringBasedDegreeOfInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult) {
		this(clusteringResult, true);
	}

	public ClusteringBasedDegreeOfInterestingnessFunction(
			IClusteringResult<FV, ? extends ICluster<FV>> clusteringResult,
			boolean retrieveNearestClusterForUnassignedElements) {

		Objects.requireNonNull(clusteringResult);
		this.clusteringResult = clusteringResult;
		this.retrieveNearestClusterForUnassignedElements = retrieveNearestClusterForUnassignedElements;
	}

	@Override
	public Map<FV, Double> apply(List<? extends FV> featureVectors) {

		Objects.requireNonNull(featureVectors);

		if (getClusteringResult().size() == 0)
			throw new IllegalArgumentException(
					getName() + ": cannot calculate clustering-based DOI with an empty clustering result.");

		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();

		if (featureVectors.size() == 0)
			return interestingnessScores;

		for (FV fv : featureVectors)
			interestingnessScores.put(fv, calculateInterestingnessScore(fv));

		NormalizationFunction normalizationFunction = new LinearNormalizationFunction(interestingnessScores.values());
		for (FV fv : interestingnessScores.keySet())
			interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

		return interestingnessScores;
	}

	protected abstract Double calculateInterestingnessScore(FV featureVector);

	@Override
	public IClusteringResult<FV, ? extends ICluster<FV>> getClusteringResult() {
		return clusteringResult;
	}

	@Override
	public boolean isRetrieveNearestClusterForUnassignedElements() {
		return retrieveNearestClusterForUnassignedElements;
	}

	@Override
	public void setRetrieveNearestClusterForUnassignedElements(boolean retrieveNearestClusterForUnassignedElements) {
		this.retrieveNearestClusterForUnassignedElements = retrieveNearestClusterForUnassignedElements;
	}

}
