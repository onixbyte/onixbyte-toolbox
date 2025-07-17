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

import java.util.Objects;

/**
 * Represents an immutable triple of three elements, referred to as 'left', 'middle' and 'right'.
 * This class provides a way to group three values of different types.
 * <p>
 * The generic types {@code L}, {@code M} and {@code R} denote the types of the left, middle and right
 * elements respectively.
 *
 * @param <L> the type of the left element
 * @param <M> the type of the middle element
 * @param <R> the type of the right element
 * @author siujamo
 * @author zihluwang
 */
public final class TriTuple<L, M, R> {

    /**
     * The left element of the triple.
     */
    private L left;

    /**
     * The middle element of the triple.
     */
    private M middle;

    /**
     * The right element of the triple.
     */
    private R right;

    /**
     * Constructs a new {@code TriTuple} with the given left, middle and right elements.
     *
     * @param left   the left element
     * @param middle the middle element
     * @param right  the right element
     */
    public TriTuple(L left, M middle, R right) {
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
     * Checks if this {@code TriTuple} is equal to the specified object.
     * Two {@code TriTuple}s are considered equal if their left, middle and right elements are equal.
     *
     * @param object the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TriTuple<?, ?, ?> triTuple)) return false;
        return Objects.equals(left, triTuple.left) &&
            Objects.equals(middle, triTuple.middle) &&
            Objects.equals(right, triTuple.right);
    }

    /**
     * Calculates the hash code for this {@code TriTuple} based on its left, middle and right elements.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(left, middle, right);
    }

    /**
     * Returns a string representation of this {@code TriTuple}, including its left, middle and right elements.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "TriTuple{" +
            "left=" + left +
            ", middle=" + middle +
            ", right=" + right +
            '}';
    }

    /**
     * Factory method to create a new {@code TriTuple} instance with the given left, middle and right elements.
     *
     * @param left   the left element
     * @param middle the middle element
     * @param right  the right element
     * @param <L>    the type of the left element
     * @param <M>    the type of the middle element
     * @param <R>    the type of the right element
     * @return a new {@code TriTuple} instance
     */
    public static <L, M, R> TriTuple<L, M, R> of(L left, M middle, R right) {
        return new TriTuple<>(left, middle, right);
    }
}
