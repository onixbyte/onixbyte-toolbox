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

package com.onixbyte.crypto.exception;

/**
 * The {@code KeyLoadingException} class represents an exception that is thrown when there is an
 * error loading cryptographic keys. This exception can be used to indicate various issues such as
 * invalid key specifications, unsupported key algorithms, or other key loading errors.
 * <p>
 * This class extends {@link RuntimeException}, allowing it to be thrown without being declared in
 * a method's {@code throws} clause.
 * </p>
 * 
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * try {
 *     PrivateKeyLoader keyLoader = new ECPrivateKeyLoader();
 *     ECPrivateKey privateKey = keyLoader.loadPrivateKey(pemPrivateKey);
 * } catch (KeyLoadingException e) {
 *     // Handle the exception
 *     e.printStackTrace();
 * }
 * }</pre>
 *
 * @author zihluwang
 * @version 3.0.0
 */
public class KeyLoadingException extends RuntimeException {

    /**
     * Creates a new instance of {@code KeyLoadingException} without a specific message or cause.
     */
    public KeyLoadingException() {
    }

    /**
     * Creates a new instance of {@code KeyLoadingException} with the specified detail message.
     *
     * @param message the detail message
     */
    public KeyLoadingException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of {@code KeyLoadingException} with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the cause of this exception
     */
    public KeyLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of {@code KeyLoadingException} with the specified cause.
     *
     * @param cause the cause of this exception
     */
    public KeyLoadingException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message, cause, suppression enabled
     * or disabled, and writable stack trace enabled or disabled.
     *
     * @param message the detail message
     * @param cause the cause of this exception
     * @param enableSuppression whether suppression is enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable
     */
    public KeyLoadingException(String message,
                               Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
