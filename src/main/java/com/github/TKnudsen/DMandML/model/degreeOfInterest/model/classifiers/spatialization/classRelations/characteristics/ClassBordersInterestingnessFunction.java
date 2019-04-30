package com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.spatialization.classRelations.characteristics;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.LinearNormalizationFunction;
import com.github.TKnudsen.ComplexDataObject.model.transformations.normalization.NormalizationFunction;
import com.github.TKnudsen.DMandML.data.classification.IClassificationResult;
import com.github.TKnudsen.DMandML.data.outliers.IOutlierAnalysisResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.model.classifiers.ClassificationBasedInterestingnessFunction;
import com.github.TKnudsen.DMandML.model.supervised.classifier.use.IClassificationApplicationFunction;
import com.github.TKnudsen.DMandML.model.unsupervised.outliers.IOutlierAnalysisAlgorithm;

public class ClassBordersInterestingnessFunction<FV>
        extends ClassificationBasedInterestingnessFunction<FV> {

    private IOutlierAnalysisAlgorithm<FV> outlierAnalysisAlgorithm;

    public ClassBordersInterestingnessFunction(
            IClassificationApplicationFunction<FV> classificationApplicationFunction,
            IOutlierAnalysisAlgorithm<FV> outlierAnalysisAlgorithm) {
        this(classificationApplicationFunction, outlierAnalysisAlgorithm, null);
    }

    public ClassBordersInterestingnessFunction(
            IClassificationApplicationFunction<FV> classificationApplicationFunction,
            IOutlierAnalysisAlgorithm<FV> outlierAnalysisAlgorithm,
            String classifierName) {
        super(classificationApplicationFunction, classifierName);

        this.outlierAnalysisAlgorithm = outlierAnalysisAlgorithm;
    }

    @Override
    public Map<FV, Double> apply(List<? extends FV> featureVectors) {

        Objects.requireNonNull(featureVectors);
        if (featureVectors.isEmpty()) {
            return Collections.emptyMap();
        }

        IClassificationResult<FV> classificationResult = computeClassificationResult(
                featureVectors);

        LinkedHashMap<FV, Double> interestingnessScores = new LinkedHashMap<>();

        if (classificationResult == null || classificationResult.getClassDistributions() == null) {
            for (FV fv : featureVectors)
                interestingnessScores.put(fv, 0.0);

            return interestingnessScores;
        }

        for (List<FV> fvs : classificationResult.getClassDistributions().values()) {
            outlierAnalysisAlgorithm.setData(fvs);
            outlierAnalysisAlgorithm.run();
            IOutlierAnalysisResult<FV> outlierAnalysisResult =
                    outlierAnalysisAlgorithm.getOutlierAnalysisResult();
            outlierAnalysisAlgorithm.resetScores();

            fvs.forEach(fv -> {
                double outlierScore = outlierAnalysisResult.getOutlierScore(fv);
                if (!Double.isNaN(outlierScore)) {
                    interestingnessScores.put(fv, outlierScore);
                }
            });
        }

        NormalizationFunction normalizationFunction = new LinearNormalizationFunction(interestingnessScores.values());
        for (FV fv : interestingnessScores.keySet())
            interestingnessScores.put(fv, normalizationFunction.apply(interestingnessScores.get(fv)).doubleValue());

        return interestingnessScores;
    }

    @Override
    public String getName() {
        return "Class Borders  [" + getClassifierName() + "]";
    }

    @Override
    public String getDescription() {
        return "Likelihood of instances to be at the outbound of a class";
    }
}
