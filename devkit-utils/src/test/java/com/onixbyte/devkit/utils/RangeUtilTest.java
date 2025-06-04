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

import static org.junit.jupiter.api.Assertions.*;

class RangeUtilTest {

    /**
     * Tests generating ascending range from 0 up to end (exclusive).
     */
    @Test
    void testRangeEndValid() {
        int[] expected = {0, 1, 2, 3, 4};
        assertArrayEquals(expected, RangeUtil.range(5).toArray());
    }

    /**
     * Tests that range(end) throws IllegalArgumentException for end less than or equal to zero.
     */
    @Test
    void testRangeEndInvalidThrows() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class,
                () -> RangeUtil.range(0));
        assertTrue(ex1.getMessage().contains("should not be less than or equal to 0"));

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,
                () -> RangeUtil.range(-3));
        assertTrue(ex2.getMessage().contains("should not be less than or equal to 0"));
    }

    /**
     * Tests ascending range where start is less than end.
     */
    @Test
    void testRangeStartEndAscending() {
        int[] expected = {3, 4, 5, 6, 7};
        assertArrayEquals(expected, RangeUtil.range(3, 8).toArray());
    }

    /**
     * Tests descending range where start is greater than end.
     */
    @Test
    void testRangeStartEndDescending() {
        int[] expected = {8, 7, 6, 5, 4};
        assertArrayEquals(expected, RangeUtil.range(8, 3).toArray());
    }

    /**
     * Tests empty stream when start equals end.
     */
    @Test
    void testRangeStartEqualsEndReturnsEmpty() {
        assertEquals(0, RangeUtil.range(5, 5).count());
    }

    /**
     * Tests that rangeClosed generates inclusive range in ascending order.
     */
    @Test
    void testRangeClosedAscending() {
        int[] expected = {3, 4, 5, 6, 7, 8};
        assertArrayEquals(expected, RangeUtil.rangeClosed(3, 8).toArray());
    }

    /**
     * Tests range method with positive step generating ascending sequence.
     */
    @Test
    void testRangeWithPositiveStep() {
        int[] expected = {2, 4, 6, 8};
        assertArrayEquals(expected, RangeUtil.range(2, 10, 2).toArray());
    }

    /**
     * Tests range method with negative step generating descending sequence.
     */
    @Test
    void testRangeWithNegativeStep() {
        int[] expected = {10, 7, 4, 1};
        assertArrayEquals(expected, RangeUtil.range(10, 0, -3).toArray());
    }

    /**
     * Tests that passing zero step throws IllegalArgumentException.
     */
    @Test
    void testRangeStepZeroThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> RangeUtil.range(0, 10, 0));
        assertEquals("Step value must not be zero.", ex.getMessage());
    }

    /**
     * Tests that range with positive step but invalid start/end throws IllegalArgumentException.
     */
    @Test
    void testRangePositiveStepInvalidRangeThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> RangeUtil.range(10, 5, 1));
        assertEquals("Range parameters are inconsistent with the step value.", ex.getMessage());
    }

    /**
     * Tests that range with negative step but invalid start/end throws IllegalArgumentException.
     */
    @Test
    void testRangeNegativeStepInvalidRangeThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> RangeUtil.range(5, 10, -1));
        assertEquals("Range parameters are inconsistent with the step value.", ex.getMessage());
    }
}
