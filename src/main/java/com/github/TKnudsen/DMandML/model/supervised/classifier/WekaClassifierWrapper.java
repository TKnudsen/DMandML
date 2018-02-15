package com.github.TKnudsen.DMandML.model.supervised.classifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.IFeatureVectorObject;
import com.github.TKnudsen.ComplexDataObject.model.tools.WekaConversion;

import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * Title: WekaClassifierWrapper
 * </p>
 * 
 * <p>
 * Description: Abstract baseline class for wrapping WEKA classifiers. Thus,
 * inherited WEKA classifiers will only require a few lines of code.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.06
 * 
 */
public abstract class WekaClassifierWrapper<FV extends IFeatureVectorObject<?, ?>> extends Classifier<FV> {

	private static final String CLASS_ATTRIBUTE_NAME = "class";

	private weka.classifiers.Classifier wekaClassifier;

	@JsonIgnore
	private final Map<FV, Map<String, Double>> labelDistributionMap;

	public WekaClassifierWrapper() {
		super(CLASS_ATTRIBUTE_NAME);
		this.labelDistributionMap = new LinkedHashMap<>();
		initializeClassifier();
	}

	/**
	 * Initialize the classifier. This will be called once in the constructor,
	 * and should be implemented by subclasses so that the
	 * {@link #wekaClassifier} is assigned and initialized
	 */
	protected abstract void initializeClassifier();

	public void trainWithWeights(List<FV> featureVectors, double[] weights) {
		if (featureVectors == null)
			throw new NullPointerException();

		updateLabelAlphabet(featureVectors);
		buildClassifier(featureVectors, weights);
	}

	private void buildClassifier(List<FV> featureVectors, double weights[]) {
		Instances trainData = WekaConversion.getLabeledInstances(featureVectors, getClassAttribute(), true);
		for (int i = 0; i < trainData.size(); i++) {
			trainData.get(i).setWeight(weights[i]);
		}
		buildClassifier(trainData);
	}

	@Override
	public Map<String, Double> getLabelDistribution(FV featureVector) {
		
		if (getLabelAlphabet().isEmpty()) {
			System.err.println("WekaClassifierWrapper: No training was performed. Returning empty label distribution");
			return Collections.emptyMap();
		}
		
		if (labelDistributionMap.get(featureVector) == null) {
			computeLabelDistributions(Collections.singletonList(featureVector));
		}
		return labelDistributionMap.get(featureVector);
	}

	@Override
	public final List<String> test(List<FV> featureVectors) {
		if (featureVectors == null)
			throw new NullPointerException();

		Set<FV> featureVectorsSet = new LinkedHashSet<>(featureVectors);
		featureVectorsSet.removeAll(labelDistributionMap.keySet());
		List<FV> newFeatureVectors = new ArrayList<>(featureVectorsSet);
		computeLabelDistributions(newFeatureVectors);

		List<String> labels = new ArrayList<String>();
		for (FV fv : featureVectors) {
			Map<String, Double> labelDistribution = getLabelDistribution(fv);
			Entry<String, Double> entryWithHighestProbability = Collections.max(labelDistribution.entrySet(),
					Map.Entry.comparingByValue());
			String label = entryWithHighestProbability.getKey();
			labels.add(label);
		}
		return labels;
	}

	private void computeLabelDistributions(List<FV> featureVectors) {
		Instances testData = WekaConversion.getInstances(featureVectors, false);

		// TODO This should never happen! Why COULD it happen?
		// (In doubt, WekaConversion.getInstances should throw!)
		if (testData.size() != featureVectors.size())
			throw new IllegalArgumentException("WekaConversion failed");

		List<String> labelAlphabet = getLabelAlphabet();
		WekaConversion.addLabelAttributeToInstance(testData, CLASS_ATTRIBUTE_NAME, labelAlphabet);

		for (int i = 0; i < testData.numInstances(); i++) {

			Instance instance = testData.instance(i);
			double[] dist = null;
			try {
				dist = wekaClassifier.distributionForInstance(instance);
			} catch (Exception e) {
				//e.printStackTrace();
				
				System.out.println("Weka classifier could not classify instance, using NaN results");
				//printDetailedWekaDebugInfo(instance, e);
				
				dist = new double[getLabelAlphabet().size()];
				Arrays.fill(dist, Double.NaN);
			}

			Map<String, Double> labelDistribution = new HashMap<String, Double>();
			for (int j = 0; j < dist.length; j++) {
				String label = labelAlphabet.get(j);
				labelDistribution.put(label, dist[j]);
			}
			labelDistributionMap.put(featureVectors.get(i), labelDistribution);
		}

	}
	
	private void printDetailedWekaDebugInfo(Instance instance, Throwable e)
	{
		System.out.println("Weka classifier could not classify instance: " + e.getMessage());
		System.out.println("  Classifier: " + wekaClassifier);
		System.out.println("  Instance  : " + instance);
		List<String> labelAlphabet = getLabelAlphabet();
		System.out.println("  Label alphabet "+labelAlphabet);
		System.out.println("  Instance class attribute values:");
		for (int j=0; j<labelAlphabet.size(); j++)
		{
			System.out.println("    "+instance.dataset().classAttribute().value(j));
		}
		System.out.println("  Using NaN results");
	}

	@Override
	protected void buildClassifier(List<FV> featureVectors) {
		Instances trainData = WekaConversion.getLabeledInstances(featureVectors, getClassAttribute(), true);
		buildClassifier(trainData);
	}

	private void buildClassifier(Instances trainData) {
		labelDistributionMap.clear();

		if (trainData.size() < 2)
			throw new IllegalArgumentException("at least two training instances required");

		if (trainData.classAttribute().numValues() == 1) {
			System.err.println("Training data contains only a single class. Not applying " + wekaClassifier);
		} else {
			try {
				wekaClassifier.buildClassifier(trainData);
			} catch (Exception e) {
				System.err.println("AbstractWekaClassifier: inherited classifier ->" + getName()
						+ "<- sent an exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// This is public ONLY for serialization
	public final weka.classifiers.Classifier getWekaClassifier() {
		return wekaClassifier;
	}

	// This is public ONLY for serialization. Usually, this should
	// ONLY be called by subclasses, in #initializeClassifier !!!
	public final void setWekaClassifier(weka.classifiers.Classifier wekaClassifier) {
		this.wekaClassifier = wekaClassifier;
	}
}
