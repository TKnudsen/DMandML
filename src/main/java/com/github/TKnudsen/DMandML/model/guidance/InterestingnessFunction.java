package com.github.TKnudsen.DMandML.model.guidance;

import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.FeatureVectorSupplier;

/**
 * <p>
 * Title: InterestingnessFunction
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
 * @author Juergen Bernard
 * @version 1.02
 */
public abstract class InterestingnessFunction<FV extends AbstractFeatureVector<?, ?>> implements IInterestingnesFunction<FV> {

	private FeatureVectorSupplier<FV> featureVectorSupplier;

	protected Map<FV, Double> interestingnessScores;

	public InterestingnessFunction(FeatureVectorSupplier<FV> featureVectorSupplier) {
		this.featureVectorSupplier = featureVectorSupplier;
	}

	public abstract void run();

	@Override
	public double getInterestingness(FV featureVector) {
		if (interestingnessScores == null) {
			System.err.println(getName() + ": Interestingness functions not calculated yet");
			return Double.NaN;
		}

		return interestingnessScores.get(featureVector);
	}

	/**
	 * resets the results of the interestingness calculation model. allows lazy
	 * implementations based on abstract function calls.
	 */
	public void resetInterestingnessScores() {
		this.interestingnessScores = null;
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public FeatureVectorSupplier<FV> getFeatureVectorSupplier() {
		return featureVectorSupplier;
	}
}
