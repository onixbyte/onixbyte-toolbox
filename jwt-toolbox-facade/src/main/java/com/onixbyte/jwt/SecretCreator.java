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

package com.onixbyte.jwt;

import com.onixbyte.jwt.exceptions.WeakSecretException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * {@code SecretCreator} is a utility class that provides methods to generate secure secret strings.
 * The generated secrets can be used as cryptographic keys or passwords for various
 * security-sensitive purposes.
 *
 * @author zihluwang
 * @version 3.0.0
 * @since 1.0.0
 */
public final class SecretCreator {

    /**
     * Generates a secure secret with the specified length and character sets.
     *
     * @param length               the length of the secret to be generated
     * @param isContainCapital     flag indicating if the secret should contain
     *                             uppercase letters
     * @param isContainDigital     flag indicating if the secret should contain
     *                             digits
     * @param isContainSpecialSign flag indicating if the secret should contain
     *                             special sign characters
     * @return the generated secure secret
     * @throws WeakSecretException if the requested secret length is less than
     *                             32 characters
     */
    public static String createSecret(int length,
                                      boolean isContainCapital,
                                      boolean isContainDigital,
                                      boolean isContainSpecialSign) {
        if (length < 32) {
            throw new WeakSecretException("""
                    The requested secret, which is only %d characters long, is too weak. \
                    Please replace it with a stronger secret.""".formatted(length));
        }

        final var randomiser = new Random();
        var charset = new StringBuilder(LOWERCASE_CHARACTERS);

        if (isContainCapital) charset.append(UPPERCASE_CHARACTERS);
        if (isContainDigital) charset.append(DIGITS);
        if (isContainSpecialSign) charset.append(SPECIAL_SIGNS);

        var secretBuilder = new StringBuilder();
        var charsetSize = charset.length();
        for (var i = 0; i < length; ++i) {
            secretBuilder.append(charset.charAt(randomiser.nextInt(charsetSize)));
        }

        return secretBuilder.toString();
    }

    /**
     * Generates a secure secret with the specified length, containing
     * uppercase letters and digits.
     *
     * @param length           the length of the secret to be generated
     * @param isContainCapital flag indicating if the secret should contain
     *                         uppercase letters
     * @param isContainDigital flag indicating if the secret should contain
     *                         digits
     * @return the generated secure secret
     * @throws WeakSecretException if the requested secret length is less than
     *                             32 characters
     * @see #createSecret(int, boolean, boolean, boolean)
     */
    public static String createSecret(int length,
                                      boolean isContainCapital,
                                      boolean isContainDigital) {
        return createSecret(length, isContainCapital, isContainDigital, false);
    }

    /**
     * Generates a secure secret with the specified length, containing
     * uppercase letters.
     *
     * @param length           the length of the secret to be generated
     * @param isContainCapital flag indicating if the secret should contain
     *                         uppercase letters
     * @return the generated secure secret
     * @throws WeakSecretException if the requested secret length is less than
     *                             32 characters
     * @see #createSecret(int, boolean, boolean, boolean)
     */
    public static String createSecret(int length,
                                      boolean isContainCapital) {
        return createSecret(length, isContainCapital, false, false);
    }

    /**
     * Generates a secure secret with the specified length, containing
     * lowercase letters.
     *
     * @param length the length of the secret to be generated
     * @return the generated secure secret
     * @throws WeakSecretException if the requested secret length is less than
     *                             32 characters
     * @see #createSecret(int, boolean, boolean, boolean)
     */
    public static String createSecret(int length) {
        return createSecret(length, false, false, false);
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private SecretCreator() {
    }

    /**
     * The string containing all lowercase characters that can be used to
     * generate the secret.
     */
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * The string containing all uppercase characters that can be used to
     * generate the secret.
     */
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * The string containing all digit characters that can be used to generate
     * the secret.
     */
    private static final String DIGITS = "0123456789";

    /**
     * The string containing all special sign characters that can be used to
     * generate the secret.
     */
    private static final String SPECIAL_SIGNS = "!@#$%^&,*()_+-=,[]{}|;:,'\",.<>/?";
}
