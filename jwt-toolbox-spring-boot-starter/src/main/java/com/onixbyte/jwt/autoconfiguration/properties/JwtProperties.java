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

package com.onixbyte.jwt.autoconfiguration.properties;

import com.onixbyte.jwt.SecretCreator;
import com.onixbyte.jwt.constants.TokenAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code JwtProperties} is a configuration properties class used to store the properties
 * related to Simple JWT library configurations. These properties can be configured in the
 * application's properties file (e.g., application.properties) with the prefix
 * "onixbyte.simple-jwt".
 * <p>
 * {@code JwtProperties} provides configuration options for the JWT algorithm, issuer,
 * and secret. The properties are used by the {@code Auth0AutoConfiguration} to
 * set up the necessary configurations for JWT generation and validation.
 * <p>
 * Developers can customise the JWT algorithm, issuer, and secret by setting the corresponding
 * properties in the application's properties file. The {@code Auth0AutoConfiguration} class
 * reads these properties and uses them to create the TokenResolver bean with the
 * desired configuration.
 *
 * @author Zihlu Wang
 * @version 1.1.0
 * @since 1.0.0
 */
// @ConfigurationProperties(prefix = "onixbyte.jwt")
public class JwtProperties {

    /**
     * Default constructor.
     */
    public JwtProperties() {
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

