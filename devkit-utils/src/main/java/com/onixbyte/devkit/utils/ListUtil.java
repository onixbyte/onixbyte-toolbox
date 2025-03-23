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

/**
 * A utility class for splitting a List into multiple sub lists, where each sublist has a maximum
 * number of elements specified by the user.
 *
 * @author siujamo
 */
public final class ListUtil {

    /**
     * Private constructor to prevent instantiation of this utility class.
     * <p>
     * This class provides static methods for list manipulation and is not intended to be
     * instantiated. The private constructor ensures that no instances can be created, enforcing
     * the utility nature of the class.
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
     * @return a List of sub lists, where each sublist has at most {@code maxSize} elements
     * @throws IllegalArgumentException if {@code originalList} is null or {@code maxSize} is less
     *                                  than or equal to 0
     */
    public static <T> List<List<T>> splitList(List<T> originalList, int maxSize) {
        // check input
        if (originalList == null || maxSize <= 0) {
            throw new IllegalArgumentException("List cannot be null and maxSize must be positive");
        }

        var result = new ArrayList<List<T>>();
        var size = originalList.size();

        // if the original list is empty or smaller than maxSize, return it as a single sublist
        if (size <= maxSize) {
            result.add(new ArrayList<>(originalList));
            return result;
        }

        // split the list
        for (var i = 0; i < size; i += maxSize) {
            var end = Math.min(i + maxSize, size); // ensure not to exceed list length
            List<T> subList = originalList.subList(i, end);
            result.add(new ArrayList<>(subList)); // create a new list to avoid reference issues
        }

        return result;
    }

}
