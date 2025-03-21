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

package com.onixbyte.serial;

import com.onixbyte.serial.properties.SerialProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * {@code SerialService} provides simple serial operations.
 *
 * @author siujamo
 */
@Service
@EnableConfigurationProperties(SerialProperties.class)
public class SerialService {

    private static final Logger log = LoggerFactory.getLogger(SerialService.class);

    private final String appName;
    private final RedisTemplate<String, Long> serialRedisTemplate;
    private final SerialProperties serialProperties;

    /**
     * Default constructor.
     *
     * @param appName             the name of this application
     * @param serialRedisTemplate serial redis template
     * @param serialProperties    serial properties
     */
    public SerialService(@Value("${spring.application.name}") String appName,
                         RedisTemplate<String, Long> serialRedisTemplate,
                         SerialProperties serialProperties) {
        this.appName = appName;
        this.serialRedisTemplate = serialRedisTemplate;
        this.serialProperties = serialProperties;
    }

    /**
     * Build a serial key.
     *
     * @param tag tag of the serial
     * @return key of a serial
     */
    public String buildKey(String tag) {
        return appName + ":serial:" + tag;
    }

    /**
     * Get the next available serial for specific tag.
     *
     * @param tag tag of the serial
     * @return next available serial
     */
    public Long nextSerial(String tag) {
        var key = buildKey(tag);
        var next = Optional.ofNullable(serialRedisTemplate.opsForValue().get(key))
                .orElse(serialProperties.getStartSerial());
        serialRedisTemplate.opsForValue().set(key, next + 1);
        return next;
    }

    /**
     * Reset all serial values.
     */
    public void reset() {
        var keys = serialRedisTemplate.keys(buildKey("*"));
        var startSerial = serialProperties.getStartSerial();

        if (!keys.isEmpty()) {
            for (var key : keys) {
                serialRedisTemplate.opsForValue().set(key, startSerial);
                log.debug("Serial {} has been reset to {}", key, startSerial);
            }
        }
    }

}
