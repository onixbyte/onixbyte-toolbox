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

package com.onixbyte.crypto.algorithm.rsa;

import com.onixbyte.crypto.PrivateKeyLoader;
import com.onixbyte.crypto.exception.KeyLoadingException;
import com.onixbyte.crypto.util.CryptoUtil;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.Base64;

/**
 * A class responsible for loading RSA keys from PEM formatted text.
 * <p>
 * This class implements the {@link PrivateKeyLoader} interface and provides methods to load both private
 * and public RSA keys. The keys are expected to be in the standard PEM format, which includes
 * Base64-encoded key content surrounded by header and footer lines. The class handles the decoding
 * of Base64 content and the generation of keys using the RSA key factory.
 * <p>
 * Any exceptions encountered during the loading process are encapsulated in a
 * {@link KeyLoadingException}, allowing for flexible error handling.
 *
 * @author siujamo
 * @see PrivateKeyLoader
 * @see KeyLoadingException
 */
public class RSAPrivateKeyLoader implements PrivateKeyLoader {

    private final Base64.Decoder decoder;

    private final KeyFactory keyFactory;

    /**
     * Constructs an instance of {@code RsaKeyLoader}.
     * <p>
     * This constructor initialises the Base64 decoder and the RSA {@link KeyFactory}. It may throw
     * a {@link KeyLoadingException} if the RSA algorithm is not available.
     */
    public RSAPrivateKeyLoader() {
        try {
            this.decoder = Base64.getDecoder();
            this.keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new KeyLoadingException(e);
        }
    }

    /**
     * Loads an RSA private key from a given PEM formatted key text.
     * <p>
     * This method extracts the raw key content from the provided PEM text, decodes the
     * Base64-encoded content, and generates an instance of {@link RSAPrivateKey}. If the key cannot
     * be loaded due to invalid specifications or types, a {@link KeyLoadingException} is thrown.
     *
     * @param pemKeyText the PEM formatted private key text
     * @return an instance of {@link RSAPrivateKey}
     * @throws KeyLoadingException if the key loading process encounters an error
     */
    @Override
    public RSAPrivateKey loadPrivateKey(String pemKeyText) {
        // Extract the raw key content
        var rawKeyContent = CryptoUtil.getRawContent(pemKeyText);

        // Decode the Base64-encoded content
        var keyBytes = decoder.decode(rawKeyContent);

        // Create a PKCS8EncodedKeySpec from the decoded bytes
        var keySpec = new PKCS8EncodedKeySpec(keyBytes);

        try {
            // Get an RSA KeyFactory and generate the private key
            var _key = keyFactory.generatePrivate(keySpec);
            if (_key instanceof RSAPrivateKey key) {
                return key;
            } else {
                throw new KeyLoadingException("Unable to load private key from pem-formatted key text.");
            }
        } catch (InvalidKeySpecException e) {
            throw new KeyLoadingException("Key spec is invalid.", e);
        }
    }
}
