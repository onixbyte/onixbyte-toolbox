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

package com.onixbyte.identitygenerator.exceptions;

/**
 * The {@code TimingException} class represents an exception that is thrown when there is an error
 * related to time sequence.
 * <p>
 * Instances of TimingException can be created with or without a message and a cause. The message
 * provides a description of the exception, while the cause represents the underlying cause of the
 * exception and provides additional information about the error.
 *
 * @author zihluwang
 * @since 3.0.0
 */
public class TimingException extends RuntimeException {

    /**
     * A custom exception that is thrown when there is an issue with timing or scheduling.
     */
    public TimingException() {
    }

    /**
     * A custom exception that is thrown when there is an issue with timing or scheduling with
     * customised error message.
     *
     * @param message customised message
     */
    public TimingException(String message) {
        super(message);
    }

    /**
     * A custom exception that is thrown when there is an issue with timing or scheduling with
     * customised error message.
     *
     * @param message customised message
     * @param cause   the cause of this exception
     */
    public TimingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * A custom exception that is thrown when there is an issue with timing or scheduling with
     * customised error message.
     *
     * @param cause the cause of this exception
     */
    public TimingException(Throwable cause) {
        super(cause);
    }
}
