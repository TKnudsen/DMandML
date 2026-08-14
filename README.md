# DMandML

A Java library of data mining and machine learning building blocks --
classification, regression, clustering, outlier detection, dimensionality
reduction, active learning, and the distance measures and evaluation
machinery that tie them together. Builds on
[ComplexDataObject](https://github.com/TKnudsen/ComplexDataObject) and
[timeSeries](https://github.com/TKnudsen/timeSeries).

Many algorithms wrap [Weka](https://www.cs.waikato.ac.nz/ml/weka/) and
[ELKI](https://elki-project.github.io/) implementations behind a consistent,
generic API, rather than requiring each caller to deal with those libraries'
own data shapes directly.

## Usage

Maven coordinates:

```xml
<dependency>
  <groupId>com.github.tknudsen</groupId>
  <artifactId>dm-and-ml</artifactId>
  <version>0.0.1</version>
</dependency>
```

Current snapshot:

```xml
<dependency>
  <groupId>com.github.tknudsen</groupId>
  <artifactId>dm-and-ml</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Supervised Learning

- **Classification** (`model.supervised.classifier`) -- `IClassifier` /
  `INumericalDataClassifier` / `IMixedDataClassifier` define the contract;
  `WekaClassifierWrapper` adapts any Weka classifier to it.
  Ready-to-use implementations include `RandomForest`, `AdaBoost`,
  `BayesNet`, `GaussianProcesses`, `NaiveBayesMultinomial`,
  `DecorateClassifier`, `MINNDClassifier`, `LibSVMClassifier`, and SVM
  variants (`SVMLinearClassifier`, `SVMPolynomialClassifier`, `SMOSVN`).
- **Regression** (`model.supervised.regression`) -- parallel
  `numericFeatures`/`mixedFeatures` implementations of `LinearRegression`,
  `M5P`, `REPTree`, `RandomTree`, `DecisionStump`, `ZeroR`.
- **Evaluation** (`model.supervised.evaluation`) -- `IModelEvaluation` /
  `KFoldCrossValidation` for model assessment; `IPerformanceMeasure` and its
  classification-specific implementations for scoring predictions.

## Unsupervised Learning

- **Clustering** (`model.unsupervised.clustering`) -- `KMeans`, `XMeans`,
  `DBScan`, `OPTICS`, `HierarchicalClustering` (with configurable
  `LinkageStrategy`), `AffinityPropagation`, `ExpectationMaximization`,
  `Canopy`, `FarthestFirst`, `Cobweb`. Cluster quality is assessed via
  `clusterValidity` measures (e.g. `SilhouetteClusterValidityMeasure`,
  `ClusterCompactnessMeasure`); `splitting.MaximumDistanceSplitting` divides
  an existing cluster further.
- **Outlier detection** (`model.unsupervised.outliers`) -- distance-,
  density-, and angle-based detectors: `LocalOutlierFactorOutlierAnalysis`,
  `KNNOutlierAnalysis`, `DistanceBasedOutlierAnalysis`,
  `DensityBasedOutlierAnalysis`, `AngleBasedOutlierDetection`,
  `AggarwalYuNaiveOutlierAnalysis`, `LocalIsolationCoefficientOutlierAnalysis`,
  `DynamicWindowOutlierFactorOutlierAnalysis`, `MedianAbsoluteDeviation`.

## Semi-Supervised: Active Learning

`model.semiSupervised.activeLearning` -- one package per query strategy
family, all selecting the next-most-informative unlabeled instance(s) for a
human/oracle to label:

- **Uncertainty sampling** -- `EntropyBasedActiveLearning`,
  `SmallestMarginActiveLearning`, `LeastSignificantConfidence`,
  `SimpsonsDiversityActiveLearningModel`.
- **Query by committee** -- `AbstractQueryByCommitteeActiveLearning` and
  variants scoring committee disagreement differently: `VoteEntropyQueryByCommittee`,
  `VoteComparisonQueryByCommittee`, `KullbackLeiblerQueryByCommittee`,
  `ProbabilityDistanceBasedQueryByCommittee`, `ActiveDecorateQueryByCommittee`.
- **Expected error/loss reduction** -- `Expected01LossReduction`,
  `ExpectedLogLossReduction`.
- **Expected information gain** -- `ExpectedInformationGainActiveLearning`.
- **Variance reduction** -- `ExpectedVarianceReductionActiveLearning`.
- **Density weighting** -- `InformationDensityActiveLearning`.

## Dimensionality Reduction

`model.transformations.dimensionalityReduction` -- `PCA`, `FLD` (Fisher's
Linear Discriminant, plus `FLDs` for the multi-class case), `MDS` and
`FastMDS` (with a `generic.GenericMDS` variant decoupled from a specific
distance measure), `TSNE` and `BhTSNE` (Barnes-Hut approximation).

## Distance Measures and Matrices

- `model.distanceMeasure` -- distance functions over `Double`, feature
  vectors, and clusters, used throughout the classifiers, clustering
  algorithms, and retrieval below.
- `data.distanceMatrix` -- `AggregationDistanceMatrix` combines multiple
  distance measures/matrices into one, for algorithms that need a single
  aggregated notion of distance across heterogeneous features.

## Retrieval

`model.retrieval` -- `IRetrievalAlgorithm` is the shared contract;
`kNN.KNN` and `epsilon.EpsilonNeighbors` are the two neighbor-retrieval
strategies built on it.

## ELKI Interop

`data.elki` -- `ELKIDataWrapper` bridges this library's data representations
into ELKI's, so ELKI-based algorithms (e.g. `OPTICS`) can be used without
hand-converting data shapes at every call site.
