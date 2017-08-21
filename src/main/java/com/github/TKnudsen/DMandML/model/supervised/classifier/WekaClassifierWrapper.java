package main.java.com.github.TKnudsen.DMandML.model.supervised.classifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;
import com.github.TKnudsen.ComplexDataObject.model.tools.IndexTools;
import com.github.TKnudsen.ComplexDataObject.model.tools.WekaConversion;

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
 * Copyright: (c) 2016-2017 J�rgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.05
 */
public abstract class WekaClassifierWrapper<O extends Object, FV extends AbstractFeatureVector<O, ? extends Feature<O>>> extends Classifier<O, FV> {

	protected weka.classifiers.Classifier wekaClassifier;

	@JsonIgnore
	protected Instances trainData;

	@JsonIgnore
	protected List<Map<String, Double>> labelDistributionResult;

	@JsonIgnore
	protected Map<FV, Map<String, Double>> labelDistributionMap;

	public WekaClassifierWrapper() {
	}

	public WekaClassifierWrapper(weka.classifiers.Classifier wekaClassifier) {
		this.wekaClassifier = wekaClassifier;
	}

	@Override
	public Map<String, Double> getLabelDistribution(FV featureVector) {
		if (labelDistributionMap == null) {
			System.err.println("WekaClassifierWrapper: no model created yet");
			return null;
		}
		if (labelDistributionMap.get(featureVector) == null) {
			List<FV> lst = new ArrayList<>();
			test(lst);
		}

		return labelDistributionMap.get(featureVector);
	}

	@Override
	public List<Map<String, Double>> getLabelDistributionResult() {
		return labelDistributionResult;
	}

	@Override
	public List<String> test(List<FV> featureVectors) {
		if (featureVectors == null)
			throw new NullPointerException();

		List<String> labels = new ArrayList<String>();
		if (labelAlphabet == null || labelAlphabet.size() < 2)
			return labels;

		if (featureVectors.size() == 0)
			return labels;

		Instances testData = WekaConversion.getInstances(featureVectors, false);
		if (testData.size() != featureVectors.size())
			throw new IllegalArgumentException("WekaConversion failed");

		testData = WekaConversion.addLabelAttributeToInstance(testData, "class", labelAlphabet);
		if (testData.size() != testData.size())
			throw new IllegalArgumentException("WekaConversion failed");

		for (int i = 0; i < testData.numInstances(); i++) {

			double[] dist = null;
			try {
				dist = wekaClassifier.distributionForInstance(testData.instance(i));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			labels.add(trainData.classAttribute().value(IndexTools.getMaxIndex(dist)));

			Map<String, Double> labelDistribution = new HashMap<String, Double>();
			for (int j = 0; j < dist.length; j++)
				labelDistribution.put(trainData.classAttribute().value(j), dist[j]);

			labelDistributionResult.add(labelDistribution);
			labelDistributionMap.put(featureVectors.get(i), labelDistribution);
		}

		return labels;
	}

	@Override
	protected void prepareData() {
		trainData = WekaConversion.getLabeledInstances(trainFeatureVectors, classAttribute, true);
	}

	@Override
	protected void buildClassifier() {
		labelDistributionResult = new ArrayList<Map<String, Double>>();
		labelDistributionMap = new HashMap<>();

		if (trainData == null)
			throw new NullPointerException("training data null");
		if (trainData.size() < 2)
			throw new IllegalArgumentException("at least two training instances required");

		try {
			wekaClassifier.buildClassifier(trainData);
		} catch (Exception e) {
			System.err.println("AbstractWekaClassifier: inherited classifier ->" + getName() + "<- sent an exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	protected void resetResults() {
		labelDistributionResult = new ArrayList<Map<String, Double>>();
		labelDistributionMap = new HashMap<>();
	}

	public weka.classifiers.Classifier getWekaClassifier() {
		return wekaClassifier;
	}

	public void setWekaClassifier(weka.classifiers.Classifier wekaClassifier) {
		this.wekaClassifier = wekaClassifier;

		resetResults();
	}
}
