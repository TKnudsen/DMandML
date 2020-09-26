package com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVectors;
import com.github.TKnudsen.ComplexDataObject.model.transformations.dimensionalityReduction.DimensionalityReduction;

import de.javagl.tsne.Tsne;

public class BhTSNE<X extends AbstractFeatureVector<?, ?>> extends DimensionalityReduction<X> {

	private Tsne tsne;

	public BhTSNE(List<? extends X> featureVectors, ToDoubleBiFunction<? super X, ? super X> distanceMeasure,
			int outputDimensionality) {

		Objects.requireNonNull(featureVectors, "Feature vectors must not be null");
		this.featureVectors = featureVectors;

		Objects.requireNonNull(distanceMeasure, "Distance measure must not be null");
		this.distanceMeasure = distanceMeasure;

		tsne = new Tsne();
		tsne.setOutputDims(outputDimensionality);
	}

	@Override
	public void calculateDimensionalityReduction() {
		if (featureVectors == null)
			throw new NullPointerException("BhTSNE: feature vectors null");

		@SuppressWarnings("unchecked")
		double[][] input = NumericalFeatureVectors
				.createMatrixRepresentation((List<? extends NumericalFeatureVector>) featureVectors);
		double[][] Y = tsne.run(input);

		System.out.println(Arrays.toString(Y[0]));
		System.out.println(Arrays.toString(Y[1]));
		System.out.println(Arrays.toString(Y[2]));
	}

	/**
	 * Set the perplexity. <br>
	 * <br>
	 * According to the t-SNE FAQ, this can be seen as a measure for the number of
	 * effective nearest neighbors. It should be larger for larger/denser data sets.
	 * The typical value range is between 5.0 and 50.0.<br>
	 * <br>
	 * Note that <code>numberOfInputPoints - 1 &gt; 3 * perplexity</code> must hold,
	 * otherwise, the underlying implementation will throw an exception.
	 * 
	 * @param perplexity The perplexity
	 */
	public void setPerplexity(double perplexity) {
		this.tsne.setPerplexity(perplexity);
	}

	/**
	 * Returns the perplexity. See {@link #setPerplexity(double)} for details.
	 * 
	 * @return The perplexity.
	 */
	public double getPerplexity() {
		return tsne.getPerplexity();
	}

	/**
	 * Set this theta parameter. Don't do this if you don't know what it does.
	 * 
	 * @param theta The theta parameter.
	 * @throws IllegalArgumentException If the given number is not positive
	 */
	public void setTheta(double theta) {
		this.tsne.setTheta(theta);
	}

	/**
	 * Returns the theta parameter. See {@link #setTheta(double)} for details.
	 * 
	 * @return The theta parameter
	 */
	public double getTheta() {
		return this.tsne.getTheta();
	}
}
