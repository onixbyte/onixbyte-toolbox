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

package com.onixbyte.tuple;

/**
 * Represents an immutable pair of two elements, referred to as 'left' and 'right'. This class
 * provides a simple way to group two values without the need to create a custom class for each
 * specific pair.
 * <p>
 * The generic types {@code L} and {@code R} denote the types of the left and right elements,
 * respectively. Instances of this class are immutable once created.
 *
 * @param <L>   the type of the left element
 * @param <R>   the type of the right element
 * @param left  the left element of this tuple
 * @param right the right element of this tuple
 * @author siujamo
 * @author zihluwang
 */
public record ImmutableBiTuple<L, R>(
    L left,
    R right
) {

    /**
     * Creates a new {@code ImmutableBiTuple} with the specified left and right elements.
     *
     * @param <L>   the type of the left element
     * @param <R>   the type of the right element
     * @param left  the left element
     * @param right the right element
     * @return a new {@code ImmutableBiTuple} containing the specified elements
     */
    public static <L, R> ImmutableBiTuple<L, R> of(L left, R right) {
        return new ImmutableBiTuple<>(left, right);
    }
}
