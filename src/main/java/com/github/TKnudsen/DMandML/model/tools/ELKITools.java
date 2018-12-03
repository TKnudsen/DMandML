package com.github.TKnudsen.DMandML.model.tools;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;

import java.util.List;
import java.util.Objects;

import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.database.StaticArrayDatabase;
import de.lmu.ifi.dbs.elki.datasource.ArrayAdapterDatabaseConnection;
import de.lmu.ifi.dbs.elki.datasource.DatabaseConnection;

public class ELKITools {

	/**
	 * creates an ELKI database instance from a list of given feature vectors. List
	 * of feature vector must contain at least one entry.
	 * 
	 * The database will be initialized as well.
	 * 
	 * @param featureVectors
	 * @return
	 */
	public static Database createAndInitializeELKIDatabase(List<? extends NumericalFeatureVector> featureVectors) {
		Objects.requireNonNull(featureVectors);

		int count = featureVectors.size();

		if (count == 0)
			throw new IllegalArgumentException(
					"ELKITools.createAndInitializeELKIDatabase requires non-empty list of FV");

		int dimensions = featureVectors.get(0).getSize();

		double[][] data = new double[count][dimensions];
		for (int i = 0; i < count; i++) {
			data[i] = featureVectors.get(i).getVector();
		}

		DatabaseConnection dbc = new ArrayAdapterDatabaseConnection(data);
		Database db = new StaticArrayDatabase(dbc, null);

		// initialize
		db.initialize();

		return db;
	}
}
