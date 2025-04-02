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

package com.onixbyte.devkit.utils.unsafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link ReflectMapUtil} class provides utility methods for converting between objects and maps
 * using reflection. This class allows for dynamic mapping of object fields to map entries and
 * vice versa.
 * <p>
 * The utility methods in this class are useful for scenarios where objects need to be represented
 * as maps for serialization, deserialization, or other purposes, and where the structure of the
 * objects is not known at compile time.
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
 * public class Example {
 *     public static void main(String[] args) throws IllegalAccessException {
 *         User user = new User();
 *         user.setName("John");
 *         user.setAge(30);
 *         
 *         // Convert object to map
 *         Map<String, Object> userMap = ReflectMapUtil.objectToMap(user);
 *         System.out.println(userMap); // Output: {name=John, age=30}
 *         
 *         // Convert map to object
 *         User newUser = ReflectMapUtil.mapToObject(userMap, User.class);
 *         System.out.println(newUser.getName()); // Output: John
 *         System.out.println(newUser.getAge());  // Output: 30
 *     }
 * }
 * }
 * </pre>
 *
 * @author zihluwang
 * @version 1.4.2
 * @since 1.4.2
 */
public final class ReflectMapUtil {

    private final static Logger log = LoggerFactory.getLogger(ReflectMapUtil.class);

    /**
     * Converts an object to a map by mapping the field names to their corresponding values.
     *
     * @param obj the object to be converted to a map
     * @return a map representing the fields and their values of the object
     * @throws IllegalAccessException if an error occurs while accessing the fields of the object
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return null;
        }

        var map = new HashMap<String, Object>();

        var declaredFields = obj.getClass().getDeclaredFields();
        for (var field : declaredFields) {
            field.setAccessible(true);
            var result = field.get(obj);
            if (result != null) {
                map.put(field.getName(), result);
            }
        }

        return map;
    }

    /**
     * Converts a map to an object of the specified type by setting the field values using the
     * map entries.
     *
     * @param map          the map representing the fields and their values
     * @param requiredType the class of the object to be created
     * @param <T>          the type of the object to be created
     * @return an object of the specified type with the field values set from the map
     * @throws NoSuchMethodException     if the constructor of the required type is not found
     * @throws InvocationTargetException if an error occurs while invoking the constructor
     * @throws InstantiationException    if the required type is abstract or an interface
     * @throws IllegalAccessException    if an error occurs while accessing the fields of the object
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> requiredType)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        var bean = requiredType.getConstructor().newInstance();
        if (map != null) {
            for (var entry : map.entrySet()) {
                try {
                    var entryValue = entry.getValue().toString();
                    // get the field by field name
                    var field = requiredType.getDeclaredField(entry.getKey());
                    var fieldType = field.getGenericType();

                    // convert field value by class
                    if (fieldType instanceof Class<?> fieldClass) {
                        if (fieldClass == Short.class || fieldClass == short.class) {
                            entry.setValue(Short.parseShort(entryValue));
                        } else if (fieldClass == Integer.class || fieldClass == int.class) {
                            entry.setValue(Integer.parseInt(entryValue));
                        } else if (fieldClass == Long.class || fieldClass == long.class) {
                            entry.setValue(Long.parseLong(entryValue));
                        } else if (fieldClass == Float.class || fieldClass == float.class) {
                            entry.setValue(Float.parseFloat(entryValue));
                        } else if (fieldClass == Double.class || fieldClass == double.class) {
                            entry.setValue(Double.parseDouble(entryValue));
                        } else if (fieldClass == Character.class || fieldClass == char.class) {
                            entry.setValue(entryValue.charAt(0));
                        } else if (fieldClass == Byte.class || fieldClass == byte.class) {
                            entry.setValue(Byte.parseByte(entryValue));
                        } else if (fieldClass == Boolean.class || fieldClass == boolean.class) {
                            entry.setValue(Boolean.parseBoolean(entryValue));
                        } else if (fieldClass == String.class) {
                            entry.setValue(entryValue);
                        } else {
                            log.error("Unable to determine the type of property {}.", field.getName());
                            continue;
                        }
                    }

                    setFieldValue(bean, entry.getKey(), entry.getValue());
                } catch (Exception e) {
                    log.error("Map to Object failed.");
                }
            }
        }
        return bean;
    }

    /**
     * Retrieves the value of a field from an object using reflection.
     *
     * @param obj       the object from which to retrieve the field value
     * @param fieldName the name of the field
     * @param fieldType the class representing the type of the field value
     * @param <T>       the type of the field value
     * @return the value of the field in the object, or null if the field does
     * not exist or cannot be accessed
     * @throws IllegalAccessException    if an error occurs while accessing the field
     * @throws InvocationTargetException if an error occurs while invoking the field getter method
     * @throws NoSuchMethodException     if the specified getter is not present
     */
    public static <T> T getFieldValue(Object obj, String fieldName, Class<T> fieldType)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var methodName = getMethodName("get", fieldName);
        var objectClass = obj.getClass();
        var method = objectClass.getDeclaredMethod(methodName);

        method.setAccessible(true);
        return cast(method.invoke(obj), fieldType);
    }

    /**
     * Sets the value of a field in an object using reflection.
     *
     * @param obj        the object in which to set the field value
     * @param fieldName  the name of the field
     * @param fieldValue the value to be set
     * @throws InvocationTargetException if an error occurs while invoking the field setter method
     * @throws IllegalAccessException    if an error occurs while accessing the field
     * @throws NoSuchMethodException     if the specific setter is not present
     */
    public static void setFieldValue(Object obj, String fieldName, Object fieldValue)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var objectClass = obj.getClass();
        var methodName = getMethodName("set", fieldName);
        var method = objectClass.getDeclaredMethod(methodName, fieldValue.getClass());
        method.setAccessible(true);
        method.invoke(obj, fieldValue);
    }

    /**
     * Casts the specified value to the required type.
     *
     * @param value        the value to be cast
     * @param requiredType the type to which the value should be cast
     * @param <T>          the type to which the value should be cast
     * @return the cast value, or null if the value cannot be cast to the required type
     */
    public static <T> T cast(Object value, Class<T> requiredType) {
        if (requiredType.isInstance(value)) {
            return requiredType.cast(value);
        }
        return null;
    }

    /**
     * Constructs a method name based on the given prefix and field name.
     *
     * @param prefix    the prefix to be added to the field name
     * @param fieldName the name of the field
     * @return the constructed method name
     */
    private static String getMethodName(String prefix, String fieldName) {
        return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * Returns the default string representation of the specified object.
     *
     * @param obj the object for which to return the default string representation
     * @return the default string representation of the object
     */
    private static String defaultObject(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return String.valueOf(obj);
        }
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ReflectMapUtil() {
    }
}
