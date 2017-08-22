package main.java.com.github.TKnudsen.DMandML.model.evaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

import main.java.com.github.TKnudsen.DMandML.model.evaluation.performanceMeasure.IPerformanceMeasure;
import main.java.com.github.TKnudsen.DMandML.model.supervised.ILearningModel;

/**
 * <p>
 * Title: KFoldCrossValidation
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
 * @version 1.02
 */
public class KFoldCrossValidation<O, X extends AbstractFeatureVector<O, ? extends Feature<O>>, Y, L extends ILearningModel<O, X, Y>> extends AbstractModelEvaluation<O, X, Y, L> {

	private int k;
	private boolean shuffle = false;

	public KFoldCrossValidation(int k) {
		this(null, k, false);
	}

	public KFoldCrossValidation(List<? extends IPerformanceMeasure<Y>> performanceMeasures, int k) {
		this(performanceMeasures, k, false);
	}

	public KFoldCrossValidation(List<? extends IPerformanceMeasure<Y>> performanceMeasures, int k, boolean shuffle) {
		super(performanceMeasures);
		this.k = k;
		this.shuffle = shuffle;
	}

	@Override
	public List<List<Double>> evaluate(L learner, List<X> featureVectors, List<Y> groundTruth) {
		if (learner == null)
			throw new IllegalArgumentException("Learning Model must not be null");
		if (featureVectors == null || groundTruth == null || featureVectors.size() != groundTruth.size())
			throw new IllegalArgumentException("Lists are null or of unequal size!");

		if (shuffle)
			Collections.shuffle(featureVectors);
		List<List<X>> groups = new ArrayList<>();
		List<List<Y>> truth = new ArrayList<>();
		double w = 1.0 * featureVectors.size() / k;
		for (int i = 0; i < k; i++) {
			groups.add(featureVectors.subList(((int) Math.round(i * w)), ((int) Math.round((i + 1) * w))));
			truth.add(groundTruth.subList(((int) Math.round(i * w)), ((int) Math.round((i + 1) * w))));
		}
		List<List<Double>> res = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			List<X> trainset = new ArrayList<>();
			List<X> testset = new ArrayList<>();
			List<Y> trainTruth = new ArrayList<>();
			List<Y> testTruth = new ArrayList<>();
			for (int j = 0; j < groups.size(); j++) {
				if (j == k) {
					testset.addAll(groups.get(j));
					testTruth.addAll(truth.get(j));
				} else {
					trainset.addAll(groups.get(j));
					trainTruth.addAll(truth.get(j));
				}
			}
			learner.train(trainset, trainTruth);
			res.add(calculatePerformances(learner.test(testset), testTruth));
		}
		return res;
	}
}
