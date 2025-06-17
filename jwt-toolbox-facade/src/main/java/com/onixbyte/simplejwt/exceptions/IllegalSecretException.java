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

package com.onixbyte.simplejwt.exceptions;

/**
 * {@link IllegalKeyPairException} indicates the secret to sign a JWT is illegal.
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
