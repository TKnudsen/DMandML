package test.com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeature;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.transformations.dimensionalityReduction.IDimensionalityReduction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction.MDS;
import com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction.PCA;
import com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction.TSNE;

/**
 * <p>
 * Title: DimensionalityReductionTest
 * </p>
 * 
 * <p>
 * Description: indicates PCA, MDS, tests tSNE.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class DimensionalityReductionTest {

	public static void main(String[] args) {
		int dim = 10;
		int n = 100;

		List<NumericalFeatureVector> featureVectors = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			List<NumericalFeature> features = new ArrayList<>();
			for (int d = 0; d < dim; d++) {
				features.add(new NumericalFeature(d + "", Math.random() * d));
			}
			NumericalFeatureVector fv = new NumericalFeatureVector(features);
			featureVectors.add(fv);
		}

		IDimensionalityReduction<NumericalFeatureVector> dimRed = null;
		int outputDimensionality = 2;

		// PCA
		dimRed = new PCA(featureVectors, outputDimensionality);
		// MDS
		dimRed = new MDS<>(featureVectors, new EuclideanDistanceMeasure(), outputDimensionality);
		// tSNE
		dimRed = new TSNE(featureVectors, outputDimensionality);

		dimRed.calculateDimensionalityReduction();
		Map<NumericalFeatureVector, NumericalFeatureVector> highDimToLowDim = dimRed.getMapping();

		for (NumericalFeatureVector highDim : highDimToLowDim.keySet()) {
			System.out.println(
					"HighDim = " + highDim.getVector() + ", lowDim = " + highDimToLowDim.get(highDim).getVector());
		}
	}

}
