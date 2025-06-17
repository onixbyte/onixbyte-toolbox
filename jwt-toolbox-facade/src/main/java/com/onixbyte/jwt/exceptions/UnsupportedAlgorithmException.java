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
 * <p>
 * If you want the supports to an unsupported algorithm, you could
 * <ul>
 *     <li>Commit an issue at GitHub Issues or;</li>
 *     <li>Communicate with us on Discord Community.</li>
 * </ul>
 *
 * @author zihluwang
 * @version 3.0.0
 */
public class UnsupportedAlgorithmException extends RuntimeException {

    /**
     * Constructs a new {@code UnsupportedAlgorithmException} with {@code null} as its
     * detail message. The cause is not initialized, and may subsequently be initialized by a call
     * to {@link #initCause}.
     */
    public UnsupportedAlgorithmException() {
    }

    /**
     * Constructs a new {@code UnsupportedAlgorithmException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call
     * to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public UnsupportedAlgorithmException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code UnsupportedAlgorithmException} with the specified detail message
     * and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *                {@link #getMessage()} method)
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A {@code null} value is permitted, and
     *                indicates that the cause is nonexistent or unknown.)
     * @since 1.4
     */
    public UnsupportedAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code UnsupportedAlgorithmException} with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which typically contains the
     * class and detail message of {@code cause}).  This constructor is useful for runtime
     * exceptions that are little more than wrappers for other throwable.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A {@code null} value is permitted, and indicates
     *              that the cause is nonexistent or unknown.)
     * @since 1.4
     */
    public UnsupportedAlgorithmException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code UnsupportedAlgorithmException} with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the detail message.
     * @param cause              the cause (A {@code null} value is permitted, and indicates that
     *                           the cause is nonexistent or unknown.)
     * @param enableSuppression  whether suppression is enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable
     * @since 1.0.0
     */
    public UnsupportedAlgorithmException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
