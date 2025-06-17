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

package com.onixbyte.identitygenerator;

/**
 * The {@code IdentityGenerator} is a generic interface for generating globally unique identifiers (GUIDs)
 * of a specific type.
 * <p>
 * The type of ID is determined by the class implementing this interface.
 * </p>
 *
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * public class StringGuidCreator implements IdentityGenerator<String> {
 *     private final AtomicLong counter = new AtomicLong();
 *
 *     @Override
 *     public String nextId() {
 *         return UUID.randomUUID().toString() + "-" + counter.incrementAndGet();
 *     }
 * }
 *
 * public class Example {
 *     public static void main(String[] args) {
 *         IdentityGenerator<String> guidCreator = new StringGuidCreator();
 *         String guid = guidCreator.nextId();
 *         System.out.println("Generated GUID: " + guid);
 *     }
 * }
 * }</pre>
 *
 * @param <IdType> this represents the type of the Global Unique Identifier
 * @author Zihlu Wang
 * @version 1.1.0
 * @since 1.0.0
 */
public interface IdentityGenerator<IdType> {

    /**
     * Generates and returns the next globally unique ID.
     *
     * @return the next globally unique ID
     */
    IdType nextId();

}
