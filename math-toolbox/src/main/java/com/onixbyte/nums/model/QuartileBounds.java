/*
 * Copyright (C) 2024-2025 OnixByte.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.onixbyte.nums.model;

/**
 * A record representing the quartile bounds of a dataset.
 * <p>
 * This class encapsulates the lower and upper bounds of a dataset, which are typically used for
 * detecting outliers in the data. The bounds are calculated based on the interquartile range (IQR)
 * of the dataset. Values below the lower bound or above the upper bound may be considered outliers.
 * <p>
 * Quartile bounds consist of:
 * <ul>
 *   <li>{@code lowerBound} - The lower bound of the dataset, typically {@code Q1 - 1.5 * IQR}.</li>
 *   <li>{@code upperBound} - The upper bound of the dataset, typically {@code Q3 + 1.5 * IQR}.</li>
 * </ul>
 * <p>
 * Example usage:
 * <pre>
 * QuartileBounds bounds = QuartileBounds.builder()
 *     .lowerBound(1.5)
 *     .upperBound(7.5)
 *     .build();
 * </pre>
 *
 * @param upperBound the upper bound of the dataset
 * @param lowerBound the lower bound of the dataset
 * @author zihluwang
 * @version 1.6.5
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
