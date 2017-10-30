package com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVectorFactory;
import com.github.TKnudsen.ComplexDataObject.model.transformations.dimensionalityReduction.DimensionalityReduction;
import com.jujutsu.tsne.SimpleTSne;
import com.jujutsu.tsne.TSne;
import com.jujutsu.tsne.barneshut.TSneConfiguration;
import com.jujutsu.utils.MatrixOps;
import com.jujutsu.utils.TSneUtils;

/**
 * <p>
 * Title: TSNE
 * </p>
 * 
 * <p>
 * Description: t-sne is a dimensionality reduction technique proposed by
 * Laurens van der Maaten. The following description refers to his website:
 * https://lvdmaaten.github.io/tsne/
 * 
 * t-Distributed Stochastic Neighbor Embedding (t-SNE) is a (prize-winning)
 * technique for dimensionality reduction that is particularly well suited for
 * the visualization of high-dimensional datasets. The technique can be
 * implemented via Barnes-Hut approximations, allowing it to be applied on large
 * real-world datasets. We applied it on data sets with up to 30 million
 * examples. The technique and its variants are introduced in the following
 * papers:
 * 
 * 
 * <p>
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class TSNE extends DimensionalityReduction<Double, NumericalFeatureVector> {

	// Input data
	/**
	 * feature vectors for the model creation and dimensionality reduction
	 */
	private List<NumericalFeatureVector> featureVectors;

	// Model parameters
	/**
	 * 
	 */
	private double perplexity = 20.0;

	/**
	 * 
	 */
	private int interationsMax = 100;

	public TSNE(List<NumericalFeatureVector> featureVectors) {
		this(featureVectors, 20.0, 100);
	}

	public TSNE(List<NumericalFeatureVector> featureVectors, double perplexity, int interationsMax) {
		this.featureVectors = featureVectors;
		this.perplexity = perplexity;
		this.interationsMax = interationsMax;
	}

	@Override
	public void calculateDimensionalityReduction() {
		if (featureVectors == null)
			throw new NullPointerException("TSNE: feature vectors null");

		mapping = new HashMap<>();

		if (featureVectors.size() == 0)
			return;

		// assess input dimensionality
		int dimensionality = 0;
		NumericalFeatureVector numericalFeatureVector = featureVectors.get(0);
		dimensionality = numericalFeatureVector.getDimensions();

		// convert to matix (not a distance matrix!)
		double[][] inputAsDoubleMatrix = new double[featureVectors.size()][dimensionality];
		for (int r = 0; r < featureVectors.size(); r++)
			for (int c = 0; c < dimensionality; c++)
				inputAsDoubleMatrix[r][c] = featureVectors.get(r).get(c);
		inputAsDoubleMatrix = MatrixOps.centerAndScale(inputAsDoubleMatrix);

		// apply T-SNE
		TSne tsne = new SimpleTSne();
		TSneConfiguration config = TSneUtils.buildConfig(inputAsDoubleMatrix, outputDimensionality, dimensionality, perplexity, interationsMax);
		double[][] outputAsDoubleMatrix = tsne.tsne(config);

		// convert to low-dim FeatureVectors
		for (int i = 0; i < outputAsDoubleMatrix.length; i++) {
			NumericalFeatureVector inputFeatureVector = featureVectors.get(i);

			double[] outputVector = outputAsDoubleMatrix[i];
			NumericalFeatureVector fv = NumericalFeatureVectorFactory.createNumericalFeatureVector(outputVector, inputFeatureVector.getName(), inputFeatureVector.getDescription());
			Iterator<String> attributeIterator = featureVectors.get(i).iterator();
			while (attributeIterator.hasNext()) {
				String attribute = attributeIterator.next();
				fv.add(attribute, featureVectors.get(i).getAttribute(attribute));
			}

			mapping.put(featureVectors.get(i), fv);
		}
	}

	public double getPerplexity() {
		return perplexity;
	}

	public void setPerplexity(double perplexity) {
		this.perplexity = perplexity;

		mapping = null;
	}

	public int getInterationsMax() {
		return interationsMax;
	}

	public void setInterationsMax(int interationsMax) {
		this.interationsMax = interationsMax;

		mapping = null;
	}

}
