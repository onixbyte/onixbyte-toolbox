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

package com.onixbyte.jwt.constants;

/**
 * The {@code TokenAlgorithm} enum class defines the algorithms that can be
 * used for signing and verifying JSON Web Tokens (JWT). JWT allows various
 * cryptographic algorithms to be used for secure token generation and
 * validation. This enum provides a list of supported algorithms to ensure
 * consistent usage and avoid potential security issues.
 * <p>
 * <b>Supported Algorithms:</b>
 * <ul>
 *   <li>{@link TokenAlgorithm#HS256}: HMAC SHA-256</li>
 *   <li>{@link TokenAlgorithm#HS384}: HMAC SHA-384</li>
 *   <li>{@link TokenAlgorithm#HS512}: HMAC SHA-512</li>
 *   <li>{@link TokenAlgorithm#RS256}: RSA PKCS#1 v1.5 with SHA-256</li>
 *   <li>{@link TokenAlgorithm#RS384}: RSA PKCS#1 v1.5 with SHA-384</li>
 *   <li>{@link TokenAlgorithm#RS512}: RSA PKCS#1 v1.5 with SHA-512</li>
 *   <li>{@link TokenAlgorithm#ES256}: ECDSA with SHA-256</li>
 *   <li>{@link TokenAlgorithm#ES384}: ECDSA with SHA-384</li>
 *   <li>{@link TokenAlgorithm#ES512}: ECDSA with SHA-512</li>
 * </ul>
 *
 * @author zihluwang
 * @version 3.0.0
 * @since 1.0.0
 */
public enum TokenAlgorithm {

    /**
     * HMAC using SHA-256
     */
    HS256,

    /**
     * HMAC using SHA-384
     */
    HS384,

    /**
     * HMAC using SHA-512
     */
    HS512,

    /**
     * RSASSA-PKCS-v1_5 using SHA-256
     */
    RS256,

    /**
     * RSASSA-PKCS-v1_5 using SHA-384
     */
    RS384,

    /**
     * RSASSA-PKCS-v1_5 using SHA-512
     */
    RS512,

    /**
     * ECDSA using P-256 and SHA-256
     */
    ES256,

    /**
     * ECDSA using P-384 and SHA-384
     */
    ES384,

    /**
     * ECDSA using P-521 and SHA-512
     */
    ES512,
    ;

}
