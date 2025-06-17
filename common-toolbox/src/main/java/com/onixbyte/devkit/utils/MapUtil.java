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
 * The {@link MapUtil} class provides utility methods for converting between objects and maps.
 * This class leverages the {@link ObjectMapAdapter} interface to perform the conversions.
 * <p>
 * The utility methods in this class are useful for scenarios where objects need to be represented
 * as maps for serialization, deserialization, or other purposes.
 * </p>
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
 * 
 * public class Example {
 *     public static void main(String[] args) {
 *         User user = new User();
 *         user.setName("John");
 *         user.setAge(30);
 *         
 *         UserMapAdapter adapter = new UserMapAdapter();
 *         
 *         // Convert object to map
 *         Map<String, Object> userMap = MapUtil.objectToMap(user, adapter);
 *         System.out.println(userMap); // Output: {name=John, age=30}
 *         
 *         // Convert map to object
 *         User newUser = MapUtil.mapToObject(userMap, adapter);
 *         System.out.println(newUser.getName()); // Output: John
 *         System.out.println(newUser.getAge());  // Output: 30
 *     }
 * }
 * }
 * </pre>
 *
 * @author zihluwang
 * @version 1.7.0
 * @since 1.0.0
 */
public final class MapUtil {

    /**
     * Converts an object to a map by mapping the field names to their corresponding values.
     *
     * @param <T>     the type of the object
     * @param entity  the object to be converted to a map
     * @param adapter adapts the entity for mapping to a map
     * @return a map representing the fields and their values of the object
     */
    public static <T> Map<String, Object> objectToMap(T entity, ObjectMapAdapter<T> adapter) {
        return adapter.toMap(entity);
    }

    /**
     * Converts a map to an object of the specified type by setting the field values using the
     * map entries.
     *
     * @param objectMap the map representing the fields and their values
     * @param adapter   the adapter to execute the setter for the entity
     * @param <T>       the type of the object to be created
     * @return an object of the specified type with the field values set from the map
     */
    public static <T> T mapToObject(Map<String, Object> objectMap, ObjectMapAdapter<T> adapter) {
        return adapter.toObject(objectMap);
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private MapUtil() {
    }
}
