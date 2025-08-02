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
public record ImmutableTuple<L, R>(
    L left,
    R right
) {

    /**
     * Creates a new {@code ImmutableTuple} with the specified left and right elements.
     *
     * @param <L>   the type of the left element
     * @param <R>   the type of the right element
     * @param left  the left element
     * @param right the right element
     * @return a new {@code ImmutableTuple} containing the specified elements
     */
    public static <L, R> ImmutableTuple<L, R> of(L left, R right) {
        return new ImmutableTuple<>(left, right);
    }
}
