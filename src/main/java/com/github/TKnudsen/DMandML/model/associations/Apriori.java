package com.github.TKnudsen.DMandML.model.associations;

import weka.core.Utils;

/**
 * <p>
 * Apriori Associator from Weka.
 * </p>
 *
 * @version 1.01
 * @since 2016
 */
public class Apriori extends WekaAssociator {

	@Override
	protected void initializeAssociator() {
		associator = new weka.associations.Apriori();

		String[] options = null;
		try {
			options = Utils.splitOptions("-N 500 -T 1 -C 0.5 -D 0.05 -U 1.0 -M 0.1 -V yes");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		try {
			associator.setOptions(options);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
