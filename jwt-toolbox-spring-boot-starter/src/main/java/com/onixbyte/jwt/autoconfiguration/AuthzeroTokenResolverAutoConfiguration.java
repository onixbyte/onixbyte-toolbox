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

package com.onixbyte.jwt.autoconfiguration;

import com.onixbyte.identitygenerator.IdentityGenerator;
import com.onixbyte.jwt.TokenResolver;
import com.onixbyte.jwt.auth0.AuthzeroTokenResolver;
import com.onixbyte.jwt.autoconfiguration.properties.SimpleJwtProperties;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onixbyte.jwt.constants.TokenAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * {@code AuthzeroTokenResolverAutoConfiguration} is responsible for automatically configuring the
 * Simple JWT library with
 * {@code com.auth0:java-jwt} when used in a Spring Boot application. It provides default settings
 * and configurations to ensure that the library works smoothly without requiring
 * manual configuration.
 * <p>
 * This autoconfiguration class sets up the necessary beans and components required for JWT
 * generation and validation. It automatically creates and configures the
 * {@link AuthzeroTokenResolver} bean based on the available options and properties.
 * <p>
 * Developers using the Simple JWT library with Spring Boot do not need to explicitly configure the
 * library, as the autoconfiguration takes care of setting up the necessary components and
 * configurations automatically. However, developers still have the flexibility to customise the
 * behavior of the library by providing their own configurations and properties.
 *
 * @author zihluwang
 * @version 1.6.0
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(value = {SimpleJwtProperties.class})
@ConditionalOnClass({DecodedJWT.class, AuthzeroTokenResolver.class})
@ConditionalOnMissingBean({TokenResolver.class})
@ConditionalOnBean(value = {IdentityGenerator.class}, name = "jtiCreator")
@AutoConfigureAfter(value = GuidAutoConfiguration.class)
public class AuthzeroTokenResolverAutoConfiguration {

    private final static Logger log = LoggerFactory.getLogger(AuthzeroTokenResolverAutoConfiguration.class);

    /**
     * Constructs a new {@code SimpleJwtAutoConfiguration} instance with the
     * provided SimpleJwtProperties.
     *
     * @param simpleJwtProperties a {@link SimpleJwtProperties} instance
     * @param jtiCreator          a creator to create ids for JSON Web Token
     * @param objectMapper        jackson JSON Handler
     */
    @Autowired
    public AuthzeroTokenResolverAutoConfiguration(SimpleJwtProperties simpleJwtProperties,
                                                  @Qualifier("jtiCreator") IdentityGenerator<?> jtiCreator,
                                                  ObjectMapper objectMapper) {
        this.jtiCreator = jtiCreator;
        this.simpleJwtProperties = simpleJwtProperties;
        this.objectMapper = objectMapper;
    }

    /**
     * Creates a new {@link TokenResolver} bean using {@link AuthzeroTokenResolver} if no existing
     * {@link TokenResolver} bean is found. The {@link AuthzeroTokenResolver} is configured with the
     * provided {@link IdentityGenerator}, {@code algorithm}, {@code issuer}, and {@code secret}
     * properties from {@link SimpleJwtProperties}.
     *
     * @return the {@link TokenResolver} instance
     */
    @Bean
    public TokenResolver<DecodedJWT> tokenResolver() {
        var builder = AuthzeroTokenResolver.builder();

        if (TokenAlgorithm.ECDSA_ALGORITHMS.contains(simpleJwtProperties.getAlgorithm())) {
            builder.keyPair(simpleJwtProperties.getPublicKey(), simpleJwtProperties.getPrivateKey())
                    .algorithm(simpleJwtProperties.getAlgorithm());
        } else if (TokenAlgorithm.HMAC_ALGORITHMS.contains(simpleJwtProperties.getAlgorithm())) {
            builder.secret(simpleJwtProperties.getSecret())
                    .algorithm(simpleJwtProperties.getAlgorithm());
        }

        builder.issuer(simpleJwtProperties.getIssuer());
        builder.jtiCreator(jtiCreator);
        builder.objectMapper(objectMapper);

        return builder.build();
    }

    private final IdentityGenerator<?> jtiCreator;

    private final SimpleJwtProperties simpleJwtProperties;

    private final ObjectMapper objectMapper;

}
