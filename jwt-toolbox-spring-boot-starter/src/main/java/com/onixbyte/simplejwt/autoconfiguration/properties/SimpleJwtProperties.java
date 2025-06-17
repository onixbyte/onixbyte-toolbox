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

package com.onixbyte.simplejwt.autoconfiguration.properties;

import com.onixbyte.simplejwt.SecretCreator;
import com.onixbyte.simplejwt.autoconfiguration.AuthzeroTokenResolverAutoConfiguration;
import com.onixbyte.simplejwt.constants.TokenAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code SimpleJwtProperties} is a configuration properties class used to store the properties
 * related to Simple JWT library configurations. These properties can be configured in the
 * application's properties file (e.g., application.properties) with the prefix
 * "onixbyte.simple-jwt".
 * <p>
 * {@code SimpleJwtProperties} provides configuration options for the JWT algorithm, issuer,
 * and secret. The properties are used by the {@link AuthzeroTokenResolverAutoConfiguration} to
 * set up the necessary configurations for JWT generation and validation.
 * <p>
 * Developers can customise the JWT algorithm, issuer, and secret by setting the corresponding
 * properties in the application's properties file. The {@code SimpleJwtAutoConfiguration} class
 * reads these properties and uses them to create the TokenResolver bean with the
 * desired configuration.
 *
 * @author Zihlu Wang
 * @version 1.1.0
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "onixbyte.simple-jwt")
public class SimpleJwtProperties {

    /**
     * Default constructor.
     */
    public SimpleJwtProperties() {
    }

    /**
     * The algorithm used for JWT generation and validation. Default value is
     * {@link TokenAlgorithm#HS256}
     */
    private TokenAlgorithm algorithm = TokenAlgorithm.HS256;

    /**
     * The issuer value to be included in the generated JWT. Default value is an empty String.
     */
    private String issuer = "";

    /**
     * The secret key used for JWT generation and validation. Default value is the result of call to
     * {@link SecretCreator#createSecret(int, boolean, boolean, boolean)}.
     */
    private String secret = SecretCreator.createSecret(32, true, true, true);

    /**
     * The private key, PEM formatted.
     */
    private String privateKey;

    /**
     * The public key, PEM formatted
     */
    private String publicKey;

    /**
     * Algorithm getter.
     *
     * @return algorithm
     */
    public TokenAlgorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * Algorithm setter.
     *
     * @param algorithm the algorithm
     */
    public void setAlgorithm(TokenAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Issuer getter.
     *
     * @return issuer
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * Issuer setter.
     *
     * @param issuer the issuer
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    /**
     * Secret setter.
     *
     * @return secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Secret setter.
     *
     * @param secret the secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Private key getter.
     *
     * @return private key
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * Private key setter.
     *
     * @param privateKey private key
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * Public key getter.
     *
     * @return public key
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Public key setter.
     *
     * @param publicKey public key
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}

