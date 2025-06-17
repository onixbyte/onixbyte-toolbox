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

package com.onixbyte.devkit.utils;

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
 * <pre>
 * {@code
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
 * }
 * </pre>
 *
 * @param <T> the type of the object to be converted
 * @author zihluwang
 * @version 1.7.0
 * @since 1.4.2
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
