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

import com.onixbyte.jwt.data.RawTokenComponent;

import java.util.Map;

/**
 *
 */
public interface TokenResolver {

    /**
     *
     * @param token
     */
    void verify(String token);

    /**
     *
     * @param token
     * @return
     */
    Map<String, String> getHeader(String token);

    /**
     *
     * @param payload
     * @return
     */
    Map<String, Object> getPayload(String payload);

    /**
     *
     * @param token
     * @return
     */
    RawTokenComponent splitToken(String token);

}
