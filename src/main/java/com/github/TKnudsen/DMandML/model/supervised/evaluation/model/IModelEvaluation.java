package com.github.TKnudsen.DMandML.model.supervised.evaluation.model;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;
import com.github.TKnudsen.DMandML.model.supervised.ILearningModel;
import com.github.TKnudsen.DMandML.model.supervised.evaluation.performanceMeasure.IPerformanceMeasure;

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
 * Copyright: (c) 2016-2018 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Christian Ritter, Juergen Bernard
 * @version 1.02
 * 
 */
public interface IModelEvaluation<X, Y, L extends ILearningModel<X, Y>> extends ISelfDescription {

	/**
	 * Evaluates the performance of a learning algorithm on a given dataset
	 * 
	 * @param learner        the learning model
	 * @param featureVectors the feature vectors of the data set
	 * @param groundTruth    the true value that should be assigned to each feature
	 *                       vector
	 */
	void evaluate(L learner, List<X> featureVectors, List<Y> groundTruth);

	/**
	 * provides a List of added/default performance measures
	 * 
	 * @return performance measures provided with the model evaluation procedure
	 */
	List<IPerformanceMeasure<Y>> getPerformanceMeasures();

	/**
	 * retrieves the result of a particular PerformanceMeasure
	 * 
	 * @param performanceMeasure the query performance measure
	 * @return cumulative performance of one measure
	 */
	Double getCumulatedPerformance(IPerformanceMeasure<Y> performanceMeasure);

	/**
	 * retrieves the a List of results of a particular PerformanceMeasure
	 * representing the distribution, etc.
	 * 
	 * @param performanceMeasure the query performance measure
	 * @return performance of one performance measure
	 */
	List<Double> getPerformance(IPerformanceMeasure<Y> performanceMeasure);
}
