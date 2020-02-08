package com.github.TKnudsen.DMandML.data.classification;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.DMandML.data.cluster.Centroid;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;
import com.github.TKnudsen.DMandML.data.cluster.ClusterFactory;
import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.general.GeneralCluster;

/**
 * <p>
 * ClassificationResult utility.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2018-2020 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.04
 */
public class ClassificationResults {

	protected static <X> Map<X, LabelDistribution> createLabelDistributionMap(
			Map<X, Map<String, Double>> labelDistributionMap) {
		Map<X, LabelDistribution> tmpLabelDistributionMap = new LinkedHashMap<>();

		for (X x : labelDistributionMap.keySet()) {
			Map<String, Double> labelDistribution = labelDistributionMap.get(x);

			if (labelDistribution == null)
				throw new IllegalArgumentException(
						"ClassificationResults.createLabelDistributionMap(...): label distribution for item "
								+ x.toString() + " was null");

			tmpLabelDistributionMap.put(x, new LabelDistribution(labelDistributionMap.get(x)));
		}

		return tmpLabelDistributionMap;
	}

	/**
	 * creates a LabelDistribution for every X, containing a distribution of 100% of
	 * the label and 0% of the remaining n-1 labels of the label alphabet.
	 * 
	 * @param labels
	 * @return
	 */
	protected static <X> Map<X, LabelDistribution> createDefaultLabelDistributionMap(Map<X, String> labels) {
		if (labels == null)
			return null;

		SortedSet<String> labelAlphabet = new TreeSet<>(labels.values());

		Map<X, LabelDistribution> labelDistributionMap = new LinkedHashMap<>();
		for (X x : labels.keySet()) {
			Map<String, Double> valueDistribution = new LinkedHashMap<>();
			for (String label : labelAlphabet)
				if (label.equals(labels.get(x)))
					valueDistribution.put(label, 1.0);
				else
					valueDistribution.put(label, 0.0);
			labelDistributionMap.put(x, new LabelDistribution(valueDistribution));
		}

		return labelDistributionMap;
	}

	protected static <X> Map<X, String> createwinningLabelsMap(Map<X, LabelDistribution> labelDistributionMap) {
		Map<X, String> labelsMap = new LinkedHashMap<>();

		for (X x : labelDistributionMap.keySet())
			labelsMap.put(x, labelDistributionMap.get(x).getRepresentant());

		return labelsMap;
	}

	public static Map<String, Double[]> createCentersOfGravity(
			IClassificationResult<NumericalFeatureVector> classificationResult) {

		Map<String, Double[]> gravityMap = new LinkedHashMap<>();

		if (classificationResult == null)
			throw new IllegalArgumentException(
					"ClassificationResults.createCentersOfGravity: classification result was null.");

		if (classificationResult.getFeatureVectors().size() == 0)
			return gravityMap;

		int dimensionality = classificationResult.getFeatureVectors().iterator().next().getDimensions();

		// create vectors that will store the numeric information of the FV instances
		Set<String> classes = classificationResult.getClassDistributions().keySet();
		for (String label : classes) {
			Double[] vector = new Double[dimensionality];
			Arrays.fill(vector, 0.0);
			gravityMap.put(label, vector);
		}

		Map<String, Double> weightMap = new LinkedHashMap<>();
		for (String label : classes)
			weightMap.put(label, 0.0);

		// add vector information
		for (NumericalFeatureVector fv : classificationResult.getFeatureVectors()) {
			double[] fvVector = fv.getVector();

			for (String label : classes) {
				Double probability = classificationResult.getLabelDistribution(fv).getProbability(label);

				Double[] gravityVector = gravityMap.get(label);
				for (int d = 0; d < dimensionality; d++)
					gravityVector[d] += (fvVector[d] * probability);

				weightMap.put(label, weightMap.get(label) + probability);
			}
		}

		// build FVs representing the center-of-gravity information
		for (String label : classes) {
			Double[] gravityVector = gravityMap.get(label);
			for (int d = 0; d < dimensionality; d++)
				gravityVector[d] /= weightMap.get(label);
		}

		return gravityMap;
	}

	/**
	 * identifies the instance that is closest to the center of the class. This
	 * (copy of the) instance will be returned as the representative.
	 * 
	 * @param classificationResult
	 * @param distanceMeasure
	 * @return
	 */
	public static <FV> Map<String, FV> createCentersOfGravity(IClassificationResult<FV> classificationResult,
			IDistanceMeasure<FV> distanceMeasure) {

		Objects.requireNonNull(classificationResult);

		Map<String, FV> gravityMap = new LinkedHashMap<>();

		if (classificationResult.getFeatureVectors().size() == 0)
			return gravityMap;

		Map<String, List<FV>> classDistributions = classificationResult.getClassDistributions();
		for (String classLabel : classDistributions.keySet()) {
			List<FV> FVs = classDistributions.get(classLabel);

			String name = classLabel;
			if (name == null)
				name = "no name probided";
			Cluster<FV> cluster = new GeneralCluster<>(FVs, distanceMeasure, name, "");
			Centroid<FV> clusterRepresentant = cluster.getCentroid();

			gravityMap.put(classLabel, clusterRepresentant.getData());
		}

		return gravityMap;
	}

	/**
	 * converts a crisp classification result to a series of clusters.
	 * 
	 * @param classificationResult
	 * @param distanceMeasure
	 * @return
	 */
	public static <FV> Map<String, ICluster<FV>> toClusters(IClassificationResult<FV> classificationResult,
			IDistanceMeasure<FV> distanceMeasure) {
		Map<String, ICluster<FV>> clusters = new HashMap<String, ICluster<FV>>();
		for (String label : classificationResult.getLabelAlphabet()) {
			ICluster<FV> cluster = new ClusterFactory().createCluster(
					classificationResult.getClassDistributions().get(label), distanceMeasure, label, label);
			if (cluster != null)
				clusters.put(label, cluster);
		}

		return clusters;
	}

	/**
	 * Returns whether the given classification results are probably equal. Here,
	 * equality is characterized using the assumption that two results are equal
	 * when they return equal values from their most important methods
	 * 
	 * @param cr0 The first result
	 * @param cr1 The second result
	 * @return Whether the results are equal
	 */
	public static <FV> boolean equal(IClassificationResult<FV> cr0, IClassificationResult<FV> cr1) {
		if (cr0 == null && cr1 == null) {
			return true;
		}
		if (cr0 == null) {
			return false;
		}
		if (cr1 == null) {
			return false;
		}
		if (cr0 == cr1) {
			return true;
		}
		Collection<FV> fvs0 = cr0.getFeatureVectors();
		Collection<FV> fvs1 = cr1.getFeatureVectors();
		if (!com.google.common.base.Objects.equal(fvs0, fvs1)) {
			return false;
		}
		Set<String> la0 = cr0.getLabelAlphabet();
		Set<String> la1 = cr1.getLabelAlphabet();
		if (!com.google.common.base.Objects.equal(la0, la1)) {
			return false;
		}
		Map<String, List<FV>> cd0 = cr0.getClassDistributions();
		Map<String, List<FV>> cd1 = cr1.getClassDistributions();
		if (!com.google.common.base.Objects.equal(cd0, cd1)) {
			return false;
		}
		return true;
	}
}
