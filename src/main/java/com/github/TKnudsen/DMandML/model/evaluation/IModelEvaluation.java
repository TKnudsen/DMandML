package com.github.TKnudsen.DMandML.model.evaluation;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;
import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;
import com.github.TKnudsen.DMandML.model.evaluation.performanceMeasure.IPerformanceMeasure;
import com.github.TKnudsen.DMandML.model.supervised.ILearningModel;

/**
 * <p>
 * Title: IModelEvaluation
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 J�rgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Christian Ritter, Juergen Bernard
 * @version 1.01
 */
public interface IModelEvaluation<O, X extends AbstractFeatureVector<O, ? extends Feature<O>>, Y, L extends ILearningModel<O, X, Y>> extends ISelfDescription {

	/**
	 * Evaluates the performance of a learning algorithm on a given dataset
	 * 
	 * @param learner
	 *            the learning model
	 * @param featureVectors
	 *            the feature vectors of the data set
	 * @param groundTruth
	 *            the true value that should be assigned to each feature vector
	 * @return a list containing a list with performance values for each
	 *         iteration of evaluation
	 */
	void evaluate(L learner, List<X> featureVectors, List<Y> groundTruth);

	/**
	 * provides a List of added/default performance measures
	 * 
	 * @return
	 */
	List<IPerformanceMeasure<Y>> getPerformanceMeasures();

	/**
	 * retrieves the result of a particular PerformanceMeasure
	 * 
	 * @param performanceMeasure
	 * @return
	 */
	Double getCumulatedPerformance(IPerformanceMeasure<Y> performanceMeasure);

	/**
	 * retrieves the a List of results of a particular PerformanceMeasure
	 * representing the distribution, etc.
	 * 
	 * @param performanceMeasure
	 * @return
	 */
	List<Double> getPerformance(IPerformanceMeasure<Y> performanceMeasure);
}
