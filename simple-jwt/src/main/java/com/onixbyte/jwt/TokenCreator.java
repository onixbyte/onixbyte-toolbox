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

/**
 * Interface for creating and signing JSON Web Tokens (JWTs).
 * <p>
 * Defines a contract for implementations that generate signed JWTs from a given payload. The
 * resulting token is typically a string in the format "header.payload.signature", where the
 * signature is created using a cryptographic algorithm specific to the implementation.
 *
 * @author zihluwang
 */
public interface TokenCreator {

    /**
     * Signs a token payload to create a JWT.
     * <p>
     * Takes a {@link TokenPayload} object, serialises its claims, and generates a signed
     * JWT string. The specific signing algorithm (e.g., HMAC, RSA, ECDSA) depends on
     * the implementation.
     *
     * @param payload the {@link TokenPayload} containing claims to include in the token
     * @return the signed JWT as a string in the format "header.payload.signature"
     * @throws IllegalArgumentException if the payload cannot be serialised to JSON due to invalid
     *                                  data or structure, or if the signing process fails due to
     *                                  configuration issues
     */
    String sign(TokenPayload payload);

}
