package com.github.TKnudsen.DMandML.model.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.TKnudsen.DMandML.model.evaluation.performanceMeasure.IPerformanceMeasure;
import com.github.TKnudsen.DMandML.model.supervised.ILearningModel;

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
 * Copyright: (c) 2016-2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Christian Ritter, Juergen Bernard
 * @version 1.01
 */
public abstract class AbstractModelEvaluation<O, X, Y, L extends ILearningModel<O, X, Y>> implements IModelEvaluation<O, X, Y, L> {

	private List<? extends IPerformanceMeasure<Y>> performanceMeasures;
	protected Map<IPerformanceMeasure<Y>, List<Double>> performanceValues;

	public AbstractModelEvaluation() {
		this.setPerformanceMeasures(null);

		initDefaultPerformanceMeasures();
	}

	public AbstractModelEvaluation(List<? extends IPerformanceMeasure<Y>> performanceMeasures) {
		this.setPerformanceMeasures(performanceMeasures);

		if (performanceMeasures == null)
			initDefaultPerformanceMeasures();
	}

	protected void calculatePerformances(List<Y> assignments, List<Y> groundTruth) {
		if (getPerformanceMeasures() == null)
			throw new IllegalArgumentException("ModelEvaluation: performance measures not set.");

		for (IPerformanceMeasure<Y> pm : getPerformanceMeasures()) {
			performanceValues.get(pm).add(pm.calcPerformance(assignments, groundTruth));
		}
	}

	public List<IPerformanceMeasure<Y>> getPerformanceMeasures() {
		return new ArrayList<>(performanceMeasures);
	}

	protected abstract void initDefaultPerformanceMeasures();

	public void setPerformanceMeasures(List<? extends IPerformanceMeasure<Y>> performanceMeasures) {
		this.performanceMeasures = performanceMeasures;
	}

	public List<Double> getPerformance(IPerformanceMeasure<Y> performanceMeasure) {
		return performanceValues.get(performanceMeasure);
	}

	public Double getCumulatedPerformance(IPerformanceMeasure<Y> performanceMeasure) {
		return cumulate(performanceValues.get(performanceMeasure));
	}

	protected abstract Double cumulate(List<Double> values);
}
