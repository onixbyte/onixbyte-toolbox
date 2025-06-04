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

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class RangeUtilTest {

    // Test range(end) with normal positive end value
    @Test
    void testRangeEndValid() {
        IntStream stream = RangeUtil.range(5);
        int[] expected = {0, 1, 2, 3, 4};
        assertArrayEquals(expected, stream.toArray());
    }

    // Test range(end) with end less than or equal to zero should throw IllegalArgumentException
    @Test
    void testRangeEndInvalidThrows() {
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
                () -> RangeUtil.range(0));
        assertTrue(exception1.getMessage().contains("Parameter [end] should not less than 0"));

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> RangeUtil.range(-5));
        assertTrue(exception2.getMessage().contains("Parameter [end] should not less than 0"));
    }

    // Test range(start, end) with valid input where start < end
    @Test
    void testRangeStartEndValid() {
        IntStream stream = RangeUtil.range(3, 8);
        int[] expected = {3, 4, 5, 6, 7};
        assertArrayEquals(expected, stream.toArray());
    }

    // Test range(start, end) where start >= end should throw IllegalStateException
    @Test
    void testRangeStartEndInvalidThrows() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> RangeUtil.range(8, 3));
        assertTrue(exception.getMessage().contains("Parameter [start] should less than parameter [end]"));

        // Also test equal values
        IllegalStateException exceptionEqual = assertThrows(IllegalStateException.class,
                () -> RangeUtil.range(5, 5));
        assertTrue(exceptionEqual.getMessage().contains("Parameter [start] should less than parameter [end]"));
    }

    // Test rangeClosed(start, end) generates inclusive ranges correctly
    @Test
    void testRangeClosed() {
        IntStream stream = RangeUtil.rangeClosed(3, 8);
        int[] expected = {3, 4, 5, 6, 7, 8};
        assertArrayEquals(expected, stream.toArray());
    }

    // Test range(start, end, step) with positive step and valid parameters
    @Test
    void testRangeWithStepPositive() {
        IntStream stream = RangeUtil.range(3, 10, 2);
        int[] expected = {3, 5, 7, 9};
        assertArrayEquals(expected, stream.toArray());
    }

    // Test range(start, end, step) with negative step and valid parameters (descending range)
    @Test
    void testRangeWithStepNegative() {
        IntStream stream = RangeUtil.range(10, 3, -2);
        int[] expected = {10, 8, 6, 4};
        assertArrayEquals(expected, stream.toArray());
    }

    // Test range(start, end, step) throws IllegalArgumentException if step is zero
    @Test
    void testRangeWithStepZeroThrows() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> RangeUtil.range(0, 10, 0));
        assertEquals("Step value must not be zero.", exception.getMessage());
    }

    // Test range(start, end, step) throws if parameters inconsistent with step positive
    @Test
    void testRangeWithStepPositiveInvalidRangeThrows() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> RangeUtil.range(10, 5, 1));
        assertEquals("Range parameters are inconsistent with the step value.", exception.getMessage());
    }

    // Test range(start, end, step) throws if parameters inconsistent with step negative
    @Test
    void testRangeWithStepNegativeInvalidRangeThrows() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> RangeUtil.range(5, 10, -1));
        assertEquals("Range parameters are inconsistent with the step value.", exception.getMessage());
    }
}
