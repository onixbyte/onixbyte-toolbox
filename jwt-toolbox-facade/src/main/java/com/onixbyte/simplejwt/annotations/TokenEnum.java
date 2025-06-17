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

package com.onixbyte.simplejwt.annotations;

import com.onixbyte.simplejwt.constants.TokenDataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks the enum field declared in payload class will be handled as basic data
 * types in {@link TokenDataType}.
 *
 * @author Zihlu Wang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TokenEnum {

    /**
     * The name of the field of the base data corresponding to the enumeration data.
     *
     * @return the name of the property
     */
    String propertyName();

    /**
     * The attribute {@code dataType} specifies what base data type to treat this enum as.
     *
     * @return the data type of the token
     */
    TokenDataType dataType();

}
