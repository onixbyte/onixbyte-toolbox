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

package com.onixbyte.crypto.algorithm.ecdsa;

import com.onixbyte.crypto.PrivateKeyLoader;
import com.onixbyte.crypto.exception.KeyLoadingException;
import com.onixbyte.crypto.util.CryptoUtil;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

/**
 * Key pair loader for loading key pairs for ECDSA-based algorithms.
 * <p>
 *
 * <b>Example usage for ECDSA:</b>
 * <pre>{@code
 * PrivateKeyLoader keyLoader = new EcKeyLoader();
 * String pemPrivateKey = """
 *                        -----BEGIN EC PRIVATE KEY-----
 *                        ...
 *                        -----END EC PRIVATE KEY-----""";
 * ECPrivateKey privateKey = PrivateKeyLoader.loadEcdsaPrivateKey(pemPrivateKey);
 *
 * String pemPublicKey = """
 *                       -----BEGIN EC PUBLIC KEY-----
 *                       ...
 *                       -----END EC PUBLIC KEY-----""";
 * ECPublicKey publicKey = PrivateKeyLoader.loadPublicKey(pemPublicKey);
 * }</pre>
 *
 * @author zihluwang
 * @version 2.0.0
 * @since 2.0.0
 */
public class ECPrivateKeyLoader implements PrivateKeyLoader {

    private final KeyFactory keyFactory;

    private final Base64.Decoder decoder;

    /**
     * Initialise a key loader for EC-based algorithms.
     */
    public ECPrivateKeyLoader() {
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
            pemKeyText = CryptoUtil.getRawContent(pemKeyText);
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
}
