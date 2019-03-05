package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.committeeResults;

import com.github.TKnudsen.ComplexDataObject.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.ComplexDataObject.model.tools.MathFunctions;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.MapUtils;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering.IClusteringBasedDegreeOfInterestingnessFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores of elements on the basis of their distances
 * to the centroids of pre-defined {@link IClusteringResult}. Uses an ensemble
 * of multiple clustering results, which will be fed into individual DOIs for
 * single {@link IClusteringResult}s.
 * </p>
 * 
 * @version 1.01
 */
public abstract class ClusteringCommitteeBasedInterestingnessFunction<FV> implements IDegreeOfInterestFunction<FV> {

	private final Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults;

	private boolean retrieveNearestClusterForUnassignedElements = true;

	private Collection<IClusteringBasedDegreeOfInterestingnessFunction<FV>> clusteringResultDOIs = null;

	public ClusteringCommitteeBasedInterestingnessFunction(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults) {
		this(clusteringResults, true);
	}

	public ClusteringCommitteeBasedInterestingnessFunction(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults,
			boolean retrieveNearestClusterForUnassignedElements) {

		Objects.requireNonNull(clusteringResults);
		this.clusteringResults = clusteringResults;
		this.retrieveNearestClusterForUnassignedElements = retrieveNearestClusterForUnassignedElements;
	}

	protected abstract Collection<IClusteringBasedDegreeOfInterestingnessFunction<FV>> createClusteringBasedDOIs(
			Collection<IClusteringResult<FV, ? extends ICluster<FV>>> clusteringResults);

	@Override
	public Map<FV, Double> apply(List<? extends FV> featureVectors) {

		// create dois once when apply is first called, s.t. it has not to be
		// done in the constructor
		if (clusteringResultDOIs == null)
			this.clusteringResultDOIs = createClusteringBasedDOIs(clusteringResults);

		Objects.requireNonNull(featureVectors);

		LinkedHashMap<FV, List<Double>> fvScoresLists = new LinkedHashMap<>();
		LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();

		if (featureVectors.size() == 0)
			return interestingnessScores;

		for (IClusteringBasedDegreeOfInterestingnessFunction<FV> doi : clusteringResultDOIs) {
			Map<FV, Double> doiScores = doi.apply(featureVectors);

			for (FV fv : featureVectors) {
				if (fvScoresLists.get(fv) == null)
					fvScoresLists.put(fv, new ArrayList<>());

				fvScoresLists.get(fv).add(doiScores.get(fv));
			}
		}

		for (FV fv : fvScoresLists.keySet())
			interestingnessScores.put(fv, MathFunctions.getMean(fvScoresLists.get(fv)));

		// for validation purposes
		MapUtils.checkForCriticalValue(interestingnessScores, null, true);
		MapUtils.checkForCriticalValue(interestingnessScores, Double.NaN, true);
		MapUtils.checkForCriticalValue(interestingnessScores, Double.NEGATIVE_INFINITY, true);
		MapUtils.checkForCriticalValue(interestingnessScores, Double.POSITIVE_INFINITY, true);

		NormalizationFunction normalizationFunction = new LinearNormalizationFunction(interestingnessScores.values());
		for (FV fv : interestingnessScores.keySet())
			interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

		return interestingnessScores;
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String getDescription() {
		return "conflates interestingnes scores of individual clustering-based DOIs.";
	}

	public Collection<IClusteringResult<FV, ? extends ICluster<FV>>> getClusteringResults() {
		return clusteringResults;
	}

	public boolean isRetrieveNearestClusterForUnassignedElements() {
		return retrieveNearestClusterForUnassignedElements;
	}

	public void setRetrieveNearestClusterForUnassignedElements(boolean retrieveNearestClusterForUnassignedElements) {
		this.retrieveNearestClusterForUnassignedElements = retrieveNearestClusterForUnassignedElements;
	}

}
