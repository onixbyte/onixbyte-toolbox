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
 * A class responsible for loading private RSA keys from PEM formatted text.
 * <p>
 * This class implements the {@link PrivateKeyLoader} interface and provides methods to load private
 * RSA keys. The keys are expected to be in the standard PEM format, which includes Base64-encoded
 * key content surrounded by header and footer lines. The class handles the decoding of Base64
 * content and the generation of keys using the RSA key factory.
 * <p>
 * Any exceptions encountered during the loading process are encapsulated in a
 * {@link KeyLoadingException}, allowing for flexible error handling.
 *
 * @author zihluwang
 * @author siujamo
 * @version 3.0.0
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
