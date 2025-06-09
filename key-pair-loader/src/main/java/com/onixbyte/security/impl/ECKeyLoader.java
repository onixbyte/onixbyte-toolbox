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

package com.onixbyte.security.impl;

import com.onixbyte.security.KeyLoader;
import com.onixbyte.security.exception.KeyLoadingException;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Key pair loader for loading key pairs for ECDSA-based algorithms.
 * <p>
 *
 * <b>Example usage for ECDSA:</b>
 * <pre>{@code
 * KeyLoader keyLoader = new EcKeyLoader();
 * String pemPrivateKey = """
 *                        -----BEGIN EC PRIVATE KEY-----
 *                        ...
 *                        -----END EC PRIVATE KEY-----""";
 * ECPrivateKey privateKey = KeyLoader.loadEcdsaPrivateKey(pemPrivateKey);
 *
 * String pemPublicKey = """
 *                       -----BEGIN EC PUBLIC KEY-----
 *                       ...
 *                       -----END EC PUBLIC KEY-----""";
 * ECPublicKey publicKey = KeyLoader.loadPublicKey(pemPublicKey);
 * }</pre>
 *
 * @author zihluwang
 * @version 2.0.0
 * @since 2.0.0
 */
public class ECKeyLoader implements KeyLoader {

    private final KeyFactory keyFactory;

    private final Base64.Decoder decoder;

    /**
     * Initialise a key loader for EC-based algorithms.
     */
    public ECKeyLoader() {
        try {
            this.keyFactory = KeyFactory.getInstance("EC");
            this.decoder = Base64.getDecoder();
        } catch (NoSuchAlgorithmException e) {
            throw new KeyLoadingException(e);
        }
    }

    /**
     * Load ECDSA private key from pem-formatted key text.
     *
     * @param pemKeyText pem-formatted key text
     * @return loaded private key
     * @throws KeyLoadingException if the generated key is not a {@link ECPrivateKey} instance,
     *                             or EC Key Factory is not loaded, or key spec is invalid
     */
    @Override
    public ECPrivateKey loadPrivateKey(String pemKeyText) {
        try {
            pemKeyText = getRawContent(pemKeyText);
            var decodedKeyString = decoder.decode(pemKeyText);
            var keySpec = new PKCS8EncodedKeySpec(decodedKeyString);

            var _key = keyFactory.generatePrivate(keySpec);
            if (_key instanceof ECPrivateKey privateKey) {
                return privateKey;
            } else {
                throw new KeyLoadingException("Unable to load private key from pem-formatted key text.");
            }
        } catch (InvalidKeySpecException e) {
            throw new KeyLoadingException("Key spec is invalid.", e);
        }
    }

    /**
     * Load public key from pem-formatted key text.
     *
     * @param pemKeyText pem-formatted key text
     * @return loaded private key
     * @throws KeyLoadingException if the generated key is not a {@link ECPrivateKey} instance,
     *                             or EC Key Factory is not loaded, or key spec is invalid
     */
    @Override
    public ECPublicKey loadPublicKey(String pemKeyText) {
        try {
            pemKeyText = getRawContent(pemKeyText);
            var keyBytes = decoder.decode(pemKeyText);
            var spec = new X509EncodedKeySpec(keyBytes);
            var key = keyFactory.generatePublic(spec);
            if (key instanceof ECPublicKey publicKey) {
                return publicKey;
            } else {
                throw new KeyLoadingException("Unable to load public key from pem-formatted key text.");
            }
        } catch (InvalidKeySpecException e) {
            throw new KeyLoadingException("Key spec is invalid.", e);
        }
    }

}
