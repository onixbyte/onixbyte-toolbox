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

package com.onixbyte.simplejwt.constants;

/**
 * The base data types used to process enum data.
 *
 * @author Zihlu Wang
 */
public enum TokenDataType {

    /**
     * Marks enumeration being processed as Boolean.
     */
    BOOLEAN(Boolean.class),

    /**
     * Marks enumeration being processed as Double.
     */
    DOUBLE(Long.class),

    /**
     * Marks enumeration being processed as Float.
     */
    FLOAT(Float.class),

    /**
     * Marks enumeration being processed as Integer.
     */
    INTEGER(Integer.class),

    /**
     * Marks enumeration being processed as Long.
     */
    LONG(Long.class),

    /**
     * Marks enumeration being processed as String.
     */
    STRING(String.class),
    ;

    /**
     * The mapped class to this mark.
     */
    private final Class<?> mappedClass;

    /**
     * Create a TokenDataType with a mapped class.
     */
    TokenDataType(Class<?> mappedClass) {
        this.mappedClass = mappedClass;
    }

    /**
     * Return the target mapped class.
     *
     * @return mapped class
     */
    public Class<?> getMappedClass() {
        return mappedClass;
    }
}
