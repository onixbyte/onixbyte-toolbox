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

package com.onixbyte.common.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * The {@link Base64Util} class provides static methods to encode and decode strings with Base64
 * encoding. It utilizes the {@link Base64} class from the Java standard library for performing the
 * encoding and decoding operations. This utility class offers convenient methods to encode and
 * decode strings with different character sets.
 * <p>
 * This class is designed as a final class with a private constructor to prevent instantiation.
 * All methods in this class are static, allowing easy access to the Base64 encoding and
 * decoding functionality.
 * <p>
 * Example usage:
 * <pre>
 * String original = "Hello, World!";
 *
 * // Encode the string using UTF-8 charset
 * String encoded = Base64Util.encode(original);
 * System.out.println("Encoded string: " + encoded);
 *
 * // Decode the encoded string using UTF-8 charset
 * String decoded = Base64Util.decode(encoded);
 * System.out.println("Decoded string: " + decoded);
 * </pre>
 * <p>
 * <b>Note:</b> This utility class uses the default charset (UTF-8) if no specific charset is
 * provided. It is recommended to specify the charset explicitly to ensure consistent
 * encoding and decoding.
 *
 * @author zihluwang
 * @version 3.0.0
 */
public final class Base64Util {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Base64Util() {
    }

    private static Base64.Encoder encoder;
    private static Base64.Decoder decoder;
    private static Base64.Encoder urlEncoder;
    private static Base64.Decoder urlDecoder;

    /**
     * Ensure that there is only one Base64 Encoder.
     *
     * @return the {@link Base64.Encoder} instance
     */
    private static Base64.Encoder getEncoder() {
        if (Objects.isNull(encoder)) {
            encoder = Base64.getEncoder();
        }
        return encoder;
    }

    /**
     * Ensure that there is only one Base64 Encoder.
     *
     * @return the {@link Base64.Encoder} instance
     */
    private static Base64.Decoder getDecoder() {
        if (Objects.isNull(decoder)) {
            decoder = Base64.getDecoder();
        }
        return decoder;
    }

    /**
     * Ensure that there is only one Base64 URL Encoder.
     *
     * @return the {@link Base64.Encoder} instance
     */
    private static Base64.Encoder getUrlEncoder() {
        if (Objects.isNull(urlEncoder)) {
            urlEncoder = Base64.getUrlEncoder();
        }
        return urlEncoder;
    }

    /**
     * Ensure that there is only one Base64 URL Decoder.
     *
     * @return the {@link Base64.Encoder} instance
     */
    public static Base64.Decoder getUrlDecoder() {
        if (Objects.isNull(urlDecoder)) {
            urlDecoder = Base64.getUrlDecoder();
        }
        return urlDecoder;
    }

    /**
     * Encodes the given string using the specified charset.
     *
     * @param value   the string to be encoded
     * @param charset the charset to be used for encoding
     * @return the Base64 encoded string
     */
    public static String encode(String value, Charset charset) {
        var encoded = getEncoder().encode(value.getBytes(charset));

        return new String(encoded);
    }

    /**
     * Encodes the given string using the default UTF-8 charset.
     *
     * @param value the string to be encoded
     * @return the Base64 encoded string
     */
    public static String encode(String value) {
        return encode(value, StandardCharsets.UTF_8);
    }

    /**
     * Decodes the given Base64 encoded string using the specified charset.
     *
     * @param value   the Base64 encoded string to be decoded
     * @param charset the charset to be used for decoding
     * @return the decoded string
     */
    public static String decode(String value, Charset charset) {
        var decoded = getDecoder().decode(value.getBytes(charset));

        return new String(decoded);
    }

    /**
     * Decodes the given Base64 encoded string using the default UTF-8 charset.
     *
     * @param value the Base64 encoded string to be decoded
     * @return the decoded string
     */
    public static String decode(String value) {
        return decode(value, StandardCharsets.UTF_8);
    }

    /**
     * Encodes the given string using the specified charset.
     *
     * @param value   the string to be encoded
     * @param charset the charset to be used for encoding
     * @return the Base64 encoded string
     */
    public static String encodeUrlComponents(String value, Charset charset) {
        var encoded = getUrlEncoder().encode(value.getBytes(charset));

        return new String(encoded);
    }

    /**
     * Encodes the given string using the default UTF-8 charset.
     *
     * @param value the string to be encoded
     * @return the Base64 encoded string
     */
    public static String encodeUrlComponents(String value) {
        return encodeUrlComponents(value, StandardCharsets.UTF_8);
    }

    /**
     * Decodes the given Base64 encoded string using the specified charset.
     *
     * @param value   the Base64 encoded string to be decoded
     * @param charset the charset to be used for decoding
     * @return the decoded string
     */
    public static String decodeUrlComponents(String value, Charset charset) {
        var decoded = getUrlDecoder().decode(value.getBytes(charset));

        return new String(decoded);
    }

    /**
     * Decodes the given Base64 encoded string using the default UTF-8 charset.
     *
     * @param value the Base64 encoded string to be decoded
     * @return the decoded string
     */
    public static String decodeUrlComponents(String value) {
        return decodeUrlComponents(value, StandardCharsets.UTF_8);
    }
}
