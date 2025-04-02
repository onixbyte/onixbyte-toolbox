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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 *
 */
public class TokenPayload {

    public static TokenPayload createPayload() {
        return new TokenPayload();
    }

    private final Map<String, Object> payload;
    private final List<String> audiences;

    private String subject;
    private String issuer;
    private String tokenId;
    private Long expiresAt;
    private Long notBefore;
    private Long issuedAt;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TokenPayload() {
        payload = new HashMap<>();
        audiences = new ArrayList<>();
    }

    public TokenPayload withAudience(String audience) {
        audiences.add(audience);
        return this;
    }

    public TokenPayload withAudiences(String... audiences) {
        this.audiences.addAll(Arrays.asList(audiences));
        return this;
    }

    public TokenPayload withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public TokenPayload withIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public TokenPayload withTokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public TokenPayload withExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt.atZone(ZoneId.systemDefault())
                .toInstant()
                .getEpochSecond();
        return this;
    }

    public TokenPayload withNotBefore(LocalDateTime notBefore) {
        this.notBefore = notBefore.atZone(ZoneId.systemDefault())
                .toInstant()
                .getEpochSecond();
        return this;
    }

    public TokenPayload withIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt.atZone(ZoneId.systemDefault())
                .toInstant()
                .getEpochSecond();
        return this;
    }

    public TokenPayload withClaim(String name, String value) {
        if (RegisteredClaims.VALUES.contains(name)) {
            throw new IllegalStateException("Please set registered claims with pre-defined methods");
        }

        this.payload.put(name, value);
        return this;
    }

    public boolean hasIssuer() {
        return Objects.nonNull(issuer) && !issuer.isBlank();
    }

    public Map<String, Object> getPayload() {
        var _payload = new HashMap<>(payload);

        Optional.of(audiences)
                .filter((aud) -> !aud.isEmpty())
                .ifPresent((aud) -> _payload.put(RegisteredClaims.AUDIENCE, aud));

        Optional.ofNullable(subject)
                .filter((sub) -> !sub.isBlank())
                .ifPresent((sub) -> _payload.put(RegisteredClaims.SUBJECT, subject));

        Optional.ofNullable(expiresAt)
                .ifPresent((exp) -> _payload.put(RegisteredClaims.EXPIRES_AT, exp));

        Optional.ofNullable(tokenId)
                .filter((jti) -> !jti.isBlank())
                .ifPresent((jti) -> _payload.put(RegisteredClaims.TOKEN_ID, jti));

        Optional.ofNullable(issuer)
                .map((iss) -> !iss.isBlank())
                .ifPresent((iss) -> _payload.put(RegisteredClaims.ISSUER, iss));

        Optional.ofNullable(issuedAt)
                .ifPresent((iat) -> _payload.put(RegisteredClaims.ISSUED_AT, iat));

        Optional.ofNullable(notBefore)
                .ifPresent((nbf) -> _payload.put(RegisteredClaims.NOT_BEFORE, nbf));

        return _payload;
    }

}
