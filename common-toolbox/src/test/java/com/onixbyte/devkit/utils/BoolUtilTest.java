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

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoolUtilTest {

    // Tests for and(Boolean... values)

    @Test
    void and_AllTrueValues_ReturnsTrue() {
        assertTrue(BoolUtil.and(true, true, true));
    }

    @Test
    void and_SomeFalseValues_ReturnsFalse() {
        assertFalse(BoolUtil.and(true, false, true));
    }

    @Test
    void and_AllFalseValues_ReturnsFalse() {
        assertFalse(BoolUtil.and(false, false));
    }

    @Test
    void and_WithNullValues_IgnoresNulls() {
        assertTrue(BoolUtil.and(true, null, true));
        assertFalse(BoolUtil.and(true, null, false));
    }

    @Test
    void and_AllNullValues_ReturnsTrue() {
        // Stream after filtering null is empty, allMatch on empty returns true
        assertTrue(BoolUtil.and((Boolean) null, null));
    }

    // Tests for and(BooleanSupplier... valueSuppliers)

    @Test
    void and_AllSuppliersTrue_ReturnsTrue() {
        BooleanSupplier trueSupplier = () -> true;
        BooleanSupplier falseSupplier = () -> false;

        assertTrue(BoolUtil.and(trueSupplier, trueSupplier));
        assertFalse(BoolUtil.and(trueSupplier, falseSupplier));
    }

    @Test
    void and_WithNullSuppliers_IgnoresNull() {
        BooleanSupplier trueSupplier = () -> true;

        assertTrue(BoolUtil.and(trueSupplier, null, trueSupplier));
        assertFalse(BoolUtil.and(trueSupplier, null, () -> false));
    }

    @Test
    void and_AllNullSuppliers_ReturnsTrue() {
        assertTrue(BoolUtil.and((BooleanSupplier) null, null));
    }


    // Tests for or(Boolean... values)

    @Test
    void or_AllTrueValues_ReturnsTrue() {
        assertTrue(BoolUtil.or(true, true, true));
    }

    @Test
    void or_SomeTrueValues_ReturnsTrue() {
        assertTrue(BoolUtil.or(false, true, false));
    }

    @Test
    void or_AllFalseValues_ReturnsFalse() {
        assertFalse(BoolUtil.or(false, false));
    }

    @Test
    void or_WithNullValues_IgnoresNull() {
        assertTrue(BoolUtil.or(false, null, true));
        assertFalse(BoolUtil.or(false, null, false));
    }

    @Test
    void or_AllNullValues_ReturnsFalse() {
        // Stream after filtering null is empty, anyMatch on empty returns false
        assertFalse(BoolUtil.or((Boolean) null, null));
    }

    // Tests for or(BooleanSupplier... valueSuppliers)

    @Test
    void or_AllSuppliersTrue_ReturnsTrue() {
        BooleanSupplier trueSupplier = () -> true;
        BooleanSupplier falseSupplier = () -> false;

        assertTrue(BoolUtil.or(trueSupplier, trueSupplier));
        assertTrue(BoolUtil.or(falseSupplier, trueSupplier));
        assertFalse(BoolUtil.or(falseSupplier, falseSupplier));
    }

    @Test
    void or_WithNullSuppliers_IgnoresNull() {
        BooleanSupplier trueSupplier = () -> true;
        BooleanSupplier falseSupplier = () -> false;

        assertTrue(BoolUtil.or(falseSupplier, null, trueSupplier));
        assertFalse(BoolUtil.or(falseSupplier, null, falseSupplier));
    }

    @Test
    void or_AllNullSuppliers_ReturnsFalse() {
        assertFalse(BoolUtil.or((BooleanSupplier) null, null));
    }
}
