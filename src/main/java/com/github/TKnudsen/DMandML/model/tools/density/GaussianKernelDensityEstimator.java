package com.github.TKnudsen.DMandML.model.tools.density;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Estimates a 1D probability density from a list of scalar samples via
 * Gaussian kernel density estimation: every sample contributes a Gaussian
 * bump of the given variance (bandwidth), summed across all samples at each
 * evaluation point. No notion of order, time, or weighting beyond what is
 * passed in explicitly -- callers with domain-specific sample semantics
 * (e.g. unevenly-sampled observations that should not count equally) weight
 * the samples themselves before calling {@link #estimateDensity(List, List,
 * List)}.
 *
 * @since 2026
 */
public class GaussianKernelDensityEstimator {

	private double variance;

	public GaussianKernelDensityEstimator(double variance) {
		this.variance = variance;
	}

	/**
	 * Evenly-spaced evaluation grid across [min, max], for use with {@link
	 * #estimateDensity(List, List)}.
	 */
	public static List<Double> createEvaluationGrid(double min, double max, int numSamples) {
		List<Double> grid = new ArrayList<>();

		double quantization = (max - min) / numSamples;
		for (double x = min; x <= max; x += quantization)
			grid.add(x);

		return grid;
	}

	/** Every sample contributes equally to the density estimate. */
	public List<Double> estimateDensity(List<Double> samples, List<Double> evaluationPoints) {
		return estimateDensity(samples, Collections.nCopies(samples.size(), 1.0), evaluationPoints);
	}

	/**
	 * Every sample contributes {@code weights.get(i)} times its Gaussian kernel
	 * to the density estimate -- e.g. to compensate for samples that do not all
	 * represent the same amount of "evidence" (unevenly spaced observations).
	 */
	public List<Double> estimateDensity(List<Double> samples, List<Double> weights, List<Double> evaluationPoints) {
		if (samples.size() != weights.size())
			throw new IllegalArgumentException("GaussianKernelDensityEstimator: samples and weights sizes differ");

		List<Double> density = new ArrayList<>();

		for (Double x : evaluationPoints) {
			double sum = 0;
			for (int i = 0; i < samples.size(); i++)
				sum += weights.get(i) * evaluateGaussAt(samples.get(i), x);
			density.add(sum);
		}

		return density;
	}

	private double evaluateGaussAt(double mean, double x) {
		double normalization = 1 / (variance * Math.sqrt(2 * Math.PI));
		return normalization * Math.exp(-Math.pow(x - mean, 2) / (2 * Math.pow(variance, 2)));
	}

	public double getVariance() {
		return variance;
	}

	public void setVariance(double variance) {
		this.variance = variance;
	}

}
