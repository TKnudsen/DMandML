package test.com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction;

import java.io.IOException;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.complexDataObject.ComplexDataObject;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.io.arff.ARFFParser;
import com.github.TKnudsen.ComplexDataObject.model.transformations.descriptors.numericalFeatures.NumericalFeatureVectorDescriptor;
import com.github.TKnudsen.DMandML.model.transformations.dimensionalityReduction.MDS;

/**
 * <p>
 * Title: MDSTesterWithIris
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */

public class MDSTesterWithIris {
	public static void main(String[] args) {

		// Iris data set
		List<ComplexDataObject> dataSet = null;

		try {
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			ARFFParser arffParser = new ARFFParser();
			dataSet = arffParser.parse("data/iris.arff");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Descriptor: transforms ComplexDataObjects into NumericalFeatureVectors
		NumericalFeatureVectorDescriptor descriptor = new NumericalFeatureVectorDescriptor();
		List<NumericalFeatureVector> fvs = descriptor.transform(dataSet);

		String classAttribute = "class";
		for (int i = 0; i < dataSet.size(); i++)
			fvs.get(i).add(classAttribute, dataSet.get(i).getAttribute("class").toString());

		// V1: with features in the constructor
		MDS<NumericalFeatureVector> mds = new MDS<>(fvs, new EuclideanDistanceMeasure(), 2);

		// // V2 with a distance matrix in the constructor
		// EuclideanDistanceMeasure dm = new EuclideanDistanceMeasure();
		// double[][] distanceMatrix = new double[fvs.size()][fvs.size()];
		// for (int i = 0; i < fvs.size(); i++)
		// for (int j = 0; j < fvs.size(); j++)
		// distanceMatrix[i][j] = dm.getDistance(fvs.get(i), fvs.get(j));
		// MultiDimensionalScaling mds = new
		// MultiDimensionalScaling(distanceMatrix, 1);

		// ...and the story goes on...
		mds.setMaxIterations(50);
		mds.setPrintProgress(true);
		mds.calculateDimensionalityReduction();
		List<NumericalFeatureVector> transformed = mds.transform(fvs);

		for (NumericalFeatureVector fv : transformed) {
			for (int i = 0; i < fv.getDimensions(); i++)
				System.out.print(fv.getVector()[i] + " ");
			System.out.println();
		}
	}
}
