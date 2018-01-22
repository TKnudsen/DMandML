package com.github.TKnudsen.DMandML.model.supervised.classifier.evaluation;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.interfaces.ISelfDescription;
import com.github.TKnudsen.DMandML.model.supervised.classifier.IClassifier;

/**
 * <p>
 * Title: IClassifierEvaluation
 * </p>
 * 
 * <p>
 * Description: Interface for classification quality assessment
 * </p>
 * 
 * <p>
 * Copyright: (c) 2017 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 * 
 * TODO_GENERICS Parameter "O" is not used any more
 * TODO_GENERICS Parameter "S extends String" !??! REMOVE THIS! 
 */
public interface IClassifierEvaluation<O, FV, S extends String> extends ISelfDescription{

	public double getQuality(IClassifier<O, FV> model, List<FV> testData, S targetVariable);
	
	
}
