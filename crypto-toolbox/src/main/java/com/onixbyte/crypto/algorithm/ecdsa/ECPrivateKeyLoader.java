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
 * <b>Example usage:</b>
 * <pre>{@code
 * PrivateKeyLoader keyLoader = new ECPrivateKeyLoader();
 * String pemPrivateKey = """
 *                        -----BEGIN EC PRIVATE KEY-----
 *                        ...
 *                        -----END EC PRIVATE KEY-----""";
 * ECPrivateKey privateKey = PrivateKeyLoader.loadEcdsaPrivateKey(pemPrivateKey);
 * }</pre>
 *
 * @author zihluwang
 * @version 3.0.0
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
