package main.java.com.github.TKnudsen.DMandML.model.evaluation;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

import main.java.com.github.TKnudsen.DMandML.model.evaluation.performanceMeasure.IPerformanceMeasure;
import main.java.com.github.TKnudsen.DMandML.model.supervised.ILearningModel;

/**
 * @author Christian Ritter
 *
 */
public abstract class AbstractEvaluation<O, X extends AbstractFeatureVector<O, ? extends Feature<O>>, Y, L extends ILearningModel<O, X, Y>>
		implements IEvaluation<O, X, Y, L> {
	
	private List<? extends IPerformanceMeasure<Y>> performanceMeasures;

	public AbstractEvaluation(List<? extends IPerformanceMeasure<Y>> performanceMeasures) {
		this.performanceMeasures = performanceMeasures;
	}

	protected List<Double> calcPerformances(List<Y> assignments, List<Y> groundTruth) {
		List<Double> res = new ArrayList<>();
		for (IPerformanceMeasure<Y> pm : performanceMeasures) {
			res.add(pm.calcPerformance(assignments, groundTruth));
		}
		return res;
	}

}
