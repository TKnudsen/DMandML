package com.github.TKnudsen.DMandML.model.tools.density;

import java.util.ArrayList;
import java.util.List;

/**
 * Nadaraya-Watson kernel regression: smooths a scatter of (x, y) samples by
 * evaluating, at every requested point, a Gaussian-weighted average of all
 * samples' y values, weighted by their x-distance to that point. Unlike
 * {@link GaussianKernelDensityEstimator} (which sums unnormalized kernel
 * contributions into a density), this normalizes by the summed weights at
 * each evaluation point, so the result stays in the same units as y rather
 * than accumulating into an unbounded density value.
 *
 * @since 2026
 */
public class GaussianKernelSmoother {

	private double bandwidth;

	public GaussianKernelSmoother(double bandwidth) {
		this.bandwidth = bandwidth;
	}

	public List<Double> smooth(List<Double> x, List<Double> y, List<Double> evaluationPoints) {
		if (x.size() != y.size())
			throw new IllegalArgumentException("GaussianKernelSmoother: x and y sizes differ");

		List<Double> smoothed = new ArrayList<>();

		for (Double evaluationPoint : evaluationPoints) {
			double weightedSum = 0;
			double weightSum = 0;

			for (int i = 0; i < x.size(); i++) {
				double weight = evaluateGaussAt(x.get(i), evaluationPoint);
				weightedSum += weight * y.get(i);
				weightSum += weight;
			}

			smoothed.add(weightSum == 0 ? Double.NaN : weightedSum / weightSum);
		}

		return smoothed;
	}

	private double evaluateGaussAt(double mean, double x) {
		return Math.exp(-Math.pow(x - mean, 2) / (2 * Math.pow(bandwidth, 2)));
	}

	public double getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(double bandwidth) {
		this.bandwidth = bandwidth;
	}

}
