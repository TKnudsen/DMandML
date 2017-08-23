package main.java.com.github.TKnudsen.DMandML.model.evaluation.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.AbstractFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.features.Feature;

import main.java.com.github.TKnudsen.DMandML.model.supervised.ILearningModel;

/**
 * <p>
 * Title: CSVModelEvaluation
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
 * @author Christian Ritter
 * @version 1.01
 */
public class CSVModelEvaluationIO<O, X extends AbstractFeatureVector<O, ? extends Feature<O>>, Y, L extends ILearningModel<O, X, Y>> extends AbstractModelEvaluationIO<O, X, Y, L> {

	@Override
	public String getName() {
		return "CSV Model Evaluation IO";
	}

	@Override
	public String getDescription() {
		return "Exports / imports evaluation results from / to CSV-files.";
	}

	@Override
	public void exportResult(String directory) {
		// simple header containing all meta data
		StringBuilder sb = new StringBuilder();
		sb.append("metadata");
		for (String k : getAllMetaData().keySet()) {
			sb.append(",");
			sb.append(k);
			sb.append("=");
			sb.append(getAllMetaData().get(k));
		}
		sb.append(System.lineSeparator());

		List<String> pmNames = new ArrayList<>();
		// column names
		sb.append("index");
		for (String pm : getPerformanceValues().keySet()) {
			sb.append(",");
			sb.append(pm);
			pmNames.add(pm);
		}
		sb.append(System.lineSeparator());

		// cumulated values
		sb.append("cumulated");
		for (String pm : pmNames) {
			sb.append(",");
			sb.append(String.valueOf(getCumulatedPerformanceValue(pm)));
		}
		sb.append(System.lineSeparator());

		// single values
		if (pmNames.size() > 0) {
			int n = getPerformanceValue(pmNames.get(0)).size();
			for (int i = 0; i < n; i++) {
				sb.append(i);
				for (String pm : pmNames) {
					sb.append(",");
					sb.append(getPerformanceValue(pm).get(i));
				}
				sb.append(System.lineSeparator());
			}
		}

		// write output to file
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(directory)));
			bw.write(sb.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importResult(String directory) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(directory)));
			String line = br.readLine();

			if (line == null) {
				br.close();
				throw new IOException("File is empty.");
			}

			// read meta data
			String[] vals = line.split(",");
			if (vals.length == 0 || !vals[0].equals("metadata")) {
				br.close();
				throw new IOException("File is malformed.");
			}

			for (int i = 1; i < vals.length; i++) {
				String[] kv = vals[i].split("=");
				getAllMetaData().put(kv[0], kv[1]);
			}

			line = br.readLine();

			// read performance measure names
			List<String> pmNames = new ArrayList<>();
			vals = line.split(",");
			if (vals.length == 0 || !vals[0].equals("index")) {
				br.close();
				throw new IOException("File is malformed.");
			}

			for (int i = 1; i < vals.length; i++) {
				pmNames.add(vals[i]);
			}

			line = br.readLine();

			// read cumulated values
			vals = line.split(",");
			if (vals.length == 0 || !vals[0].equals("cumulated")) {
				br.close();
				throw new IOException("File is malformed.");
			}

			for (int i = 1; i < vals.length; i++) {
				getCumulatedPerformanceValues().put(pmNames.get(i), Double.valueOf(vals[i]));
			}

			line = br.readLine();

			// read remaining values
			while (line != null) {
				vals = line.split(",");

				for (int i = 1; i < vals.length; i++) {
					getPerformanceValues().get(pmNames.get(i)).add(Double.valueOf(vals[i])); 
				}
				br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
