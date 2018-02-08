package com.github.TKnudsen.DMandML.view.visualMapping;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.view.visualMapping.color.VisualColorMappingFunction;
import com.github.TKnudsen.DMandML.data.cluster.Cluster;
import com.github.TKnudsen.DMandML.data.cluster.IClusteringResult;
import com.github.TKnudsen.DMandML.model.unsupervised.clustering.IClusteringAlgorithm;

/**
 * <p>
 * Title: ClusterToColorMappingFunction
 * </p>
 * 
 * <p>
 * Description: visual mapping of clusters to colors.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2016-2018 Juergen Bernard, https://github.com/TKnudsen/DMandML
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.01
 */
public class ClusterToColorMappingFunction extends VisualColorMappingFunction<NumericalFeatureVector> {
	private IClusteringAlgorithm<NumericalFeatureVector> clusteringRoutine;
	private List<Color> colorList;

	public ClusterToColorMappingFunction(IClusteringAlgorithm<NumericalFeatureVector> clusteringRoutine) {
		this.clusteringRoutine = clusteringRoutine;

	}

	@Override
	protected Color calculateMapping(NumericalFeatureVector t) {

		IClusteringResult<NumericalFeatureVector, ? extends Cluster<NumericalFeatureVector>> result = clusteringRoutine
				.getClusteringResult();

		if (colorList == null) {
			colorList = new ArrayList<>();
			for (int i = 0; i < result.getClusters().size(); i++) {
				colorList.add(new Color((int) (Math.random() * 0x1000000)));
			}
			colorList.set(0, new Color(45, 85, 241));
			colorList.set(1, new Color(103, 204, 23));
		}
		int index = result.getClusterIndex(t);

		if (index > -1)
			return colorList.get(index);
		else
			return Color.WHITE;
	}

}
