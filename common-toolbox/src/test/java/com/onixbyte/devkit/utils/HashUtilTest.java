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

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HashUtilTest {

    // Test MD2 hashing with explicit charset and default charset
    @Test
    void testMd2() {
        String input = "test";
        // Known MD2 hash of "test" with UTF-8
        String expectedHash = "dd34716876364a02d0195e2fb9ae2d1b";
        assertEquals(expectedHash, HashUtil.md2(input, StandardCharsets.UTF_8));
        assertEquals(expectedHash, HashUtil.md2(input));
        // Test null charset fallback to UTF-8
        assertEquals(expectedHash, HashUtil.md2(input, null));
    }

    // Test MD5 hashing with explicit charset and default charset
    @Test
    void testMd5() {
        String input = "test";
        // Known MD5 hash of "test"
        String expectedHash = "098f6bcd4621d373cade4e832627b4f6";
        assertEquals(expectedHash, HashUtil.md5(input, StandardCharsets.UTF_8));
        assertEquals(expectedHash, HashUtil.md5(input));
        assertEquals(expectedHash, HashUtil.md5(input, null));
    }

    // Test SHA-1 hashing with explicit charset and default charset
    @Test
    void testSha1() {
        String input = "test";
        // Known SHA-1 hash of "test"
        String expectedHash = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
        assertEquals(expectedHash, HashUtil.sha1(input, StandardCharsets.UTF_8));
        assertEquals(expectedHash, HashUtil.sha1(input));
        assertEquals(expectedHash, HashUtil.sha1(input, null));
    }

    // Test SHA-224 hashing with explicit charset and default charset
    @Test
    void testSha224() {
        String input = "test";
        // Known SHA-224 hash of "test"
        String expectedHash = "90a3ed9e32b2aaf4c61c410eb925426119e1a9dc53d4286ade99a809";
        assertEquals(expectedHash, HashUtil.sha224(input, StandardCharsets.UTF_8));
        assertEquals(expectedHash, HashUtil.sha224(input));
        assertEquals(expectedHash, HashUtil.sha224(input, null));
    }

    // Test SHA-256 hashing with explicit charset and default charset
    @Test
    void testSha256() {
        String input = "test";
        // Known SHA-256 hash of "test"
        String expectedHash = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        assertEquals(expectedHash, HashUtil.sha256(input, StandardCharsets.UTF_8));
        assertEquals(expectedHash, HashUtil.sha256(input));
        assertEquals(expectedHash, HashUtil.sha256(input, null));
    }

    // Test SHA-384 hashing with explicit charset and default charset
    @Test
    void testSha384() {
        String input = "test";
        // Known SHA-384 hash of "test"
        String expectedHash = "768412320f7b0aa5812fce428dc4706b3cae50e02a64caa16a782249bfe8efc4b7ef1ccb126255d196047dfedf17a0a9";
        assertEquals(expectedHash, HashUtil.sha384(input, StandardCharsets.UTF_8));
        assertEquals(expectedHash, HashUtil.sha384(input));
        assertEquals(expectedHash, HashUtil.sha384(input, null));
    }

    // Test SHA-512 hashing with explicit charset and default charset
    @Test
    void testSha512() {
        String input = "test";
        // Known SHA-512 hash of "test"
        String expectedHash = "ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff";
        // remove all whitespace in expected to match format generated
        expectedHash = expectedHash.replaceAll("\\s+", "");
        assertEquals(expectedHash, HashUtil.sha512(input, StandardCharsets.UTF_8));
        assertEquals(expectedHash, HashUtil.sha512(input));
        assertEquals(expectedHash, HashUtil.sha512(input, null));
    }

    // Test empty string input
    @Test
    void testEmptyString() {
        String input = "";
        // MD5 hash of empty string
        String expectedMd5 = "d41d8cd98f00b204e9800998ecf8427e";
        assertEquals(expectedMd5, HashUtil.md5(input));
        // SHA-256 hash of empty string
        String expectedSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        assertEquals(expectedSha256, HashUtil.sha256(input));
    }

    // Test null charset fallback for one algorithm as a sample
    @Test
    void testNullCharsetFallsBackToUtf8() {
        String input = "abc";
        String hashWithNull = HashUtil.md5(input, null);
        String hashWithUtf8 = HashUtil.md5(input, StandardCharsets.UTF_8);
        assertEquals(hashWithUtf8, hashWithNull);
    }
}
