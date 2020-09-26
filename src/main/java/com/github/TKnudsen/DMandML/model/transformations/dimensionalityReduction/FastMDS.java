package com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ToDoubleBiFunction;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVectors;
import com.github.TKnudsen.ComplexDataObject.model.transformations.dimensionalityReduction.DimensionalityReduction;
import com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction.generic.GenericMDS;

public class FastMDS<X extends AbstractFeatureVector<?, ?>> extends DimensionalityReduction<X> {

	/**
	 * the generic MDS model used here in the context of feature vectors
	 */
	private GenericMDS<X> model;

	private double[][] output;

	public FastMDS(List<? extends X> featureVectors, ToDoubleBiFunction<? super X, ? super X> distanceMeasure,
			int outputDimensionality) {

		Objects.requireNonNull(featureVectors, "Feature vectors must not be null");
		this.featureVectors = featureVectors;

		Objects.requireNonNull(distanceMeasure, "Distance measure must not be null");
		this.distanceMeasure = distanceMeasure;

		this.outputDimensionality = outputDimensionality;
	}

	@Override
	public void calculateDimensionalityReduction() {
		if (featureVectors == null)
			throw new NullPointerException("FastMDS: feature vectors null");

		model = new GenericMDS<X>(featureVectors, distanceMeasure, outputDimensionality);
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
				X vector = featureVectors.get(i);
				NumericalFeatureVector point = NumericalFeatureVectors.createNumericalFeatureVector(output[i],
						vector.getName(), vector.getDescription());
				Iterator<String> attributeIterator = vector.iterator();
				while (attributeIterator.hasNext()) {
					String attribute = attributeIterator.next();
					point.add(attribute, vector.getAttribute(attribute));
				}
				mapping.put(vector, point);
			}
		}

		return mapping;
	}

}
