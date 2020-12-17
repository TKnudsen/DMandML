package com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction;

import java.util.LinkedHashMap;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVectors;
import com.github.TKnudsen.ComplexDataObject.model.transformations.dimensionalityReduction.DimensionalityReduction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.dimensionalityReduction.DimensionalityReductions;
import com.jujutsu.tsne.TSne;
import com.jujutsu.tsne.barneshut.BHTSne;
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
 * Copyright: (c) 2016-2020 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.04
 */
public class TSNE extends DimensionalityReduction<NumericalFeatureVector> {

	/**
	 * Model parameters
	 */
	private double perplexity = 20.0;

	/**
	 * 
	 */
	private int iterationsMax = 200;

	public TSNE(List<NumericalFeatureVector> featureVectors, int outputDimensionality) {
		this(featureVectors, outputDimensionality, 20.0, 200);
	}

	public TSNE(List<NumericalFeatureVector> featureVectors, int outputDimensionality, double perplexity,
			int interationsMax) {
		this.featureVectors = featureVectors;
		this.outputDimensionality = outputDimensionality;
		this.perplexity = perplexity;
		this.iterationsMax = interationsMax;
	}

	@Override
	public void calculateDimensionalityReduction() {
		if (featureVectors == null)
			throw new NullPointerException("TSNE: feature vectors null");

		mapping = new LinkedHashMap<>();

		if (featureVectors.size() == 0)
			return;

		// assess input dimensionality
		int dimensionality = 0;
		NumericalFeatureVector numericalFeatureVector = featureVectors.get(0);
		dimensionality = numericalFeatureVector.getDimensions();

		// convert to matrix (not a distance matrix!)
		double[][] inputAsDoubleMatrix = new double[featureVectors.size()][dimensionality];
		for (int r = 0; r < featureVectors.size(); r++)
			for (int c = 0; c < dimensionality; c++)
				inputAsDoubleMatrix[r][c] = featureVectors.get(r).get(c);
		inputAsDoubleMatrix = MatrixOps.centerAndScale(inputAsDoubleMatrix);

		// apply T-SNE
		TSneConfiguration config = TSneUtils.buildConfig(inputAsDoubleMatrix, outputDimensionality, dimensionality,
				perplexity, iterationsMax, true, 0.5, false);
//		TSneConfiguration config = TSneUtils.buildConfig(inputAsDoubleMatrix, outputDimensionality, dimensionality,
//				perplexity, iterationsMax);

		TSne tsne = new BHTSne();
		double[][] outputAsDoubleMatrix = tsne.tsne(config);

		// convert to low-dim FeatureVectors
		for (int i = 0; i < outputAsDoubleMatrix.length; i++) {
			NumericalFeatureVector inputFeatureVector = featureVectors.get(i);

			NumericalFeatureVector outputFeatureVector = NumericalFeatureVectors.createNumericalFeatureVector(
					outputAsDoubleMatrix[i], inputFeatureVector.getName(), inputFeatureVector.getDescription());

			DimensionalityReductions.synchronizeFeatureVectorMetadata(inputFeatureVector, outputFeatureVector);

			mapping.put(featureVectors.get(i), outputFeatureVector);
		}
	}

	public double getPerplexity() {
		return perplexity;
	}

	public void setPerplexity(double perplexity) {
		this.perplexity = perplexity;

		mapping = null;
	}

	public int getIterationsMax() {
		return iterationsMax;
	}

	public void setIterationsMax(int interationsMax) {
		this.iterationsMax = interationsMax;

		mapping = null;
	}

}
