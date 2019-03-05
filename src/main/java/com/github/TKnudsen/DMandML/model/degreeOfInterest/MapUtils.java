package com.github.TKnudsen.DMandML.model.degreeOfInterest;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import smile.math.Math;

/**
 * 
 * DMandML
 *
 * Copyright: (c) 2016-2018 Juergen Bernard,
 * https://github.com/TKnudsen/DMandML<br>
 * <br>
 * 
 * Utility methods for maps that contain <code>Double</code> values
 */
public class MapUtils {
	
	public static boolean doiValidationMode = true;

	/**
	 * Some tests
	 * 
	 * @param args Not used
	 */
	public static void main(String[] args) {

		Map<String, Double> map = new LinkedHashMap<>();
		map.put("A", 1.0);
		map.put("B", 2.0);
		map.put("C", 3.0);
		map.put("D", -3.0);

		System.out.println("Input: " + map);
		System.out.println("normalizeValuesMinMax      : " + normalizeValuesMinMax(map));
		System.out.println("normalizeValuesMinMax -3, 1: " + normalizeValuesMinMax(map, -3, 1));
		System.out.println("normalizeValuesMaxMin       : " + normalizeValuesMaxMin(map));
		System.out.println("normalizeValues             : " + normalizeValues(map));

		Map<String, Double> map2 = new LinkedHashMap<>();
		map2.put("X", 10.0);
		map2.put("Y", 10.0);
		map2.put("Z", 10.0);
		System.out.println("Input: " + map2);
		System.out.println("normalizeValuesMinMax       : " + normalizeValuesMinMax(map2));
		System.out.println("normalizeValuesMaxMin       : " + normalizeValuesMaxMin(map2));

		Map<String, Double> map3 = new LinkedHashMap<>();
		map3.put("X", -10.0);
		map3.put("Y", -10.0);
		map3.put("Z", -10.0);
		System.out.println("Input: " + map3);
		System.out.println("normalizeValuesMinMax       : " + normalizeValuesMinMax(map3));
		System.out.println("normalizeValuesMaxMin       : " + normalizeValuesMaxMin(map3));

	}

	private static final double EPSILON = 1e-10;

	/**
	 * Normalize the values in the given map so that the maximum value is mapped to
	 * 0.0 and the minimum value is mapped to 1.0.<br>
	 * <br>
	 * If the minimum and maximum are equal up to a small machine epsilon, then each
	 * value 'v' will be clamped to be in [0,1] and the values in the resulting map
	 * will be (1-'v')
	 * 
	 * @param     <K> The key type
	 * 
	 * @param map The input map
	 * @return The resulting map
	 */
	public static <K> Map<K, Double> normalizeValuesMaxMin(Map<K, Double> map) {
		return affineTransformValues(normalizeValuesMinMax(map), -1.0, 1.0);
	}

	/**
	 * Normalize the values in the given map so that the minimum value is mapped to
	 * 0.0 and the maximum value is mapped to 1.0.<br>
	 * <br>
	 * If the minimum and maximum are equal up to a small machine epsilon, then the
	 * values will be clamped to be in [0,1]
	 * 
	 * @param     <K> The key type
	 * 
	 * @param map The input map
	 * @return The resulting map
	 */
	public static <K> Map<K, Double> normalizeValuesMinMax(Map<K, Double> map) {

		double min = 0.0;
		double max = 1.0;
		if (!map.isEmpty()) {
			min = Collections.min(map.values());
			max = Collections.max(map.values());
		}
		return normalizeValuesMinMax(map, min, max);
	}

	/**
	 * Normalize the values in the given map so that the given minimum value is
	 * mapped to 0.0 and the maximum value is mapped to 1.0.<br>
	 * <br>
	 * If the minimum and maximum are equal up to a small machine epsilon, then the
	 * values will be clamped to be in [0,1]
	 * 
	 * @param     <K> The key type
	 * 
	 * @param map The input map
	 * @return The resulting map
	 */
	public static <K> Map<K, Double> normalizeValuesMinMax(Map<K, Double> map, double min, double max) {
		double delta = max - min;
		if (Math.abs(delta) < EPSILON) {
			return clampValues(map, 0.0, 1.0);
		}
		double factor = 1.0 / delta;
		double addend = -min * factor;
		return affineTransformValues(map, factor, addend);
	}

	/**
	 * Performs an affine transformation of the values of the given map. This means
	 * that each value X is replaced with Y = factor * X + addend. The result is
	 * returned as a new map.
	 * 
	 * @param     <K> The key type
	 * 
	 * @param map The input map
	 * @return The map where the values are transformed
	 */
	public static <K> Map<K, Double> affineTransformValues(Map<K, Double> map, double factor, double addend) {
		Map<K, Double> result = new LinkedHashMap<>();
		for (Entry<K, Double> entry : map.entrySet()) {
			K key = entry.getKey();
			double originalValue = entry.getValue();
			double transformedValue = factor * originalValue + addend;
			result.put(key, transformedValue);
		}
		return result;
	}

	/**
	 * Clamps the values in the given map to be in the specified range.
	 * 
	 * @param     <K> The key type
	 * 
	 * @param map The input map
	 * @return The map where the values are clamped
	 */
	public static <K> Map<K, Double> clampValues(Map<K, Double> map, double min, double max) {
		Map<K, Double> result = new LinkedHashMap<>();
		for (Entry<K, Double> entry : map.entrySet()) {
			K key = entry.getKey();
			double originalValue = entry.getValue();
			double clampedValue = Math.min(max, Math.max(min, originalValue));
			result.put(key, clampedValue);
		}
		return result;
	}

	// TODO Are there any real use cases for this?
	/**
	 * Normalize the values in the given map so that they sum up to 1.0.<br>
	 * <br>
	 * (Note that if the sum of the values in the given map is 0.0, then the
	 * resulting values will be Infinity)<br>
	 * 
	 * @param     <K> The key type
	 * 
	 * @param map The input map
	 * @return The map where the values sum to 1.0
	 */
	static <K> Map<K, Double> normalizeValues(Map<K, Double> map) {
		double sum = map.values().stream().mapToDouble(Double::doubleValue).sum();
		return affineTransformValues(map, 1.0 / sum, 0.0);
	}

	/**
	 * Checks whether a map contains a certain (critical) value.
	 * 
	 * @param map
	 * @param value
	 * @param printError
	 * @return
	 */
	public static <K> boolean checkForCriticalValue(Map<K, Double> map, Double value, boolean printError) {
		if (map.containsValue(value)) {
			if (printError)
				System.err.println("critical value detected.");
			return true;
		}
		return false;
	}

	/**
	 * Private constructor to prevent instantiation
	 */
	private MapUtils() {
		// Private constructor to prevent instantiation
	}
}
