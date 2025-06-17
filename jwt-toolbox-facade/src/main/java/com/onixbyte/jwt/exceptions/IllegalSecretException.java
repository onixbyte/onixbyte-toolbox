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

package com.onixbyte.jwt.exceptions;

/**
 * {@code IllegalKeyPairException} indicates the secret to sign a JWT is illegal.
 *
 * @author zihluwang
 * @version 1.6.0
 * @since 1.6.0
 */
public class IllegalSecretException extends RuntimeException {

    /**
     * Create a default exception instance.
     */
    public IllegalSecretException() {
    }

    /**
     * Create an exception instance with specific message.
     *
     * @param message the message of the exception
     */
    public IllegalSecretException(String message) {
        super(message);
    }

    /**
     * Create an exception instance with specific message and the cause of this exception.
     *
     * @param message the message of the exception
     * @param cause   the cause of the exception
     */
    public IllegalSecretException(String message, Throwable cause) {
        super(message, cause);
    }

}
