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
 * <pre>
 * {@code
 * boolean result1 = BoolUtil.and(true, true, false); // false
 * boolean result2 = BoolUtil.or(true, false, false); // true
 * boolean result3 = BoolUtil.not(false); // true
 * }
 * </pre>
 *
 * @author zihluwang
 * @version 1.6.2
 * @since 1.6.2
 */
public final class BoolUtil {

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

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private BoolUtil() {}

}
