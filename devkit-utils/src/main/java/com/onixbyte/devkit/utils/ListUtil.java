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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A utility class providing static methods for manipulating lists.
 *
 * @author siujamo
 * @author zihluwang
 */
public final class ListUtil {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ListUtil() {
    }

    /**
     * Splits a given List into a List of sub lists, where each sublist contains at most
     * {@code maxSize} elements. The original list is not modified, and new sub lists are created
     * to hold the partitioned data.
     * <p>
     * If the original list's size is less than or equal to {@code maxSize}, a single sublist
     * containing all elements is returned. If the list is empty, an empty list of sub lists
     * is returned.
     *
     * @param <T>          the type of elements in the list
     * @param originalList the list to be split, must not be null
     * @param maxSize      the maximum number of elements in each sublist, must be positive
     * @param listFactory  list factory
     * @return a List of sub lists, where each sublist has at most {@code maxSize} elements
     * @throws IllegalArgumentException if {@code originalList} is null or {@code maxSize} is less
     *                                  than or equal to 0
     */
    public static <T> List<List<T>> chunk(List<T> originalList, int maxSize, Supplier<List<T>> listFactory) {
        // check input
        if (Objects.isNull(originalList)) {
            throw new IllegalArgumentException("List cannot be null");
        }

        if (maxSize <= 0) {
            throw new IllegalArgumentException("Max size should be greater than 0");
        }

        if (Objects.isNull(listFactory)) {
            throw new IllegalArgumentException("List factory cannot be null");
        }

        var result = new ArrayList<List<T>>();
        var size = originalList.size();

        // if the original list is empty or smaller than maxSize, return it as a single sublist
        if (size <= maxSize) {
            var singleSubList = listFactory.get();
            singleSubList.addAll(originalList);
            result.add(singleSubList);
            return result;
        }

        // split the list
        for (var i = 0; i < size; i += maxSize) {
            var end = Math.min(i + maxSize, size); // ensure not to exceed list length
            var subList = originalList.subList(i, end);
            var subListWrapper = listFactory.get();
            subListWrapper.addAll(subList);
            result.add(subListWrapper); // create a new list to avoid reference issues
        }

        return result;
    }

    /**
     * Splits a given List into a List of sub lists, where each sublist contains at most
     * {@code maxSize} elements. The original list is not modified, and new sub lists are created
     * to hold the partitioned data.
     * <p>
     * If the original list's size is less than or equal to {@code maxSize}, a single sublist
     * containing all elements is returned. If the list is empty, an empty list of sub lists
     * is returned.
     *
     * @param <T>          the type of elements in the list
     * @param originalList the list to be split, must not be null
     * @param maxSize      the maximum number of elements in each sublist, must be positive
     * @return a List of sub lists, where each sublist has at most {@code maxSize} elements
     * @throws IllegalArgumentException if {@code originalList} is null or {@code maxSize} is less
     *                                  than or equal to 0
     * @see #chunk(List, int, Supplier)
     */
    public static <T> List<List<T>> chunk(List<T> originalList, int maxSize) {
        return chunk(originalList, maxSize, ArrayList::new);
    }

}
