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
 * Represents an immutable triple of three elements, referred to as 'left', 'middle', and 'right'.
 * This class provides a generic way to group three values without the need to create a custom class
 * for each specific combination.
 * <p>
 * The generic types {@code L}, {@code M}, and {@code R} denote the types of the left, middle, and
 * right elements, respectively. Instances of this class are immutable once created.
 *
 * @param <L>    the type of the left element
 * @param <M>    the type of the middle element
 * @param <R>    the type of the right element
 * @param left   the left element of this triple
 * @param middle the middle element of this triple
 * @param right  the right element of this triple
 * @author siujamo
 * @author zihluwang
 */
public record ImmutableTriTuple<L, M, R>(
    L left,
    M middle,
    R right
) {

    /**
     * Creates a new {@code TriTuple} with the specified left, middle, and right elements.
     *
     * @param <L>    the type of the left element
     * @param <M>    the type of the middle element
     * @param <R>    the type of the right element
     * @param left   the left element
     * @param middle the middle element
     * @param right  the right element
     * @return a new {@code TriTuple} containing the specified elements
     */
    public static <L, M, R> ImmutableTriTuple<L, M, R> of(L left, M middle, R right) {
        return new ImmutableTriTuple<>(left, middle, right);
    }
}
