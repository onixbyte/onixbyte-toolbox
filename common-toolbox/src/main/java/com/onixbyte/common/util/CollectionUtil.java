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

package com.onixbyte.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A utility class providing static methods for manipulating collections.
 *
 * @author zihluwang
 */
public final class CollectionUtil {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private CollectionUtil() {
    }

    /**
     * Splits a collection into a list of sub-collections, each with a maximum size specified by
     * the caller.
     * <p>
     * This method takes an original collection and divides it into smaller sub-collections,
     * ensuring that each sub-collection contains no more than the specified maximum size. If the
     * original collection's size is less than or equal to the maximum size, it is returned as a
     * single sub-collection. The sub-collections are created using the provided collection factory.
     *
     * @param <T>                the type of elements in the collection
     * @param <C>                the type of the collection, which must extend {@link Collection}
     * @param originalCollection the collection to be split into sub-collections
     * @param maxSize            the maximum number of elements allowed in each sub-collection
     * @param collectionFactory  a supplier that creates new instances of the sub-collection type
     * @return a list of sub-collections, each containing up to {@code maxSize} elements
     * @throws IllegalArgumentException if {@code originalCollection} is {@code null},
     *                                  {@code maxSize} is less than zero, or
     *                                  {@code collectionFactory} is {@code null}
     */
    public static <T, C extends Collection<T>> List<C> chunk(C originalCollection,
                                                             int maxSize,
                                                             Supplier<C> collectionFactory) {
        // check inputs
        if (Objects.isNull(originalCollection)) {
            throw new IllegalArgumentException("Collection must not be null.");
        }

        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize must greater than 0.");
        }

        if (Objects.isNull(collectionFactory)) {
            throw new IllegalArgumentException("Factory method cannot be null.");
        }

        var result = new ArrayList<C>();
        var size = originalCollection.size();

        // if original collection is empty or the size less than maxSize, return it as a single
        // sub collection
        if (size <= maxSize) {
            var singleCollection = collectionFactory.get();
            singleCollection.addAll(originalCollection);
            result.add(singleCollection);
            return result;
        }

        // use iterator to split the given collection
        var iter = originalCollection.iterator();
        var count = 0;
        var currentSubCollection = collectionFactory.get();

        while (iter.hasNext()) {
            var element = iter.next();
            currentSubCollection.add(element);
            count++;

            // add sub collection to result when current sub collection reached maxSize or
            // collection traverse is completed
            if (count % maxSize == 0 || !iter.hasNext()) {
                result.add(currentSubCollection);
                currentSubCollection = collectionFactory.get();
            }
        }

        return result;
    }

}
