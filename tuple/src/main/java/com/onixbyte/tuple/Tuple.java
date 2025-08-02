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
public final class Tuple<L, R> {

    private L left;
    private R right;

    /**
     * Constructs a new {@code Tuple} with the specified left and right elements.
     *
     * @param left  the left element to be stored in the tuple
     * @param right the right element to be stored in the tuple
     */
    public Tuple(L left, R right) {
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
     * Compares this {@code Tuple} with the specified object for equality.
     * <p>
     * Two {@code Tuple}s are considered equal if they are of the same type and their left and
     * right elements are equal according to their respective {@code equals} methods.
     *
     * @param object the object to compare with this {@code Tuple}
     * @return {@code true} if the specified object is equal to this {@code Tuple},
     * {@code false} otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tuple<?, ?> tuple)) return false;
        return Objects.equals(left, tuple.left) && Objects.equals(right, tuple.right);
    }

    /**
     * Returns a hash code value for the {@code Tuple}.
     * <p>
     * The hash code is calculated based on the hash codes of the left and right elements.
     *
     * @return a hash code value for this {@code Tuple}
     */
    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    /**
     * Returns a string representation of the {@code Tuple}.
     * <p>
     * The string representation consists of the class name, followed by the values of
     * the left and right elements in the format {@code "Tuple{left=value1, right=value2}"}.
     *
     * @return a string representation of the {@code Tuple}
     */
    @Override
    public String toString() {
        return "Tuple{" +
            "left=" + left +
            ", right=" + right +
            '}';
    }

    /**
     * Creates a new {@code Tuple} with the specified left and right elements.
     *
     * @param <L>   the type of the left element
     * @param <R>   the type of the right element
     * @param left  the left element
     * @param right the right element
     * @return a new {@code Tuple} containing the specified elements
     */
    public static <L, R> Tuple<L, R> of(L left, R right) {
        return new Tuple<>(left, right);
    }
}
