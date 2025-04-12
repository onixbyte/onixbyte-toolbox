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

package com.onixbyte.jwt.data;

/**
 * A record representing the raw components of a JSON Web Token (JWT).
 * <p>
 * Holds the header, payload, and signature of a JWT as strings, typically in their Base64 URL-encoded
 * form as extracted from a token string. This record is used to facilitate parsing and processing
 * of JWTs without decoding or validating their contents.
 *
 * @param header    the Base64 URL-encoded header string of the JWT
 * @param payload   the Base64 URL-encoded payload string of the JWT
 * @param signature the Base64 URL-encoded signature string of the JWT
 * @author zihluwang
 */
public record RawTokenComponent(
        String header,
        String payload,
        String signature
) {
}
