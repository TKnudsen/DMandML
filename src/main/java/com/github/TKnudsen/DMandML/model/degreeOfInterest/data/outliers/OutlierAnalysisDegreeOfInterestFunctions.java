package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.outliers;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.model.degreeOfInterest.IDegreeOfInterestFunction;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.IDistanceMeasure;
import com.github.TKnudsen.ComplexDataObject.model.distanceMeasure.featureVector.EuclideanDistanceMeasure;

import java.util.List;
import java.util.function.Supplier;

import com.github.TKnudsen.DMandML.data.outliers.IOutlierAnalysisResult;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.IOutlierAnalysisAlgorithm;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.impl.AggarwalYuNaiveOutlierAnalysis;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.impl.AngleBasedOutlierDetection;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.impl.DistanceBasedOutlierAnalysis;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.impl.DynamicWindowOutlierFactorOutlierAnalysis;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.impl.KNNOutlierAnalysis;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.impl.LocalIsolationCoefficientOutlierAnalysis;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.impl.LocalOutlierFactorOutlierAnalysis;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Creates instances of different outlier analysis-based
 * {@link IDegreeOfInterestFunction} implementations.
 * 
 * Uses a variety of outlier analysis algorithms, i.e., their
 * {@link IOutlierAnalysisResult}, to create a series of different instances.
 * 
 * Note that, in its current state, this factory only creates instances of
 * outlier analysis-based DOIs that do not allow the retrieval of instances that
 * are not contained in the outlier analysis result.
 * </p>
 * 
 * @version 1.01
 */
public class OutlierAnalysisDegreeOfInterestFunctions {

	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOutlierAggarwalYuNaive(
			Supplier<? extends List<? extends NumericalFeatureVector>> trainingVectorsSupplier, int phi, int k,
			boolean approximateScoresForMissingElements, int kNN,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IOutlierAnalysisAlgorithm<NumericalFeatureVector> outlierAlgorithm = new AggarwalYuNaiveOutlierAnalysis(phi, k);

		outlierAlgorithm.setData(trainingVectorsSupplier.get());
		outlierAlgorithm.run();

		return instantiateOutlierAnalysisInterestingnessFunction(outlierAlgorithm, approximateScoresForMissingElements,
				kNN, distanceMeasure);
	}

	/**
	 * 
	 * @param trainingVectorsSupplier
	 * @param approximateScoresForMissingElements
	 * @param kNN
	 * @param distanceMeasure
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOutlierAngleBased(
			Supplier<? extends List<? extends NumericalFeatureVector>> trainingVectorsSupplier,
			boolean approximateScoresForMissingElements, int kNN,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IOutlierAnalysisAlgorithm<NumericalFeatureVector> outlierAlgorithm = new AngleBasedOutlierDetection();

		outlierAlgorithm.setData(trainingVectorsSupplier.get());
		outlierAlgorithm.run();

		return instantiateOutlierAnalysisInterestingnessFunction(outlierAlgorithm, approximateScoresForMissingElements,
				kNN, distanceMeasure);
	}

	/**
	 * 
	 * @param trainingVectorsSupplier
	 * @param radius                  the maximum distance that defines the vicinity
	 *                                of an instance.
	 * @param threshold               the percentage of instances that must must
	 *                                have a larger distance than the radius in
	 *                                order to mark an instance an outlier.
	 * @return
	 */
	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOutlierDistanceBased(
			Supplier<? extends List<? extends NumericalFeatureVector>> trainingVectorsSupplier, double radius,
			double threshold, boolean approximateScoresForMissingElements, int kNN,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IOutlierAnalysisAlgorithm<NumericalFeatureVector> outlierAlgorithm = new DistanceBasedOutlierAnalysis(radius,
				threshold);

		outlierAlgorithm.setData(trainingVectorsSupplier.get());
		outlierAlgorithm.run();

		return instantiateOutlierAnalysisInterestingnessFunction(outlierAlgorithm, approximateScoresForMissingElements,
				kNN, distanceMeasure);
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOutlierDynamicWindowOutlierFactor(
			Supplier<? extends List<? extends NumericalFeatureVector>> trainingVectorsSupplier, int kNN,
			boolean approximateScoresForMissingElements, IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IOutlierAnalysisAlgorithm<NumericalFeatureVector> outlierAlgorithm = new DynamicWindowOutlierFactorOutlierAnalysis(
				kNN);

		outlierAlgorithm.setData(trainingVectorsSupplier.get());
		outlierAlgorithm.run();

		return instantiateOutlierAnalysisInterestingnessFunction(outlierAlgorithm, approximateScoresForMissingElements,
				kNN, distanceMeasure);
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOutlierKNN(
			Supplier<? extends List<? extends NumericalFeatureVector>> trainingVectorsSupplier, int kNN,
			boolean approximateScoresForMissingElements, IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IOutlierAnalysisAlgorithm<NumericalFeatureVector> outlierAlgorithm = new KNNOutlierAnalysis(kNN);

		outlierAlgorithm.setData(trainingVectorsSupplier.get());
		outlierAlgorithm.run();

		return instantiateOutlierAnalysisInterestingnessFunction(outlierAlgorithm, approximateScoresForMissingElements,
				kNN, distanceMeasure);
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOutlierLocalIsolationCoefficient(
			Supplier<? extends List<? extends NumericalFeatureVector>> trainingVectorsSupplier, int kNN,
			boolean approximateScoresForMissingElements, IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IOutlierAnalysisAlgorithm<NumericalFeatureVector> outlierAlgorithm = new LocalIsolationCoefficientOutlierAnalysis(
				kNN);

		outlierAlgorithm.setData(trainingVectorsSupplier.get());
		outlierAlgorithm.run();

		return instantiateOutlierAnalysisInterestingnessFunction(outlierAlgorithm, approximateScoresForMissingElements,
				kNN, distanceMeasure);
	}

	public static IDegreeOfInterestFunction<NumericalFeatureVector> createOutlierLocalOutlierFactor(
			Supplier<? extends List<? extends NumericalFeatureVector>> trainingVectorsSupplier, int kNN,
			boolean approximateScoresForMissingElements, IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IOutlierAnalysisAlgorithm<NumericalFeatureVector> outlierAlgorithm = new LocalOutlierFactorOutlierAnalysis(kNN);

		outlierAlgorithm.setData(trainingVectorsSupplier.get());
		outlierAlgorithm.run();

		return instantiateOutlierAnalysisInterestingnessFunction(outlierAlgorithm, approximateScoresForMissingElements,
				kNN, distanceMeasure);
	}

	/**
	 * instance will not support interestingness scores for instances not contained
	 * in the outlier analysis result.
	 * 
	 * @param outlierAlgorithm
	 * @return
	 */
	private static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateOutlierAnalysisInterestingnessFunction(
			IOutlierAnalysisAlgorithm<NumericalFeatureVector> outlierAlgorithm) {

		outlierAlgorithm.run();
		IOutlierAnalysisResult<NumericalFeatureVector> outlierAnalysisResult = outlierAlgorithm
				.getOutlierAnalysisResult();

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new OutlierAnalysisBasedInterestingnessFunction<NumericalFeatureVector>(
				outlierAnalysisResult);

		return degreeOfInterestFunction;
	}

	/**
	 * instance is capable with instances not contained in the outlier analysis
	 * result itself.
	 * 
	 * @param outlierAlgorithm
	 * @param approximateScoresForMissingElements
	 * @param kNN
	 * @param distanceMeasure
	 * @return
	 */
	private static IDegreeOfInterestFunction<NumericalFeatureVector> instantiateOutlierAnalysisInterestingnessFunction(
			IOutlierAnalysisAlgorithm<NumericalFeatureVector> outlierAlgorithm,
			boolean approximateScoresForMissingElements, int kNN,
			IDistanceMeasure<NumericalFeatureVector> distanceMeasure) {

		IOutlierAnalysisResult<NumericalFeatureVector> outlierAnalysisResult = outlierAlgorithm
				.getOutlierAnalysisResult();

		IDegreeOfInterestFunction<NumericalFeatureVector> degreeOfInterestFunction = new OutlierAnalysisBasedInterestingnessFunction<NumericalFeatureVector>(
				outlierAnalysisResult, true, kNN, new EuclideanDistanceMeasure());

		return degreeOfInterestFunction;
	}
}
