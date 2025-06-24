/*
 * Copyright (c) 2024-2025 OnixByte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.onixbyte.math;

import com.onixbyte.math.model.QuartileBounds;

import java.util.List;

/**
 * A utility class that provides methods for calculating percentiles and interquartile range (IQR)
 * bounds for a dataset.
 * <p>
 * This class contains static methods to:
 * <ul>
 *   <li>
 *     Calculate a specified percentile from a list of double values using linear interpolation.
 *   </li>
 *   <li>
 *     Calculate interquartile bounds (Q1, Q3) and the corresponding lower and upper bounds,
 *     which can be used to identify outliers in the dataset.
 *   </li>
 * </ul>
 * <p>
 * This class is final, meaning it cannot be subclassed, and it only contains static methods,
 * so instances of the class cannot be created.
 * <h2>Example usage:</h2>
 * <pre>{@code
 * List<Double> data = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
 * Double percentileValue = PercentileCalculator.calculatePercentile(data, 50.0);  // Calculates median
 * QuartileBounds bounds = PercentileCalculator.calculatePercentileBounds(data);   // Calculates IQR bounds
 * }</pre>
 *
 * @author zihluwang
 * @version 3.0.0
 * @since 1.6.5
 */
public final class PercentileCalculator {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private PercentileCalculator() {
    }

    /**
     * Calculates the specified percentile from a list of values.
     * <p>
     * This method takes a list of double values and calculates the given percentile using linear
     * interpolation between the two closest ranks. The list is first sorted in ascending order,
     * and the specified percentile is then calculated.
     *
     * @param values     a list of {@code Double} values from which the percentile is calculated.
     * @param percentile a {@code Double} representing the percentile to be calculated (e.g., 50.0
     *                   for the median)
     * @return a {@code Double} value representing the calculated percentile
     */
    public static Double calculatePercentile(List<Double> values, Double percentile) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("Unable to sort an empty list.");
        }
        var sorted = values.stream().sorted().toList();

        var rank = percentile / 100. * (sorted.size() - 1);
        var lowerIndex = (int) Math.floor(rank);
        var upperIndex = (int) Math.ceil(rank);
        var weight = rank - lowerIndex;

        return sorted.get(lowerIndex) * (1 - weight) + sorted.get(upperIndex) * weight;
    }

    /**
     * Calculates the interquartile range (IQR) and the corresponding lower and upper bounds
     * based on the first (Q1) and third (Q3) quartiles of a dataset.
     * <p>
     * This method takes a list of double values, calculates the first quartile (Q1),
     * the third quartile (Q3), and the interquartile range (IQR). Using the IQR, it computes
     * the lower and upper bounds, which can be used to detect outliers in the dataset.
     * The lower bound is defined as {@code Q1 - 1.5 * IQR}, and the upper bound is defined as
     * {@code Q3 + 1.5 * IQR}.
     *
     * @param data a list of {@code Double} values for which the quartile bounds will be calculated
     * @return a {@code QuartileBounds} object containing the calculated lower and upper bounds
     */
    public static QuartileBounds calculatePercentileBounds(List<Double> data) {
        var sorted = data.stream().sorted().toList();
        var q1 = calculatePercentile(sorted, 25.);
        var q3 = calculatePercentile(sorted, 75.);

        var iqr = q3 - q1;

        var lowerBound = q1 - 1.5 * iqr;
        var upperBound = q3 + 1.5 * iqr;

        return QuartileBounds.builder()
            .upperBound(upperBound)
            .lowerBound(lowerBound)
            .build();
    }

}
