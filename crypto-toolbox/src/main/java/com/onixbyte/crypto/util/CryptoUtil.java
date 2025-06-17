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

package com.onixbyte.crypto.util;

public final class CryptoUtil {

    private CryptoUtil() {
    }

    /**
     * Retrieves the raw content of a PEM formatted key by removing unnecessary headers, footers,
     * and new line characters.
     *
     * <p>
     * This method processes the provided PEM key text to return a cleaned string that contains
     * only the key content. The method strips away the
     * {@code "-----BEGIN (EC )?(PRIVATE|PUBLIC) KEY-----"} and
     * {@code "-----END (EC )?(PRIVATE|PUBLIC) KEY-----"} lines, as well as any new line characters,
     * resulting in a continuous string representation of the key, which can be used for further
     * cryptographic operations.
     *
     * @param pemKeyText the PEM formatted key as a string, which may include headers, footers and
     *                   line breaks
     * @return a string containing the raw key content devoid of  any unnecessary formatting
     * or whitespace
     */
    public static String getRawContent(String pemKeyText) {
        // remove all unnecessary parts of the pem key text
        return pemKeyText
            .replaceAll("-----BEGIN ((EC )|(RSA ))?(PRIVATE|PUBLIC) KEY-----", "")
            .replaceAll("-----END ((EC )|(RSA ))?(PRIVATE|PUBLIC) KEY-----", "")
            .replaceAll("\n", "");
    }
}
