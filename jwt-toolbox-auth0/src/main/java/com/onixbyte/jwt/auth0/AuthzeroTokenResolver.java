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

package com.onixbyte.jwt.auth0;

import com.onixbyte.common.util.Base64Util;
import com.onixbyte.crypto.algorithm.ecdsa.ECPrivateKeyLoader;
import com.onixbyte.identitygenerator.IdentityGenerator;
import com.onixbyte.jwt.TokenPayload;
import com.onixbyte.jwt.TokenResolver;
import com.onixbyte.jwt.annotations.ExcludeFromPayload;
import com.onixbyte.jwt.annotations.TokenEnum;
import com.onixbyte.jwt.constants.PredefinedKeys;
import com.onixbyte.jwt.constants.TokenAlgorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.onixbyte.jwt.exceptions.IllegalKeyPairException;
import com.onixbyte.jwt.exceptions.IllegalSecretException;
import com.onixbyte.jwt.exceptions.UnsupportedAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The {@code AuthzeroTokenResolver} class is an implementation of the {@link TokenResolver}
 * interface. It uses the {@code com.auth0:java-jwt} library to handle JSON Web Token (JWT)
 * resolution. This resolver provides functionality to create, extract, verify, and renew JWT
 * tokens using various algorithms and custom payload data.
 * <p>
 * <b>Usage:</b>
 * To use the {@code AuthzeroTokenResolver}, first, create an instance of this class:
 * <pre>{@code
 * TokenResolver<DecodedJWT> tokenResolver =
 *     new AuthzeroTokenResolver(TokenAlgorithm.HS256,
 *                               "Token Subject",
 *                               "Token Issuer",
 *                               "Token Secret");
 * }</pre>
 * <p>
 * Then, you can utilize the various methods provided by this resolver to handle JWT tokens:
 * <pre>{@code
 * // Creating a new JWT token
 * String token =
 *     tokenResolver.createToken(Duration.ofHours(1),
 *                               "your_subject",
 *                               "your_audience",
 *                               customPayloads);
 *
 * // Extracting payload data from a JWT token
 * DecodedJWT decodedJWT = tokenResolver.resolve(token);
 * T payloadData = decodedJWT.extract(token, T.class);
 *
 * // Renewing an existing JWT token
 * String renewedToken =
 *     tokenResolver.renew(token, Duration.ofMinutes(30), customPayloads);
 * }</pre>
 * <p>
 * <b>Note:</b>
 * It is essential to configure the appropriate algorithms, secret, and issuer according to your
 * specific use case when using this resolver. Additionally, ensure that the
 * {@code com.auth0:java-jwt} library is correctly configured in your project's dependencies.
 *
 * @author Zihlu Wang
 * @version 1.1.1
 * @see IdentityGenerator
 * @see Algorithm
 * @see JWTVerifier
 * @see JWTCreator
 * @see JWTCreator.Builder
 * @since 1.0.0
 */
public class AuthzeroTokenResolver implements TokenResolver<DecodedJWT> {

    private final static Logger log = LoggerFactory.getLogger(AuthzeroTokenResolver.class);

    /**
     * Create a builder of {@link AuthzeroTokenResolver}.
     *
     * @return a builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link AuthzeroTokenResolver}
     */
    public static class Builder {

        /**
         * IdentityGenerator used for generating unique identifiers for "jti" claim in JWT tokens.
         */
        private IdentityGenerator<?> jtiCreator;

        /**
         * The algorithm used for signing and verifying JWT tokens.
         */
        private Algorithm algorithm;

        /**
         * The issuer claim value to be included in JWT tokens.
         */
        private String issuer;

        /**
         * Jackson JSON handler.
         */
        private ObjectMapper objectMapper;

        /**
         * The secret to sign a JWT with HMAC based algorithm.
         */
        private String secret;

        /**
         * The private key to sign a JWT with ECDSA based algorithm.
         */
        private ECPrivateKey privateKey;

        /**
         * The public key to read a JWT with ECDSA based algorithm.
         */
        private ECPublicKey publicKey;

        /**
         * Private constructor to prevent instantiation of this utility class.
         */
        private Builder() {
        }

        /**
         * Set the secret to sign a JWT.
         *
         * @param secret the secret
         * @return the builder instance
         */
        public Builder secret(String secret) {
            this.secret = secret;
            return this;
        }

        /**
         * Set the key pair to sign a JWT.
         *
         * @param publicKey  the pem formatted public key text
         * @param privateKey the pem formatted private key text
         * @return the builder instance
         */
        public Builder keyPair(String publicKey, String privateKey) {
            var keyLoader = new ECPrivateKeyLoader();
            // this.publicKey = keyLoader.loadPublicKey(publicKey);
            this.privateKey = keyLoader.loadPrivateKey(privateKey);
            return this;
        }

        /**
         * Set the algorithm to sign a JWT.
         * <p>
         * A secret required by HMAC-based algorithms, or key pair required by ECDSA-based
         * algorithms need to be set before initialise an algorithm.
         *
         * @param algorithm an {@link TokenAlgorithm} value
         * @return the builder instance
         */
        public Builder algorithm(TokenAlgorithm algorithm) {
            // check the secret or key pair before algorithm initialised
            if (HMAC_ALGORITHMS.containsKey(algorithm)) {
                if (Objects.isNull(secret) || secret.isBlank()) {
                    throw new IllegalSecretException("""
                            Please specify a secret before define an algorithm.""");
                }
            } else if (ECDSA_ALGORITHMS.containsKey(algorithm)) {
                if (Objects.isNull(publicKey) || Objects.isNull(privateKey)) {
                    throw new IllegalKeyPairException("""
                            Please specify a ECDSA key pair before define an algorithm.""");
                }
            }

            // initialise algorithm
            this.algorithm = switch (algorithm) {
                case HS256, HS384, HS512 -> HMAC_ALGORITHMS.get(algorithm).apply(secret);
                case ES256, ES384, ES512 -> ECDSA_ALGORITHMS.get(algorithm)
                        .apply(publicKey, privateKey);
                default -> throw new UnsupportedAlgorithmException("""
                        This algorithm is not supported yet.""");
            };
            return this;
        }

        /**
         * Set the object mapper.
         *
         * @param objectMapper an object mapper
         * @return the builder instance
         */
        public Builder objectMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
            return this;
        }

        /**
         * Set the creator of JWT id.
         *
         * @param jtiCreator a creator to create JWT id
         * @return the builder instance
         */
        public Builder jtiCreator(IdentityGenerator<?> jtiCreator) {
            this.jtiCreator = jtiCreator;
            return this;
        }

        /**
         * Set the issuer of created JWT.
         *
         * @param issuer the person or organisation issued this JWT
         * @return the builder instance
         */
        public Builder issuer(String issuer) {
            this.issuer = issuer;
            return this;
        }

        /**
         * Create an {@link AuthzeroTokenResolver} instance
         *
         * @return created instance
         */
        public AuthzeroTokenResolver build() {
            return new AuthzeroTokenResolver(jtiCreator, algorithm, issuer, objectMapper);
        }
    }

    /**
     * Creates a new token with the specified expiration duration, subject, and
     * audience.
     *
     * @param expireAfter the duration after which the token will expire
     * @param subject     the subject of the token
     * @param audience    the audience for which the token is intended
     * @return the generated token as a {@code String}
     */
    @Override
    public String createToken(Duration expireAfter, String audience, String subject) {
        final var builder = JWT.create();
        buildBasicInfo(builder, expireAfter, subject, audience);
        return buildToken(builder);
    }

    /**
     * Creates a new token with the specified expiration time, subject,
     * audience, and custom payload data.
     *
     * @param expireAfter the duration after which the token will expire
     * @param subject     the subject of the token
     * @param audience    the audience for which the token is intended
     * @param payloads    the custom payload data to be included in the token
     * @return the generated token as a {@code String}
     */
    @Override
    public String createToken(Duration expireAfter, String audience, String subject, Map<String, Object> payloads) {
        // Create token.
        final var builder = JWT.create();
        buildBasicInfo(builder, expireAfter, subject, audience);
        buildMapClaims(builder, payloads);
        return buildToken(builder);
    }

    /**
     * Creates a new token with the specified expiration time, subject,
     * audience, and strongly-typed payload data.
     *
     * @param expireAfter the duration after which the token will expire
     * @param subject     the subject of the token
     * @param audience    the audience for which the token is intended
     * @param payload     the strongly-typed payload data to be included in the
     *                    token
     * @return the generated token as a {@code String}
     */
    @Override
    public <T extends TokenPayload> String createToken(Duration expireAfter, String audience, String subject, T payload) {
        final var builder = JWT.create();
        buildBasicInfo(builder, expireAfter, subject, audience);

        var payloadClass = payload.getClass();
        var fields = payloadClass.getDeclaredFields();

        for (var field : fields) {
            try {
                var fieldName = field.getName();
                // Skip the fields which are annotated with ExcludeFromPayload
                if (field.isAnnotationPresent(ExcludeFromPayload.class))
                    continue;

                Object invokeObj = payload;
                var getter = payloadClass.getDeclaredMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
                if (field.isAnnotationPresent(TokenEnum.class)) {
                    var tokenEnum = field.getAnnotation(TokenEnum.class);
                    invokeObj = getter.invoke(payload);
                    getter = field.getType().getDeclaredMethod("get" + tokenEnum.propertyName().substring(0, 1).toUpperCase() + tokenEnum.propertyName().substring(1));
                }

                // Build Claims
                addClaim(builder, fieldName, getter.invoke(invokeObj));
            } catch (IllegalAccessException e) {
                log.error("Cannot access field {}!", field.getName());
            } catch (NoSuchMethodException e) {
                log.error("Unable to find setter according to given field name.", e);
            } catch (InvocationTargetException e) {
                log.info("Cannot invoke method.", e);
            }
        }

        return buildToken(builder);
    }

    /**
     * Resolves the given token into a {@link DecodedJWT} object.
     *
     * @param token the token to be resolved
     * @return a {@link DecodedJWT} object
     */
    @Override
    public DecodedJWT resolve(String token) {
        return jwtVerifier.verify(token);
    }

    /**
     * Extracts the payload information from the given token and maps it to the
     * specified target type.
     *
     * @param token      the token from which to extract the payload
     * @param targetType the target class representing the payload data type
     * @return an instance of the specified target type with the extracted
     * payload data, or {@code null} when extraction fails
     */
    @Override
    public <T extends TokenPayload> T extract(String token, Class<T> targetType) {
        try {
            // Get claims from token.
            var payloads = objectMapper.readValue(Base64Util.decodeUrlComponents(resolve(token).getPayload()), new MapTypeReference());
            // Get the no-argument constructor to create an instance.
            var bean = targetType.getConstructor().newInstance();

            for (var entry : payloads.entrySet()) {
                // Jump all JWT pre-defined properties and the fields that are annotated to be excluded.
                if (PredefinedKeys.KEYS.contains(entry.getKey()) || targetType.getDeclaredField(entry.getKey()).isAnnotationPresent(ExcludeFromPayload.class))
                    continue;

                var field = targetType.getDeclaredField(entry.getKey());
                var setter = targetType.getDeclaredMethod("set" + entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1), field.getType());
                var fieldValue = entry.getValue();
                if (field.isAnnotationPresent(TokenEnum.class)) {
                    var annotation = field.getAnnotation(TokenEnum.class);
                    var enumStaticLoader = field.getType().getDeclaredMethod("loadBy" + annotation.propertyName().substring(0, 1).toUpperCase() + annotation.propertyName().substring(1), annotation.dataType().getMappedClass());
                    fieldValue = enumStaticLoader.invoke(null, fieldValue);
                }

                if (setter.canAccess(bean)) {
                    setter.invoke(bean, fieldValue);
                } else {
                    log.error("Setter for field {} can't be accessed.", entry.getKey());
                }
            }
            return bean;
        } catch (JsonProcessingException e) {
            log.error("Unable to read payload as a Map<String, Object>.", e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            log.error("Unable to load the constructor or setter.", e);
        } catch (NoSuchFieldException e) {
            log.error("Unable to load the field.", e);
        }
        return null;
    }

    /**
     * Re-generate a new token with the payload in the old one.
     *
     * @param oldToken    the old token
     * @param expireAfter how long the new token can be valid for
     * @return re-generated token with the payload in the old one or
     * {@code null} if an {@link JsonProcessingException} occurred.
     */
    @Override
    public String renew(String oldToken, Duration expireAfter) {
        var resolved = resolve(oldToken);

        try {
            var payload = objectMapper.readValue(Base64Util.decodeUrlComponents(resolved.getPayload()), ObjectNode.class);
            payload.remove(PredefinedKeys.KEYS);

            var payloadMap = objectMapper.convertValue(payload, new MapTypeReference());
            return createToken(expireAfter, resolved.getAudience().get(0), resolved.getSubject(), payloadMap);
        } catch (JsonProcessingException e) {
            log.error("Cannot read payload content, error details:", e);
        }

        return null;
    }

    /**
     * Renews the given expired token with the specified custom payload data.
     *
     * @param oldToken the expired token to be renewed
     * @param payload  the custom payload data to be included in the renewed
     *                 token
     * @return the renewed token as a {@code String}
     */
    @Override
    public String renew(String oldToken, Duration expireAfter, Map<String, Object> payload) {
        final var resolvedToken = this.resolve(oldToken);
        var audience = resolvedToken.getAudience().get(0);

        return createToken(expireAfter, audience, resolvedToken.getSubject(), payload);
    }

    /**
     * Renews the given expired token with the specified custom payload data.
     *
     * @param oldToken the expiring token to be renewed
     * @param payload  the custom payload data to be included in the renewed
     *                 token
     * @return the renewed token as a {@code String}
     */
    @Override
    public String renew(String oldToken, Map<String, Object> payload) {
        return renew(oldToken, Duration.ofMinutes(30), payload);
    }

    /**
     * Renews the given expired token with the new specified strongly-typed
     * payload data.
     *
     * @param oldToken the expiring token to be renewed
     * @param payload  the strongly-typed payload data to be included in the
     *                 renewed token
     * @return the renewed token as a {@code String}
     */
    @Override
    public <T extends TokenPayload> String renew(String oldToken, Duration expireAfter, T payload) {
        final var resolvedToken = this.resolve(oldToken);
        var audience = resolvedToken.getAudience().get(0);

        return createToken(expireAfter, audience, resolvedToken.getSubject(), payload);
    }

    /**
     * Renews the given expired token with the new specified strongly-typed
     * payload data.
     *
     * @param <T>      the type of the payload data, must implement
     *                 {@link TokenPayload}
     * @param oldToken the expired token to be renewed
     * @param payload  the strongly-typed payload data to be included in the
     *                 renewed token
     * @return the renewed token as a {@link String}
     */
    @Override
    public <T extends TokenPayload> String renew(String oldToken, T payload) {
        return renew(oldToken, Duration.ofMinutes(30), payload);
    }

    /**
     * Builds the basic information of the JSON Web Token (JWT) using the
     * provided parameters and adds it to the JWTCreator.Builder.
     *
     * @param subject     the subject claim value to be included in the JWT
     * @param audience    an array of audience claim values to be included in
     *                    the JWT
     * @param expireAfter the duration after which the JWT will expire
     * @param builder     the JWTCreator.Builder instance to which the basic
     *                    information will be added
     */
    private void buildBasicInfo(JWTCreator.Builder builder, Duration expireAfter, String subject, String... audience) {
        var now = LocalDateTime.now();

        // bind issuer (iss)
        builder.withIssuer(issuer);
        // bind issued at (iat)
        builder.withIssuedAt(now.atZone(ZoneId.systemDefault()).toInstant());
        // bind not before (nbf)
        builder.withNotBefore(now.atZone(ZoneId.systemDefault()).toInstant());
        // bind audience (aud)
        builder.withAudience(audience);
        // bind subject (sub)
        builder.withSubject(subject);
        // bind expire at (exp)
        builder.withExpiresAt(now.plus(expireAfter).atZone(ZoneId.systemDefault()).toInstant());
        // bind JWT Id (jti)
        builder.withJWTId(jtiCreator.nextId().toString());
    }

    /**
     * Add a claim to a builder.
     *
     * @param builder the builder to build this JSON Web Token
     * @param name    the property name
     * @param value   the property value
     */
    private void addClaim(JWTCreator.Builder builder, String name, Object value) {
        if (Objects.nonNull(value)) {
            if (value instanceof Boolean v) {
                builder.withClaim(name, v);
            } else if (value instanceof Double v) {
                builder.withClaim(name, v);
            } else if (value instanceof Float v) {
                builder.withClaim(name, v.doubleValue());
            } else if (value instanceof Integer v) {
                builder.withClaim(name, v);
            } else if (value instanceof Long v) {
                builder.withClaim(name, v);
            } else if (value instanceof String v) {
                builder.withClaim(name, v);
            } else if (value instanceof Date v) {
                builder.withClaim(name, v);
            } else if (value instanceof List<?> v) {
                builder.withClaim(name, v);
            } else {
                log.warn("""
                        Unable to determine the type of field {}, we will handle it as a String.""", name);
                builder.withClaim(name, value.toString());
            }
        } else {
            builder.withNullClaim(name);
        }
    }

    /**
     * Builds the custom claims of the JSON Web Token (JWT) using the provided
     * Map of claims and adds them to the JWTCreator.Builder.
     * <p>
     * This method is used to add custom claims to the JWT. It takes a Map of
     * claims, where each entry represents a custom claim name (key) and its
     * corresponding value (value). The custom claims will be added to the JWT
     * using the JWTCreator.Builder.
     *
     * @param claims  a Map containing the custom claims to be added to the JWT
     * @param builder the JWTCreator.Builder instance to which the custom
     *                claims will be added
     */
    private void buildMapClaims(JWTCreator.Builder builder, Map<String, Object> claims) {
        if (Objects.nonNull(claims)) {
            for (var e : claims.entrySet()) {
                addClaim(builder, e.getKey(), e.getValue());
            }
        }
    }

    /**
     * Finish creating a token.
     * <p>
     * This is the final step of create a token, to sign this token.
     *
     * @param builder the builder to build this JWT
     * @return the generated token as a {@code String}
     */
    private String buildToken(JWTCreator.Builder builder) {
        return builder.sign(algorithm);
    }

    /**
     * Default type reference for Map.
     */
    public static class MapTypeReference extends TypeReference<Map<String, Object>> {
        MapTypeReference() {
        }
    }

    /**
     * IdentityGenerator used for generating unique identifiers for "jti" claim in JWT tokens.
     */
    private final IdentityGenerator<?> jtiCreator;

    /**
     * The algorithm used for signing and verifying JWT tokens.
     */
    private final Algorithm algorithm;

    /**
     * The issuer claim value to be included in JWT tokens.
     */
    private final String issuer;

    /**
     * The JSON Web Token resolver.
     */
    private final JWTVerifier jwtVerifier;

    /**
     * Jackson JSON handler.
     */
    private final ObjectMapper objectMapper;

    /**
     * A map contains all HMAC-SHA based algorithms.
     */
    public static final Map<TokenAlgorithm, Function<String, Algorithm>> HMAC_ALGORITHMS = Map.of(
            TokenAlgorithm.HS256, Algorithm::HMAC256,
            TokenAlgorithm.HS384, Algorithm::HMAC384,
            TokenAlgorithm.HS512, Algorithm::HMAC512
    );

    /**
     * A map contains all ECDSA based algorithms.
     */
    public static final Map<TokenAlgorithm, BiFunction<ECPublicKey, ECPrivateKey, Algorithm>> ECDSA_ALGORITHMS = Map.of(
            TokenAlgorithm.ES256, Algorithm::ECDSA256,
            TokenAlgorithm.ES384, Algorithm::ECDSA384,
            TokenAlgorithm.ES512, Algorithm::ECDSA512
    );

    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * @param jtiCreator   a creator that can create JWT id
     * @param algorithm    an algorithm to sign this JWT
     * @param issuer       the person or organisation who issued this JWT
     * @param objectMapper a mapper for handling JSON serialisation and deserialization
     */
    private AuthzeroTokenResolver(IdentityGenerator<?> jtiCreator, Algorithm algorithm, String issuer, ObjectMapper objectMapper) {
        this.jtiCreator = jtiCreator;
        this.algorithm = algorithm;
        this.issuer = issuer;
        this.objectMapper = objectMapper;
        this.jwtVerifier = JWT.require(algorithm).build();
    }

}
