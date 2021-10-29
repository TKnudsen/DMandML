package com.github.TKnudsen.DMandML.model.supervised.classifier.use;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.model.supervised.classifier.IClassifier;

public class ClassificationApplications {

	public static <FV> List<Function<List<? extends FV>, IClassificationResult<FV>>> createClassificationApplicationFunctions(
			List<IClassifier<FV>> classifiers) {

		List<Function<List<? extends FV>, IClassificationResult<FV>>> classificationApplicationFunctions = new ArrayList<>();
		for (IClassifier<FV> classifier : classifiers)
			classificationApplicationFunctions.add(classifier::createClassificationResult);

		return classificationApplicationFunctions;
	}

	/**
	 * * Creates a list of functions that encapsulate the
	 * {@link IClassifier#createClassificationResult(List)} call, to be forwarded to
	 * the DOIs as a "read-only-view" on the classifiers
	 * 
	 * @param <T>         object type
	 * @param classifiers The classifiers
	 * @return The functions
	 * @return
	 */
	public static <T> List<IClassificationApplicationFunction<T>> createFor(
			List<? extends IClassifier<T>> classifiers) {
		List<IClassificationApplicationFunction<T>> classificationResultFunctions = new ArrayList<>();
		for (IClassifier<T> classifier : classifiers) {
			classificationResultFunctions.add(createFor(classifier));
		}
		return classificationResultFunctions;
	}

	public static <T> IClassificationApplicationFunction<T> createFor(IClassifier<T> classifier) {
		return new IClassificationApplicationFunction<T>() {

			@Override
			public IClassificationResult<T> apply(List<? extends T> t) {
				return classifier.createClassificationResult(t);
			}

			@Override
			public String toString() {
				return classifier.getName();
			}
		};
	}
}
