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

package com.onixbyte.devkit.utils;

import java.util.stream.IntStream;

/**
 * {@code RangeUtil} is a utility class providing methods for generating streams of integers that
 * emulate the behaviour of Python's {@code range} function.
 * <p>
 * This class offers static methods to create ranges with various configurations. These methods
 * leverage the {@link IntStream} to provide efficient and versatile integer sequences.
 *
 * @author zihluwang
 * @see IntStream
 */
public final class RangeUtil {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private RangeUtil() {
    }

    /**
     * Generates a stream of integers starting from {@code 0} up to the specified {@code end} value.
     * <p>
     * It creates a sequential, ordered {@code IntStream} that can be used for iteration or
     * further processing.
     * <p>
     * <b>Example Usage:</b>
     * <pre>{@code
     * RangeUtil.range(5).forEach(System.out::println);
     *
     * // Output:
     * // 0
     * // 1
     * // 2
     * // 3
     * // 4
     * }</pre>
     *
     * @param end upper-bound of the range (exclusive)
     * @return an {@code IntStream} of integers from {@code 0} (inclusive) to
     * {@code end} (exclusive)
     * @throws IllegalArgumentException if the given {@code end} value is less equal to 0
     * @see IntStream
     */
    public static IntStream range(int end) {
        if (end <= 0) {
            throw new IllegalArgumentException("Parameter [end] should not be less than or equal to 0, provided: " +
                    end);
        }
        return IntStream.range(0, end);
    }

    /**
     * Generates a stream of integers starting from the specified {@code start} value up to the
     * specified {@code end} value.
     * <p>
     * It creates a sequential, ordered {@code IntStream} that can be used for iteration or
     * further processing.
     * <p>
     * If {@code start} is less than {@code end}, an ascending range (exclusive of {@code end})
     * is generated. If {@code start} is greater than {@code end}, a descending range (exclusive of {@code end})
     * is generated. If {@code start} equals {@code end}, an empty stream is returned.
     * <p>
     * <b>Example Usage:</b>
     * <pre>{@code
     * RangeUtil.range(3, 8).forEach(System.out::println);
     *
     * // Output:
     * // 3
     * // 4
     * // 5
     * // 6
     * // 7
     *
     * RangeUtil.range(8, 3).forEach(System.out::println);
     *
     * // Output:
     * // 8
     * // 7
     * // 6
     * // 5
     * // 4
     * }</pre>
     *
     * @param start the starting value of the range (inclusive)
     * @param end   upper-bound of the range (exclusive)
     * @return an {@code IntStream} of integers in ascending or descending order, exclusive of {@code end}
     * @see IntStream
     */
    public static IntStream range(int start, int end) {
        if (start == end) {
            return IntStream.empty();
        }
        if (start < end) {
            return IntStream.range(start, end);
        } else {
            // Descending range (exclusive of end)
            return IntStream.iterate(start, n -> n > end, n -> n - 1);
        }
    }

    /**
     * Generates a stream of integers starting from the specified {@code start} value up to the
     * specified {@code end} value.
     * <p>
     * It creates a sequential, ordered {@code IntStream} that can be used for iteration or
     * further processing.
     * <p>
     * The range includes both {@code start} and {@code end}.
     * <p>
     * <b>Example Usage:</b>
     * <pre>{@code
     * RangeUtil.rangeClosed(3, 8).forEach(System.out::println);
     *
     * // Output:
     * // 3
     * // 4
     * // 5
     * // 6
     * // 7
     * // 8
     * }</pre>
     *
     * @param start the starting value of the range (inclusive)
     * @param end   upper-bound of the range (inclusive)
     * @return an {@code IntStream} of integers from {@code start} to {@code end} inclusive
     * @see IntStream
     */
    public static IntStream rangeClosed(int start, int end) {
        return IntStream.rangeClosed(start, end);
    }

    /**
     * Generates a stream of integers starting from the specified {@code start} value, incremented by
     * the specified {@code step}, up to the specified {@code end} value.
     * <p>
     * It creates a sequential, ordered {@code IntStream} that can be used for iteration or
     * further processing.
     * <p>
     * The stream excludes the {@code end} value.
     * <p>
     * <b>Example Usage:</b>
     * <pre>{@code
     * RangeUtil.range(3, 10, 2).forEach(System.out::println);
     *
     * // Output:
     * // 3
     * // 5
     * // 7
     * // 9
     *
     * RangeUtil.range(10, 3, -2).forEach(System.out::println);
     *
     * // Output:
     * // 10
     * // 8
     * // 6
     * // 4
     * }</pre>
     *
     * @param start the starting value of the range (inclusive)
     * @param end   upper-bound of the range (exclusive)
     * @param step  the increment or decrement between each value (non-zero)
     * @return an {@code IntStream} of integers from {@code start} to {@code end} exclusive stepping by {@code step}
     * @throws IllegalArgumentException if {@code step} is zero or if {@code start} and {@code end} are inconsistent
     *                                  with the direction imposed by {@code step}
     * @see IntStream
     */
    public static IntStream range(int start, int end, int step) {
        if (step == 0) {
            throw new IllegalArgumentException("Step value must not be zero.");
        }
        if ((step > 0 && start >= end) || (step < 0 && start <= end)) {
            throw new IllegalArgumentException("Range parameters are inconsistent with the step value.");
        }
        return IntStream.iterate(start, (n) -> step > 0 ? n < end : n > end, (n) -> n + step);
    }

}
