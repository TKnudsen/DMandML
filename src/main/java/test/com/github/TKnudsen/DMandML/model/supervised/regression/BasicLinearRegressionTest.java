package test.com.github.TKnudsen.DMandML.model.supervised.regression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeature;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.DMandML.model.supervised.regression.BasicLinearRegression;

public class BasicLinearRegressionTest {

	public static void main(String[] args) {

//		1	0,59
//		2	0,66
//		3	0,77
//		4	0,79
//		5	0,88
//		6	0,97

		// trend of a time series (years 1-6)

		List<Double> values = Arrays.asList(new Double[] { 0.59, 0.66, 0.77, 0.79, 0.88, 0.97 });

		List<NumericalFeatureVector> trainFeatureVectors = new ArrayList<>();
		List<Double> targetValues = new ArrayList<Double>();

		int index = 1;
		for (int i = 0; i < values.size(); i++) {
			NumericalFeature timeFeature = new NumericalFeature("time", (double) index++);
			trainFeatureVectors.add(new NumericalFeatureVector(Arrays.asList(new NumericalFeature[] { timeFeature })));

			targetValues.add(values.get(i));
		}

		BasicLinearRegression<NumericalFeatureVector> regression = new BasicLinearRegression<>();

		regression.setTargetValues(targetValues);

		regression.train(trainFeatureVectors);

		// calculate trend: delta of predictions between first and last time stamp
		List<Double> test = regression.test(trainFeatureVectors);

		for (int i = 0; i < values.size(); i++)
			System.out.println("original value: " + values.get(i) + ", model output: " + test.get(i));

		double deltaT = trainFeatureVectors.get(trainFeatureVectors.size() - 1).get(0)
				- trainFeatureVectors.get(0).get(0);
		double deltaV = test.get(test.size() - 1) - test.get(0);

		double m = deltaV / deltaT;
		System.out.println(m);
	}

}
