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
import com.onixbyte.crypto.PublicKeyLoader;
import com.onixbyte.crypto.exception.KeyLoadingException;
import com.onixbyte.crypto.util.CryptoUtil;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * A class responsible for loading public RSA keys from PEM formatted text.
 * <p>
 * This class implements the {@link PublicKeyLoader} interface and provides methods to load public
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
public class RSAPublicKeyLoader implements PublicKeyLoader {

    private final Base64.Decoder decoder;

    private final Base64.Decoder urlDecoder;

    private final KeyFactory keyFactory;

    /**
     * Constructs an instance of {@code RsaKeyLoader}.
     * <p>
     * This constructor initialises the Base64 decoder and the RSA {@link KeyFactory}. It may throw
     * a {@link KeyLoadingException} if the RSA algorithm is not available.
     */
    public RSAPublicKeyLoader() {
        try {
            this.decoder = Base64.getDecoder();
            this.urlDecoder = Base64.getUrlDecoder();
            this.keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new KeyLoadingException(e);
        }
    }

    /**
     * Loads an RSA public key from a given PEM formatted key text.
     * <p>
     * This method extracts the raw key content from the provided PEM text, decodes the
     * Base64-encoded content, and generates an instance of {@link RSAPublicKey}. If the key cannot
     * be loaded due to invalid specifications or types, a {@link KeyLoadingException} is thrown.
     *
     * @param pemKeyText the PEM formatted public key text
     * @return an instance of {@link RSAPublicKey}
     * @throws KeyLoadingException if the key loading process encounters an error
     */
    @Override
    public RSAPublicKey loadPublicKey(String pemKeyText) {
        // Extract the raw key content
        var rawKeyContent = CryptoUtil.getRawContent(pemKeyText);

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

    /**
     * Get the public key with given modulus and public exponent.
     *
     * @param modulus  the modulus
     * @param exponent the public exponent
     * @return generated public key object from the provided key specification
     * @see KeyFactory#getInstance(String)
     * @see KeyFactory#generatePublic(KeySpec)
     */
    @Override
    public RSAPublicKey loadPublicKey(String modulus, String exponent) {
        try {
            var _modulus = new BigInteger(1, urlDecoder.decode(modulus));
            var _exponent = new BigInteger(1, urlDecoder.decode(exponent));

            var keySpec = new RSAPublicKeySpec(_modulus, _exponent);
            var kf = KeyFactory.getInstance("RSA");
            if (kf.generatePublic(keySpec) instanceof RSAPublicKey rsaPublicKey) {
                return rsaPublicKey;
            } else {
                throw new KeyLoadingException("Cannot generate RSA public key with given modulus and exponent.");
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new KeyLoadingException("Cannot generate RSA public key with given modulus and exponent.", e);
        }
    }
}
