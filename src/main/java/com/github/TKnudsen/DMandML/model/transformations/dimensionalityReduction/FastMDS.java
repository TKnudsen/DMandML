package com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVectors;
import com.github.TKnudsen.ComplexDataObject.model.transformations.dimensionalityReduction.DimensionalityReduction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.dimensionalityReduction.DimensionalityReductions;
import com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction.generic.GenericMDS;

public class FastMDS<X extends AbstractFeatureVector<?, ?>> extends DimensionalityReduction<X> {

	/**
	 * the generic MDS model used here in the context of feature vectors
	 */
	private GenericMDS<X> model;

	private double[][] output;

	private final int maxIterations;

	public FastMDS(List<? extends X> featureVectors, ToDoubleBiFunction<? super X, ? super X> distanceMeasure,
			int outputDimensionality) {
		this(featureVectors, distanceMeasure, outputDimensionality, 1000);
	}

	public FastMDS(List<? extends X> featureVectors, ToDoubleBiFunction<? super X, ? super X> distanceMeasure,
			int outputDimensionality, int maxIterations) {

		Objects.requireNonNull(featureVectors, "Feature vectors must not be null");
		this.featureVectors = featureVectors;

		Objects.requireNonNull(distanceMeasure, "Distance measure must not be null");
		this.distanceMeasure = distanceMeasure;

		this.outputDimensionality = outputDimensionality;
		this.maxIterations = maxIterations;
	}

	@Override
	public void calculateDimensionalityReduction() {
		if (featureVectors == null)
			throw new NullPointerException("FastMDS: feature vectors null");

		model = new GenericMDS<X>(featureVectors, distanceMeasure, outputDimensionality, maxIterations);
		output = model.compute();

		mapping = null;
	}

	@Override
	public Map<X, NumericalFeatureVector> getMapping() {
		if (output == null)
			throw new NullPointerException("Output is null, calculate the dimensionality reduction first");

		if (mapping == null) {
			mapping = new LinkedHashMap<X, NumericalFeatureVector>();
			for (int i = 0; i < featureVectors.size(); i++) {
				X inputFeatureVector = featureVectors.get(i);
				NumericalFeatureVector outputFeatureVector = NumericalFeatureVectors.createNumericalFeatureVector(
						output[i], inputFeatureVector.getName(), inputFeatureVector.getDescription());

				DimensionalityReductions.synchronizeFeatureVectorMetadata(inputFeatureVector, outputFeatureVector);

				mapping.put(inputFeatureVector, outputFeatureVector);
			}
		}

		return mapping;
	}

}
