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

package com.onixbyte.propertyguard.autoconfiguration;

import com.onixbyte.devkit.utils.AesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Optional;

/**
 * {@code PropertyGuard} is a utility class designed for encrypting configuration properties in
 * Spring Boot applications.
 * <p>
 * Spring Boot applications often need to store sensitive configuration details such as database
 * passwords, API keys, etc. To ensure that these sensitive pieces of information are not exposed
 * to the public, developers can utilize the {@code PropertyGuard} class to encrypt and store them
 * within configuration files.
 * <p>
 * <b>Usage</b>
 * You need a 16-char long secret for encrypting a configuration property. You can get this secret
 * on your own, or use the helper utility class by the following code:
 * <pre>{@code
 * var secret = AesUtil.generateRandomSecret(); // Let's presume the result is "3856faef0d2d4f33"
 * }</pre>
 * <p>
 * Then, in {@code application.yml} or {@code application.properties}, change the original value
 * from plain text to encrypted value with the prefix "<code>pg:</code>".
 * <pre>{@code
 * # original
 * app.example-properties=Sample Value
 *
 * # encrypted with key 3856faef0d2d4f33
 * app.example-properties=pg:t4YBfv8M9ZmTzWgTi2gJqg==
 * }</pre>
 * After that, before running, you need to add the command line arguments "pg.key" as the following
 * codes: {@code --pg.key=<the secret>}.
 * <p>
 * This class is extracted from <a href="https://baomidou.com/pages/e0a5ce/">MyBatis-Plus</a>.
 *
 * @author hubin@baomidou
 * @version 1.1.0
 * @see EnvironmentPostProcessor
 * @since 1.1.0 (3.3.2 of MyBatis-Plus)
 */
@Deprecated(forRemoval = true)
public class PropertyGuard implements EnvironmentPostProcessor {

    private final static Logger log = LoggerFactory.getLogger(PropertyGuard.class);

    /**
     * Create a {@link PropertyGuard} instance.
     */
    public PropertyGuard() {
    }

    /**
     * Process the encryption environment variables.
     *
     * @param environment the environment to post-process
     * @param application the application to which the environment belongs
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // Get the key for encryption from command line.
        var encryptionKey = "";
        for (var ps : environment.getPropertySources()) {
            if (ps instanceof SimpleCommandLinePropertySource source) {
                encryptionKey = source.getProperty("%s.key".formatted(PREFIX));
                break;
            }
        }

        if (Optional.ofNullable(encryptionKey).map((key) -> !key.isEmpty()).orElse(false)) {
            var map = new HashMap<String, Object>();
            for (var propertySources : environment.getPropertySources()) {
                if (propertySources instanceof OriginTrackedMapPropertySource source) {
                    for (var name : source.getPropertyNames()) {
                        if (source.getProperty(name) instanceof String str) {
                            if (str.startsWith("%s:".formatted(PREFIX))) {
                                try {
                                    map.put(name, AesUtil.decrypt(str.substring(3), encryptionKey));
                                } catch (GeneralSecurityException e) {
                                    log.error("Unable to decrypt param {}", name);
                                }
                            }
                        }
                    }
                }
            }
            // Put the decrypted data into environment variables, and made them at top-level.
            if (!map.isEmpty()) {
                environment.getPropertySources().addFirst(new MapPropertySource("custom-encrypt", map));
            }
        }
    }

    private static final String PREFIX = "pg";
}
