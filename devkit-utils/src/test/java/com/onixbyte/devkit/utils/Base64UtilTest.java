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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class Base64UtilTest {

    @Test
    void testEncodeAndDecodeWithUtf8() {
        var original = "Hello, Base64!";
        var encoded = Base64Util.encode(original);
        assertNotNull(encoded);
        assertNotEquals(original, encoded);

        var decoded = Base64Util.decode(encoded);
        assertNotNull(decoded);
        assertEquals(original, decoded);
    }

    @Test
    void testEncodeAndDecodeWithCharset() {
        var original = "编码测试";  // Some unicode characters (Chinese)
        var charset = StandardCharsets.UTF_8;

        var encoded = Base64Util.encode(original, charset);
        assertNotNull(encoded);
        assertNotEquals(original, encoded);

        var decoded = Base64Util.decode(encoded, charset);
        assertNotNull(decoded);
        assertEquals(original, decoded);
    }

    @Test
    void testEncodeUrlComponentsAndDecodeWithUtf8() {
        var original = "This is a test for URL-safe Base64 encoding+!";

        var encodedUrl = Base64Util.encodeUrlComponents(original);
        assertNotNull(encodedUrl);
        assertNotEquals(original, encodedUrl);
        // URL-safe encoding should not contain '+' or '/' characters
        assertFalse(encodedUrl.contains("+"));
        assertFalse(encodedUrl.contains("/"));

        var decodedUrl = Base64Util.decodeUrlComponents(encodedUrl);
        assertNotNull(decodedUrl);
        assertEquals(original, decodedUrl);
    }

    @Test
    void testEncodeUrlComponentsAndDecodeWithCharset() {
        var original = "测试 URL 安全编码";  // Unicode string
        var charset = StandardCharsets.UTF_8;

        var encodedUrl = Base64Util.encodeUrlComponents(original, charset);
        assertNotNull(encodedUrl);
        assertNotEquals(original, encodedUrl);

        var decodedUrl = Base64Util.decodeUrlComponents(encodedUrl, charset);
        assertNotNull(decodedUrl);
        assertEquals(original, decodedUrl);
    }

    @Test
    void testEncodeAndDecodeEmptyString() {
        var original = "";

        var encoded = Base64Util.encode(original);
        assertNotNull(encoded);
        assertEquals("", Base64Util.decode(encoded));
    }

    @Test
    void testEncodeAndDecodeNullSafety() {
        // Since Base64Util does not explicitly handle null, the test expects NPE if null is input
        assertThrows(NullPointerException.class, () -> Base64Util.encode(null));
        assertThrows(NullPointerException.class, () -> Base64Util.decode(null));
    }

}
