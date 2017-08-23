package main.java.com.github.TKnudsen.DMandML.model.evaluation.performanceMeasure;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;

/**
 * @author Christian Ritter
 *
 */
public interface IPerformanceMeasure<T> extends ISelfDescription{
	Double calcPerformance(List<T> values, List<T> groundTruth);
}
