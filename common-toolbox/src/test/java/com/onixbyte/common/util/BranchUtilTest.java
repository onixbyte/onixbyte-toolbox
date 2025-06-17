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

package com.onixbyte.common.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class BranchUtilTest {

    // Test the static methods or(Boolean... values) and and(Boolean... values)
    @Test
    void testOrWithBooleanValues() {
        BranchUtil trueResult = BranchUtil.or(true, false, false);
        assertNotNull(trueResult);

        BranchUtil falseResult = BranchUtil.or(false, false, false);
        assertNotNull(falseResult);
    }

    @Test
    void testAndWithBooleanValues() {
        BranchUtil trueResult = BranchUtil.and(true, true, true);
        assertNotNull(trueResult);

        BranchUtil falseResult = BranchUtil.and(true, false, true);
        assertNotNull(falseResult);
    }

    // Test the static methods or(BooleanSupplier... valueSuppliers) and and(BooleanSupplier... valueSuppliers)
    @Test
    void testOrWithBooleanSuppliers() {
        BooleanSupplier trueSupplier = () -> true;
        BooleanSupplier falseSupplier = () -> false;

        BranchUtil trueResult = BranchUtil.or(falseSupplier, trueSupplier);

        BranchUtil falseResult = BranchUtil.or(falseSupplier, falseSupplier);
    }

    @Test
    void testAndWithBooleanSuppliers() {
        BooleanSupplier trueSupplier = () -> true;
        BooleanSupplier falseSupplier = () -> false;

        BranchUtil trueResult = BranchUtil.and(trueSupplier, trueSupplier);

        BranchUtil falseResult = BranchUtil.and(trueSupplier, falseSupplier);
    }

    // Test thenSupply(T, T)
    @Test
    void testThenSupplyBothSuppliers_ResultTrue() {
        BranchUtil b = BranchUtil.and(true);
        String trueVal = "yes";
        String falseVal = "no";

        String result = b.thenSupply(() -> trueVal, () -> falseVal);
        assertEquals(trueVal, result);
    }

    @Test
    void testThenSupplyBothSuppliers_ResultFalse_WithFalseSupplier() {
        BranchUtil b = BranchUtil.and(false);
        String trueVal = "yes";
        String falseVal = "no";

        String result = b.thenSupply(() -> trueVal, () -> falseVal);
        assertEquals(falseVal, result);
    }

    @Test
    void testThenSupplyBothSuppliers_ResultFalse_NoFalseSupplier() {
        BranchUtil b = BranchUtil.and(false);
        String trueVal = "yes";

        String result = b.thenSupply(() -> trueVal, null);
        assertNull(result);
    }

    @Test
    void testThenSupplySingleTrueSupplier_ResultTrue() {
        BranchUtil b = BranchUtil.and(true);
        String trueVal = "success";

        String result = b.thenSupply(() -> trueVal);
        assertEquals(trueVal, result);
    }

    @Test
    void testThenSupplySingleTrueSupplier_ResultFalse() {
        BranchUtil b = BranchUtil.and(false);
        String trueVal = "success";

        String result = b.thenSupply(() -> trueVal);
        assertNull(result);
    }

    // Test then(Runnable, Runnable)
    @Test
    void testThenWithBothHandlers_ResultTrue() {
        BranchUtil b = BranchUtil.and(true);
        AtomicBoolean trueRun = new AtomicBoolean(false);
        AtomicBoolean falseRun = new AtomicBoolean(false);

        b.then(() -> trueRun.set(true), () -> falseRun.set(true));

        assertTrue(trueRun.get());
        assertFalse(falseRun.get());
    }

    @Test
    void testThenWithBothHandlers_ResultFalse() {
        BranchUtil b = BranchUtil.and(false);
        AtomicBoolean trueRun = new AtomicBoolean(false);
        AtomicBoolean falseRun = new AtomicBoolean(false);

        b.then(() -> trueRun.set(true), () -> falseRun.set(true));

        assertFalse(trueRun.get());
        assertTrue(falseRun.get());
    }

    @Test
    void testThenWithOnlyTrueHandler_ResultTrue() {
        BranchUtil b = BranchUtil.and(true);
        AtomicBoolean trueRun = new AtomicBoolean(false);

        b.then(() -> trueRun.set(true));

        assertTrue(trueRun.get());
    }

    @Test
    void testThenWithOnlyTrueHandler_ResultFalse() {
        BranchUtil b = BranchUtil.and(false);
        AtomicBoolean trueRun = new AtomicBoolean(false);

        b.then(() -> trueRun.set(true));

        assertFalse(trueRun.get());
    }
}
