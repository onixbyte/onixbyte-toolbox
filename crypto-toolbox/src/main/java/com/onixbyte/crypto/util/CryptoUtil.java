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

package com.onixbyte.crypto.util;

/**
 * Utility class for cryptographic operations.
 *
 * @author zihluwang
 * @author siujamo
 * @version 3.0.0
 */
public final class CryptoUtil {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private CryptoUtil() {
    }

    /**
     * Extracts the raw content from a PEM-formatted key by removing any headers, footers,
     * and newline characters.
     *
     * <p>
     * This method processes the given PEM key text and returns a cleaned string containing only
     * the key material. It removes the lines matching the
     * {@code "-----BEGIN (EC )?(RSA )?(PRIVATE|PUBLIC) KEY-----"} and
     * {@code "-----END (EC )?(RSA )?(PRIVATE|PUBLIC) KEY-----"} patterns,
     * as well as any newline characters,
     * resulting in a continuous string that can be used directly for cryptographic operations.
     *
     * @param pemKeyText the PEM-formatted key as a string, which may include headers, footers,
     *                   and line breaks
     * @return a string containing the raw key content without any unnecessary formatting or whitespace
     */
    public static String getRawContent(String pemKeyText) {
        return pemKeyText
            .replaceAll("-----BEGIN ((EC )|(RSA ))?(PRIVATE|PUBLIC) KEY-----", "")
            .replaceAll("-----END ((EC )|(RSA ))?(PRIVATE|PUBLIC) KEY-----", "")
            .replaceAll("\n", "");
    }
}
