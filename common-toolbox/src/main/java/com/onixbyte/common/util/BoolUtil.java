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

package com.onixbyte.common.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * The {@link BoolUtil} class provides utility methods for boolean calculations.
 * This class offers methods to perform logical operations such as AND, OR, and NOT on boolean values.
 * <p>
 * The utility methods in this class are useful for scenarios where multiple boolean values need to be
 * evaluated together, and for simplifying complex boolean expressions.
 * </p>
 * 
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * boolean result1 = BoolUtil.and(true, true, false); // false
 * boolean result2 = BoolUtil.or(true, false, false); // true
 * boolean result3 = BoolUtil.not(false); // true
 * }</pre>
 *
 * @author zihluwang
 * @version 3.0.0
 */
public final class BoolUtil {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private BoolUtil() {
    }

    /**
     * Logical and calculation.
     *
     * @param values the values to be calculated
     * @return {@code true} if all value in values is {@code true}, otherwise {@code false}
     */
    public static boolean and(Boolean... values) {
        return Arrays.stream(values)
                .filter(Objects::nonNull)
                .allMatch(Boolean::booleanValue);
    }

    /**
     * Logical and calculation.
     *
     * @param valueSuppliers the suppliers of value to be calculated
     * @return {@code true} if all value in values is {@code true}, otherwise {@code false}
     */
    public static boolean and(BooleanSupplier... valueSuppliers) {
        return Arrays.stream(valueSuppliers)
                .filter(Objects::nonNull)
                .allMatch(BooleanSupplier::getAsBoolean);
    }

    /**
     * Logical or calculation.
     *
     * @param values the values to be calculated
     * @return {@code true} if any value in values is {@code true}, otherwise {@code false}
     */
    public static boolean or(Boolean... values) {
        return Arrays.stream(values)
                .filter(Objects::nonNull)
                .anyMatch(Boolean::booleanValue);
    }

    /**
     * Logical or calculation.
     *
     * @param valueSuppliers the suppliers of value to be calculated
     * @return {@code true} if any value in values is {@code true}, otherwise {@code false}
     */
    public static boolean or(BooleanSupplier... valueSuppliers) {
        return Arrays.stream(valueSuppliers)
                .filter(Objects::nonNull)
                .anyMatch(BooleanSupplier::getAsBoolean);
    }
}
