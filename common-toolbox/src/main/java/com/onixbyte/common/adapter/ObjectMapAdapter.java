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

package com.onixbyte.common.adapter;

import java.util.Map;

/**
 * The {@link ObjectMapAdapter} interface provides methods to convert between objects and maps.
 * This interface is useful for scenarios where objects need to be represented as maps for
 * serialization, deserialization, or other purposes.
 *
 * <p>Implementations of this interface should provide the logic to convert an object of type
 * {@code T} to a {@link Map} and vice versa.</p>
 *
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * public class User {
 *     private String name;
 *     private int age;
 *     
 *     // getters and setters
 * }
 * 
 * public class UserMapAdapter implements ObjectMapAdapter<User> {
 *     @Override
 *     public Map<String, Object> toMap(User user) {
 *         Map<String, Object> map = new HashMap<>();
 *         map.put("name", user.getName());
 *         map.put("age", user.getAge());
 *         return map;
 *     }
 * 
 *     @Override
 *     public User fromMap(Map<String, Object> map) {
 *         User user = new User();
 *         user.setName((String) map.get("name"));
 *         user.setAge((Integer) map.get("age"));
 *         return user;
 *     }
 * }
 * }</pre>
 *
 * @param <T> the type of the object to be converted
 * @author zihluwang
 * @version 3.0.0
 */
public interface ObjectMapAdapter<T> {

    /**
     * Convert an object to a map.
     *
     * @param element the element that will be converted to Map
     * @return a Map that is converted from the element
     */
    Map<String, Object> toMap(T element);

    /**
     * Convert a Map to an object.
     *
     * @param map the map that will be converted to an object
     * @return the object that is converted from the Map
     */
    T toObject(Map<String, Object> map);

}
