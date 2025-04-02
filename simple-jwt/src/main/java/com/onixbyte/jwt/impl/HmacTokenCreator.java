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

package com.onixbyte.jwt.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onixbyte.jwt.TokenCreator;
import com.onixbyte.jwt.TokenPayload;
import com.onixbyte.jwt.constant.Algorithm;
import com.onixbyte.jwt.constant.HeaderClaims;
import com.onixbyte.jwt.holder.ObjectMapperHolder;
import com.onixbyte.jwt.util.CryptoUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

/**
 * Implementation of {@link TokenCreator} that generates HMAC-signed JSON Web Tokens (JWTs).
 * <p>
 * This class uses a specified HMAC algorithm to create signed tokens, incorporating a header,
 * payload, and signature. It ensures the secret key meets the minimum length requirement for
 * the chosen algorithm and handles JSON serialisation of the token components.
 *
 * @author zihluwang
 */
public class HmacTokenCreator implements TokenCreator {

    private final Algorithm algorithm;
    private final String issuer;
    private final byte[] secret;

    private final ObjectMapper objectMapper;

    /**
     * Constructs an HMAC token creator with the specified algorithm, issuer, and secret key.
     * <p>
     * Validates that the secret key length meets the minimum requirement for the chosen algorithm.
     *
     * @param algorithm the HMAC algorithm to use for signing (e.g., HS256, HS384, HS512)
     * @param issuer    the issuer identifier to include in the token payload if not already present
     * @param secret    the secret key as a string, used to generate the HMAC signature
     * @throws IllegalArgumentException if the secret key is shorter than the minimum required
     *                                  length for the specified algorithm
     */
    public HmacTokenCreator(Algorithm algorithm, String issuer, String secret) {
        var _minSecretLength = algorithm.getShaLength() >> 3;
        var secretBytesLength = secret.getBytes(StandardCharsets.UTF_8).length;
        if (secretBytesLength < _minSecretLength) {
            throw new IllegalArgumentException("Secret key too short for HS%d: minimum %d bytes required, got %d."
                    .formatted(algorithm.getShaLength(), _minSecretLength, secretBytesLength)
            );
        }

        this.algorithm = algorithm;
        this.issuer = issuer;
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.objectMapper = ObjectMapperHolder.getInstance().getObjectMapper();
    }

    /**
     * Creates and signs a JWT using the HMAC algorithm.
     * <p>
     * Generates a token by encoding the header and payload as Base64 URL-safe strings,
     * creating an HMAC signature, and concatenating them with dots. If the payload does not
     * include an issuer, the configured issuer is added.
     *
     * @param payload the {@link TokenPayload} containing claims to include in the token
     * @return the signed JWT as a string in the format "header.payload.signature"
     * @throws IllegalArgumentException if the payload cannot be serialised to JSON due to
     *                                  invalid data or structure
     * @throws RuntimeException         if an unexpected error occurs during JSON processing
     */
    @Override
    public String sign(TokenPayload payload) {
        var header = new HashMap<String, String>();

        header.put(HeaderClaims.ALGORITHM, algorithm.name());
        if (!header.containsKey(HeaderClaims.TYPE)) {
            header.put(HeaderClaims.TYPE, "JWT");
        }

        if (!payload.hasIssuer()) {
            payload.withIssuer(issuer);
        }

        try {
            var encodedHeader = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(objectMapper.writeValueAsBytes(header));
            var encodedPayload = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(objectMapper.writeValueAsBytes(payload.getPayload()));

            var signatureBytes = CryptoUtil.createSignatureFor(algorithm,
                    secret,
                    encodedHeader.getBytes(StandardCharsets.UTF_8),
                    encodedPayload.getBytes(StandardCharsets.UTF_8));
            var signature = Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString((signatureBytes));

            return "%s.%s.%s".formatted(encodedHeader, encodedPayload, signature);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialise token header or payload to JSON.", e);
        }
    }
}
