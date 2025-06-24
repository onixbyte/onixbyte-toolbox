/*
 * Copyright (c) 2024-2025 OnixByte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.onixbyte.identitygenerator;

/**
 * The {@code IdentityGenerator} is a generic interface for generating globally unique identifiers
 * (GUIDs) of a specific type.
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
 * @author zihluwang
 * @version 3.0.0
 */
public interface IdentityGenerator<IdType> {

    /**
     * Generates and returns the next globally unique ID.
     *
     * @return the next globally unique ID
     */
    IdType nextId();

}
