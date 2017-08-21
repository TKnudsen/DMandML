package main.java.com.github.TKnudsen.DMandML.model.evaluation;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

import main.java.com.github.TKnudsen.DMandML.model.supervised.ILearningModel;

/**
 * @author Christian Ritter
 *
 */
public interface IEvaluation<O, X extends AbstractFeatureVector<O, ? extends Feature<O>>, Y, L extends ILearningModel<O, X, Y>> {

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
	List<List<Double>> evaluate(L learner, List<X> featureVectors, List<Y> groundTruth);

}
