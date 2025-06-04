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

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class CollectionUtilTest {

    @Test
    void chunk_NullOriginalCollection_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> CollectionUtil.chunk(null, 3, ArrayList::new));
        assertEquals("Collection must not be null.", ex.getMessage());
    }

    @Test
    void chunk_NegativeMaxSize_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> CollectionUtil.chunk(List.of(1, 2), -1, ArrayList::new));
        assertEquals("maxSize must greater than 0.", ex.getMessage());
    }

    @Test
    void chunk_NullCollectionFactory_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> CollectionUtil.chunk(List.of(1, 2), 2, null));
        assertEquals("Factory method cannot be null.", ex.getMessage());
    }

    @Test
    void chunk_EmptyCollection_ReturnsOneEmptySubCollection() {
        List<List<Integer>> chunks = CollectionUtil.chunk(Collections.emptyList(), 3, ArrayList::new);
        assertEquals(1, chunks.size());
        assertTrue(chunks.get(0).isEmpty());
    }

    @Test
    void chunk_CollectionSizeLessThanMaxSize_ReturnsOneSubCollectionWithAllElements() {
        List<Integer> list = List.of(1, 2);
        List<List<Integer>> chunks = CollectionUtil.chunk(list, 5, ArrayList::new);
        assertEquals(1, chunks.size());
        assertEquals(list, chunks.get(0));
    }

    @Test
    void chunk_CollectionSizeEqualMaxSize_ReturnsOneSubCollectionWithAllElements() {
        List<Integer> list = List.of(1, 2, 3);
        List<List<Integer>> chunks = CollectionUtil.chunk(list, 3, ArrayList::new);
        assertEquals(1, chunks.size());
        assertEquals(list, chunks.get(0));
    }

    @Test
    void chunk_CollectionSizeGreaterThanMaxSize_ReturnsMultipleSubCollections() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7);
        int maxSize = 3;
        List<List<Integer>> chunks = CollectionUtil.chunk(list, maxSize, ArrayList::new);

        // Expect 3 subcollections: [1,2,3], [4,5,6], [7]
        assertEquals(3, chunks.size());
        assertEquals(List.of(1, 2, 3), chunks.get(0));
        assertEquals(List.of(4, 5, 6), chunks.get(1));
        assertEquals(List.of(7), chunks.get(2));
    }

    @Test
    void chunk_UsesDifferentCollectionTypeAsSubCollections() {
        LinkedList<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4));
        Supplier<LinkedList<Integer>> factory = LinkedList::new;
        List<LinkedList<Integer>> chunks = CollectionUtil.chunk(list, 2, factory);
        assertEquals(2, chunks.size());
        assertInstanceOf(LinkedList.class, chunks.get(0));
        assertInstanceOf(LinkedList.class, chunks.get(1));
        assertEquals(List.of(1, 2), chunks.get(0));
        assertEquals(List.of(3, 4), chunks.get(1));
    }

    @Test
    void chunk_CollectionWithOneElementAndMaxSizeOne_ReturnsOneSubCollection() {
        List<String> list = List.of("a");
        List<List<String>> chunks = CollectionUtil.chunk(list, 1, ArrayList::new);
        assertEquals(1, chunks.size());
        assertEquals(list, chunks.get(0));
    }

    @Test
    void chunk_MaxSizeZero_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> CollectionUtil.chunk(List.of(1), 0, ArrayList::new));
        assertEquals("maxSize must greater than 0.", ex.getMessage());
    }
}
