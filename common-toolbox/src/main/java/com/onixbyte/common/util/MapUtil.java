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

package com.onixbyte.common.util;

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
 * <pre>{@code
 * // User.java
 * public class User {
 *     private String name;
 *     private int age;
 *     
 *     // getters and setters
 * }
 *
 * // UserMapAdapter.java
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
 * }</pre>
 *
 * @author zihluwang
 * @version 3.0.0
 */
public final class MapUtil {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private MapUtil() {
    }

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
}
