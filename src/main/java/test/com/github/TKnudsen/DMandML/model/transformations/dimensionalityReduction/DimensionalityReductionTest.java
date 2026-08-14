package test.com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.Features;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeature;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.transformations.dimensionalityReduction.IDimensionalityReduction;
import com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction.FastMDS;

/**
 * <p>
 * indicates PCA, MDS, tests tSNE.
 * </p>
 *
 * @version 1.01
 * @since 2016
 */
public class DimensionalityReductionTest {

	public static void main(String[] args) {
		int dim = 20;
		int n = 1000;

		List<NumericalFeatureVector> featureVectors = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			List<NumericalFeature> features = new ArrayList<>();
			for (int d = 0; d < dim; d++) {
				features.add(
						new NumericalFeature(Features.DEFAULT_FEATURE_NAME_PREFIX + " " + (d + 1), Math.random() * d));
			}
			NumericalFeatureVector fv = new NumericalFeatureVector(features);
			featureVectors.add(fv);
		}

		IDimensionalityReduction<NumericalFeatureVector, NumericalFeatureVector> dimRed = null;
		int outputDimensionality = 2;

		// PCA
		//dimRed = new PCA(featureVectors, outputDimensionality);
		// MDS
		//dimRed = new MDS<>(featureVectors, new EuclideanDistanceMeasure(), outputDimensionality);
		dimRed = new FastMDS<>(featureVectors, new EuclideanDistanceMeasure(), outputDimensionality);
		// tSNE
		//dimRed = new TSNE(featureVectors, outputDimensionality);

		dimRed.calculateDimensionalityReduction();
		Map<NumericalFeatureVector, NumericalFeatureVector> highDimToLowDim = dimRed.getMapping();

		for (NumericalFeatureVector highDim : highDimToLowDim.keySet()) {
			System.out.println(
					"HighDim = " + highDim.getVector() + ", lowDim = " + highDimToLowDim.get(highDim).getVector());
		}
	}

}
