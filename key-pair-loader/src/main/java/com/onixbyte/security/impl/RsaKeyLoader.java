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
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaKeyLoader implements KeyLoader {

    private final Base64.Decoder decoder;
    private final KeyFactory keyFactory;

    public RsaKeyLoader() {
        try {
            this.decoder = Base64.getDecoder();
            this.keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new KeyLoadingException(e);
        }
    }

    @Override
    public RSAPrivateKey loadPrivateKey(String pemKeyText) {
        // Extract the raw key content
        var rawKeyContent = getRawContent(pemKeyText);

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

    @Override
    public RSAPublicKey loadPublicKey(String pemKeyText) {
        // Extract the raw key content
        var rawKeyContent = getRawContent(pemKeyText);

        // Decode the Base64-encoded content
        var keyBytes = decoder.decode(rawKeyContent);

        // Create an X509EncodedKeySpec from the decoded bytes
        var keySpec = new X509EncodedKeySpec(keyBytes);

        // Get an RSA KeyFactory and generate the public key
        try {
            var _key = keyFactory.generatePublic(keySpec);
            if (_key instanceof RSAPublicKey key) {
                return key;
            } else {
                throw new KeyLoadingException("Unable to load public key from pem-formatted key text.");
            }
        } catch (InvalidKeySpecException e) {
            throw new KeyLoadingException("Key spec is invalid.", e);
        }
    }
}
