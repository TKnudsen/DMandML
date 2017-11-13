package com.github.TKnudsen.DMandML.model.degreeOfInterest;

import java.util.Map;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.IFeatureVectorSupplier;

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
public abstract class DegreeOfInterestFunction<FV extends AbstractFeatureVector<?, ?>> implements IDegreeOfInterestFunction<FV> {

	private IFeatureVectorSupplier<FV> featureVectorSupplier;

	protected Map<FV, Double> interestingnessScores;

	public DegreeOfInterestFunction(IFeatureVectorSupplier<FV> featureVectorSupplier) {
		this.featureVectorSupplier = featureVectorSupplier;
	}

	public abstract void run();

	@Override
	public Double apply(FV featureVector) {
		if (interestingnessScores == null) {
			run();
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
	public IFeatureVectorSupplier<FV> getFeatureVectorSupplier() {
		return featureVectorSupplier;
	}
}
