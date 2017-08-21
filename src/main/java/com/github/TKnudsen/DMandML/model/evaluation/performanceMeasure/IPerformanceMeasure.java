package main.java.com.github.TKnudsen.DMandML.model.evaluation.performanceMeasure;

import java.util.List;

/**
 * @author Christian Ritter
 *
 */
public interface IPerformanceMeasure<T> {
	Double calcPerformance(List<T> values, List<T> groundTruth);
}
