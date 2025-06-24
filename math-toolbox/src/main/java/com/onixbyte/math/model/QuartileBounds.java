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

package com.onixbyte.math.model;

/**
 * A record representing the quartile bounds of a dataset.
 * <p>
 * This class encapsulates the lower and upper bounds of a dataset, which are typically used for
 * detecting outliers in the data. The bounds are calculated based on the interquartile range (IQR)
 * of the dataset. Values below the lower bound or above the upper bound may be considered outliers.
 * <p>
 * Quartile bounds consist of:
 * <ul>
 *   <li>
 *     {@code lowerBound} - The lower bound of the dataset, typically {@code Q1 - 1.5 * IQR}.
 *   </li>
 *   <li>
 *     {@code upperBound} - The upper bound of the dataset, typically {@code Q3 + 1.5 * IQR}.
 *   </li>
 * </ul>
 * <p>
 * Example usage:
 * <pre>{@code
 * QuartileBounds bounds = QuartileBounds.builder()
 *      .lowerBound(1.5)
 *      .upperBound(7.5)
 *      .build();
 * }</pre>
 *
 * @param upperBound the upper bound of the dataset
 * @param lowerBound the lower bound of the dataset
 * @author zihluwang
 * @version 3.0.0
 * @since 1.6.5
 */
public record QuartileBounds(
    Double upperBound,
    Double lowerBound
) {

    /**
     * Creates a new {@link Builder} instance for building a {@code QuartileBounds} object.
     * <p>
     * The {@link Builder} pattern is used to construct the {@code QuartileBounds} object with
     * optional values for the upper and lower bounds.
     * </p>
     *
     * @return a new instance of the {@link Builder} class
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder class for constructing instances of the {@code QuartileBounds} record.
     * <p>
     * The {@link Builder} pattern allows for the step-by-step construction of a
     * {@code QuartileBounds} object, providing a flexible way to set values for the lower and
     * upper bounds. Once the builder has the required values, the {@link #build()} method creates
     * and returns a new {@code QuartileBounds} object.
     * </p>
     * <p>
     * Example usage:
     * <pre>
     * {@code
     * QuartileBounds bounds = QuartileBounds.builder()
     *     .lowerBound(1.5)
     *     .upperBound(7.5)
     *     .build();
     * }
     * </pre>
     */
    public static class Builder {
        private Double upperBound;
        private Double lowerBound;

        /**
         * Private constructor to prevent instantiation of this utility class.
         */
        private Builder() {
        }

        /**
         * Sets the upper bound for the {@code QuartileBounds}.
         *
         * @param upperBound the upper bound of the dataset
         * @return the current {@code Builder} instance, for method chaining
         */
        public Builder upperBound(Double upperBound) {
            this.upperBound = upperBound;
            return this;
        }

        /**
         * Sets the lower bound for the {@code QuartileBounds}.
         *
         * @param lowerBound the lower bound of the dataset
         * @return the current {@code Builder} instance, for method chaining
         */
        public Builder lowerBound(Double lowerBound) {
            this.lowerBound = lowerBound;
            return this;
        }

        /**
         * Builds and returns a new {@code QuartileBounds} instance with the specified upper and
         * lower bounds.
         *
         * @return a new {@code QuartileBounds} object containing the specified bounds
         */
        public QuartileBounds build() {
            return new QuartileBounds(upperBound, lowerBound);
        }
    }

}
