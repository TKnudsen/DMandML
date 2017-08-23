package main.java.com.github.TKnudsen.DMandML.model.evaluation.performanceMeasure.classification;

import java.util.List;

/**
 * <p>
 * Title: FMeasure
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
 * @author Christian Ritter
 * @version 1.01
 */
public class AverageFMeasure implements IClassificationPerformanceMeasure {

	private double beta;

	public AverageFMeasure() {
		this(1.0);
	}

	public AverageFMeasure(double beta) {
		this.beta = beta;
	}

	@Override
	public Double calcPerformance(List<String> values, List<String> groundTruth) {
		AverageRecall ar = new AverageRecall();
		AveragePrecision ap = new AveragePrecision();

		double r = ar.calcPerformance(values, groundTruth);
		double p = ap.calcPerformance(values, groundTruth);

		double beta2 = beta * beta;
		return (1 + beta2) * p * r / (beta2 * p + r);
	}

	@Override
	public String getDescription() {
		return "Calculates the F-measure over all classes of a classification result";
	}

	@Override
	public String getName() {
		return "Average F" + String.valueOf(beta) + "-Measure";
	}

}
