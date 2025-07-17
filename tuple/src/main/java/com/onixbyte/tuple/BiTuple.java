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
 * Represents an ordered pair of elements, where the first element is of type {@code L} and the
 * second is of type {@code R}. This class provides a simple way to group two related
 * values together.
 * <p>
 * The class is mutable, allowing the values of the left and right elements to be changed
 * after creation. It also overrides the {@code equals}, {@code hashCode}, and {@code toString}
 * methods to provide meaningful comparisons and string representations.
 *
 * @param <L> the type of the left element
 * @param <R> the type of the right element
 * @author siujamo
 * @author zihluwang
 */
public final class BiTuple<L, R> {

    /**
     * The left element of the tuple.
     */
    private L left;

    /**
     * The right element of the tuple.
     */
    private R right;

    /**
     * Constructs a new {@code BiTuple} with the specified left and right elements.
     *
     * @param left  the left element to be stored in the tuple
     * @param right the right element to be stored in the tuple
     */
    public BiTuple(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Retrieves the left element of the tuple.
     *
     * @return the left element of the tuple
     */
    public L getLeft() {
        return left;
    }

    /**
     * Sets the left element of the tuple to the specified value.
     *
     * @param left the new value for the left element of the tuple
     */
    public void setLeft(L left) {
        this.left = left;
    }

    /**
     * Retrieves the right element of the tuple.
     *
     * @return the right element of the tuple
     */
    public R getRight() {
        return right;
    }

    /**
     * Sets the right element of the tuple to the specified value.
     *
     * @param right the new value for the right element of the tuple
     */
    public void setRight(R right) {
        this.right = right;
    }

    /**
     * Compares this {@code BiTuple} with the specified object for equality.
     * <p>
     * Two {@code BiTuple}s are considered equal if they are of the same type and their left and
     * right elements are equal according to their respective {@code equals} methods.
     *
     * @param object the object to compare with this {@code BiTuple}
     * @return {@code true} if the specified object is equal to this {@code BiTuple},
     * {@code false} otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BiTuple<?, ?> biTuple)) return false;
        return Objects.equals(left, biTuple.left) && Objects.equals(right, biTuple.right);
    }

    /**
     * Returns a hash code value for the {@code BiTuple}.
     * <p>
     * The hash code is calculated based on the hash codes of the left and right elements.
     *
     * @return a hash code value for this {@code BiTuple}
     */
    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    /**
     * Returns a string representation of the {@code BiTuple}.
     * <p>
     * The string representation consists of the class name, followed by the values of
     * the left and right elements in the format {@code "BiTuple{left=value1, right=value2}"}.
     *
     * @return a string representation of the {@code BiTuple}
     */
    @Override
    public String toString() {
        return "BiTuple{" +
            "left=" + left +
            ", right=" + right +
            '}';
    }

    /**
     * Creates a new {@code BiTuple} with the specified left and right elements.
     *
     * @param <L>   the type of the left element
     * @param <R>   the type of the right element
     * @param left  the left element
     * @param right the right element
     * @return a new {@code BiTuple} containing the specified elements
     */
    public static <L, R> BiTuple<L, R> of(L left, R right) {
        return new BiTuple<>(left, right);
    }
}
