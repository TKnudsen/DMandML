package com.github.TKnudsen.DMandML.model.semiSupervised.activeLearning;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.IKeyValueProvider;

public class ActiveLearning {

	/**
	 * uses an active learner, a set of unlabeled data (candidates)
	 * 
	 * @param activeLearningModel
	 * @param candidates
	 * @param count
	 * @return a list of cont candidates ranked according to the active learner
	 */
	public static <FV extends IKeyValueProvider<Object>> List<FV> getActiveLearningSuggestions(
			AbstractActiveLearningModel<FV> activeLearningModel, List<FV> candidates, int count) {
		activeLearningModel.setCandidates(candidates);
		return activeLearningModel.suggestCandidates(count);
	}
}
