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

package com.onixbyte.jwt.holder;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.Objects;

public class ObjectMapperHolder {

    private static ObjectMapperHolder HOLDER;

    private final ObjectMapper objectMapper;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ObjectMapperHolder() {
        this.objectMapper = JsonMapper.builder()
                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
                .build();
    }

    /**
     * Get singleton instance.
     *
     * @return the {@code ObjectMapperHolder} instance
     */
    public static ObjectMapperHolder getInstance() {
        if (Objects.isNull(HOLDER)) {
            synchronized (ObjectMapperHolder.class) {
                if (Objects.isNull(HOLDER)) {
                    HOLDER = new ObjectMapperHolder();
                }
            }
        }
        return HOLDER;
    }

    /**
     * Get Object
     *
     * @return
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
