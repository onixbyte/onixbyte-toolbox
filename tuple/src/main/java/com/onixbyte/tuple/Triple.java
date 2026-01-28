/*
 * Copyright (c) 2024-2026 OnixByte
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

import java.util.Objects;

/**
 * Represents a mutable triple of three elements, referred to as 'left', 'middle' and 'right'.
 * This class provides a way to group three values of different types.
 * <p>
 * The generic types {@code L}, {@code M} and {@code R} denote the types of the left, middle and
 * right elements respectively.
 *
 * @param <L> the type of the left element
 * @param <M> the type of the middle element
 * @param <R> the type of the right element
 * @author siujamo
 * @author zihluwang
 */
public final class Triple<L, M, R> {

    private L left;
    private M middle;
    private R right;

    /**
     * Constructs a new {@code Triple} with the given left, middle and right elements.
     *
     * @param left   the left element
     * @param middle the middle element
     * @param right  the right element
     */
    public Triple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    /**
     * Retrieves the left element of the triple.
     *
     * @return the left element
     */
    public L getLeft() {
        return left;
    }

    /**
     * Sets the left element of the triple.
     *
     * @param left the new left element
     */
    public void setLeft(L left) {
        this.left = left;
    }

    /**
     * Retrieves the middle element of the triple.
     *
     * @return the middle element
     */
    public M getMiddle() {
        return middle;
    }

    /**
     * Sets the middle element of the triple.
     *
     * @param middle the new middle element
     */
    public void setMiddle(M middle) {
        this.middle = middle;
    }

    /**
     * Retrieves the right element of the triple.
     *
     * @return the right element
     */
    public R getRight() {
        return right;
    }

    /**
     * Sets the right element of the triple.
     *
     * @param right the new right element
     */
    public void setRight(R right) {
        this.right = right;
    }

    /**
     * Checks if this {@code Triple} is equal to the specified object. Two {@code Triple}s are
     * considered equal if their left, middle and right elements are equal.
     *
     * @param object the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Triple<?, ?, ?> triple)) return false;
        return Objects.equals(left, triple.left) &&
            Objects.equals(middle, triple.middle) &&
            Objects.equals(right, triple.right);
    }

    /**
     * Calculates the hash code for this {@code Triple} based on its left, middle and
     * right elements.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(left, middle, right);
    }

    /**
     * Returns a string representation of this {@code Triple}, including its left, middle and
     * right elements.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "Triple{" +
            "left=" + left +
            ", middle=" + middle +
            ", right=" + right +
            '}';
    }

    /**
     * Factory method to create a new {@code Triple} instance with the given left, middle and right elements.
     *
     * @param left   the left element
     * @param middle the middle element
     * @param right  the right element
     * @param <L>    the type of the left element
     * @param <M>    the type of the middle element
     * @param <R>    the type of the right element
     * @return a new {@code Triple} instance
     */
    public static <L, M, R> Triple<L, M, R> of(L left, M middle, R right) {
        return new Triple<>(left, middle, right);
    }
}
