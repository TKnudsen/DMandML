package main.java.com.github.TKnudsen.DMandML.model.evaluation;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

import main.java.com.github.TKnudsen.DMandML.model.evaluation.performanceMeasure.IPerformanceMeasure;
import main.java.com.github.TKnudsen.DMandML.model.supervised.ILearningModel;

/**
 * <p>
 * Title: AbstractModelEvaluation
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2017 Jürgen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Christian Ritter, Juergen Bernard
 * @version 1.01
 */
public abstract class AbstractModelEvaluation<O, X extends AbstractFeatureVector<O, ? extends Feature<O>>, Y, L extends ILearningModel<O, X, Y>> implements IModelEvaluation<O, X, Y, L> {

	private List<? extends IPerformanceMeasure<Y>> performanceMeasures;

	public AbstractModelEvaluation() {
		this.setPerformanceMeasures(null);

		initDefaultPerformanceMeasures();
	}

	public AbstractModelEvaluation(List<? extends IPerformanceMeasure<Y>> performanceMeasures) {
		this.setPerformanceMeasures(performanceMeasures);

		if (performanceMeasures == null)
			initDefaultPerformanceMeasures();
	}

	protected abstract void initDefaultPerformanceMeasures();

	protected List<Double> calculatePerformances(List<Y> assignments, List<Y> groundTruth) {
		List<Double> res = new ArrayList<>();

		if (getPerformanceMeasures() == null)
			throw new IllegalArgumentException("ModelEvaluation: performance measures not set.");

		for (IPerformanceMeasure<Y> pm : getPerformanceMeasures()) {
			res.add(pm.calcPerformance(assignments, groundTruth));
		}
		return res;
	}

	public List<? extends IPerformanceMeasure<Y>> getPerformanceMeasures() {
		return performanceMeasures;
	}

	public void setPerformanceMeasures(List<? extends IPerformanceMeasure<Y>> performanceMeasures) {
		this.performanceMeasures = performanceMeasures;
	}
}
