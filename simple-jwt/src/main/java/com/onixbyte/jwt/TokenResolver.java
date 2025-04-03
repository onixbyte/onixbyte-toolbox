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

package com.onixbyte.jwt;

import com.onixbyte.jwt.constant.RegisteredClaims;
import com.onixbyte.jwt.data.RawTokenComponent;

import java.util.Map;

/**
 *
 */
public interface TokenResolver {

    /**
     * Verifies the HMAC signature of the provided JWT.
     * <p>
     * Splits the token into its components and uses the configured algorithm and secret to check
     * the signature's validity. If the signature does not match, an exception is thrown by the
     * underlying cryptographic utility.
     *
     * @param token the JWT string to verify
     * @throws IllegalArgumentException if the token is malformed or the signature verification
     *                                  fails due to an invalid algorithm, key, or
     *                                  mismatched signature
     */
    void verify(String token);

    /**
     * Retrieves the header claims from the provided JWT.
     * <p>
     * Decodes the Base64-encoded header and deserialises it into a map of strings.
     *
     * @param token the JWT string from which to extract the header
     * @return a map containing the header claims as key-value pairs
     * @throws IllegalArgumentException if the token is malformed or the header cannot be
     *                                  deserialised due to invalid JSON format
     */
    Map<String, String> getHeader(String token);

    /**
     * Retrieves the payload claims from the provided JWT, excluding registered claims.
     * <p>
     * Decodes the Base64-encoded payload, deserialises it into a map, and removes any registered
     * claims as defined in {@link RegisteredClaims}.
     *
     * @param token the JWT string from which to extract the payload
     * @return a map containing the custom payload claims as key-value pairs
     * @throws IllegalArgumentException if the token is malformed or the payload cannot be
     *                                  deserialised due to invalid JSON format
     */
    Map<String, Object> getPayload(String token);

    /**
     * Splits a JWT into its raw components: header, payload, and signature.
     *
     * @param token the JWT string to split
     * @return a {@link RawTokenComponent} containing the header, payload, and signature as strings
     * @throws IllegalArgumentException if the token does not consist of exactly three parts
     *                                  separated by dots
     */
    default RawTokenComponent splitToken(String token) {
        var tokenTuple = token.split("\\.");

        if (tokenTuple.length != 3) {
            throw new IllegalArgumentException(
                    "The provided JWT is invalid: it must consist of exactly three parts separated by dots.");
        }

        return new RawTokenComponent(tokenTuple[0], tokenTuple[1], tokenTuple[2]);
    }

}
