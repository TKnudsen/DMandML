package com.github.TKnudsen.DMandML.model.degreeOfInterest.data.clustering;

import com.github.TKnudsen.DMandML.data.cluster.ICluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.degreeOfInterest.IDegreeOfInterestFunction;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Calculates interestingness scores of elements on the basis of
 * {@link IClusteringResult}s.
 * </p>
 * 
 * Partitions a set of instances into disjoint groups or clusters C1,...,Cn of
 * similar instances. Clustering provides a meta-structure on the original data
 * and is a common building block for several degree-of-interest functions where
 * it facilitates the priorization of instances at e.g. cluster centroids,
 * cluster border areas, or at spatially close clusters.
 * </p>
 * 
 * Clustering (CLU) DOI/building block published in: Juergen Bernard, Matthias
 * Zeppelzauer, Markus Lehmann, Martin Mueller, and Michael Sedlmair: Towards
 * User-Centered Active Learning Algorithms. Eurographics Conference on
 * Visualization (EuroVis), Computer Graphics Forum (CGF), 2018.
 * </p>
 * 
 * @version 1.02
 */
public interface IClusteringBasedDegreeOfInterestingnessFunction<FV> extends IDegreeOfInterestFunction<FV> {

	public IClusteringResult<FV, ? extends ICluster<FV>> getClusteringResult();

	public boolean isRetrieveNearestClusterForUnassignedElements();

	public void setRetrieveNearestClusterForUnassignedElements(boolean retrieveNearestClusterForUnassignedElements);

}
