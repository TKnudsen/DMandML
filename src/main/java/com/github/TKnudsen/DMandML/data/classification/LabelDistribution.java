package com.github.TKnudsen.DMandML.data.classification;

import java.util.Map;

public class LabelDistribution {

	private Map<String, Double> labelDistribution;

	private String winningLabel;

	public LabelDistribution(Map<String, Double> labelDistribution) {
		this.labelDistribution = labelDistribution;

		calculateWinningLabel();
	}

	public LabelDistribution(Map<String, Double> labelDistribution, String winningLabel) {
		this.labelDistribution = labelDistribution;
		this.winningLabel = winningLabel;
	}

	private void calculateWinningLabel() {
		// TODO
	}

	public String getWinningLabel() {
		return winningLabel;
	}
}
