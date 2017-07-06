package test;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVectorFactory;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;

import main.java.com.github.TKnudsen.DMandML.data.distanceMatrix.AggregationBasedDistanceMatrix;

public class AggregationBasedDistanceMatrixTester {
	public static void main(String[] args) {
		int n = 10;

		List<NumericalFeatureVector> fvs = new ArrayList<>();
		for (int i = 0; i < n; i++)
			fvs.add(NumericalFeatureVectorFactory.createNumericalFeatureVector(new double[] { i }));

		AggregationBasedDistanceMatrix dm = new AggregationBasedDistanceMatrix(fvs, new EuclideanDistanceMeasure(), 3);

		for (int i = 0; i < n; i++)
			System.out.println("Distance between 0 and "+i+": " + dm.getDistance(fvs.get(0), fvs.get(i)));
	}
}
