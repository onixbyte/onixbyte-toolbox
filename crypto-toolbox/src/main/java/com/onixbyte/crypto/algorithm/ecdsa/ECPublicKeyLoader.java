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

import com.onixbyte.crypto.PublicKeyLoader;
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

public class ECPublicKeyLoader implements PublicKeyLoader {

    /**
     * Supported curves.
     */
    public static final Set<String> SUPPORTED_CURVES = new HashSet<>(Set.of(
        "secp256r1", "secp384r1", "secp521r1", "secp224r1"
    ));

    private final KeyFactory keyFactory;

    private final Base64.Decoder decoder;

    /**
     * Initialise a key loader for EC-based algorithms.
     */
    public ECPublicKeyLoader() {
        try {
            this.keyFactory = KeyFactory.getInstance("EC");
            this.decoder = Base64.getDecoder();
        } catch (NoSuchAlgorithmException e) {
            throw new KeyLoadingException(e);
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
            pemKeyText = CryptoUtil.getRawContent(pemKeyText);
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

    /**
     * Loads an EC public key from the given hexadecimal x and y coordinates alongside the curve name.
     * <p>
     * This method converts the hexadecimal string representations of the EC point coordinates into {@link BigInteger}
     * instances, then constructs an {@link ECPoint} and retrieves the corresponding {@link ECParameterSpec} for the
     * named curve. Subsequently, it utilises the {@link KeyFactory} to generate an {@link ECPublicKey}.
     * <p>
     * Only curves listed in {@link #SUPPORTED_CURVES} are supported. Should the specified curve name be unsupported,
     * or if key construction fails due to invalid parameters or unsupported algorithms, a {@link KeyLoadingException}
     * will be thrown.
     *
     * @param xHex      the hexadecimal string representing the x-coordinate of the EC point
     * @param yHex      the hexadecimal string representing the y-coordinate of the EC point
     * @param curveName the name of the elliptic curve
     * @return the {@link ECPublicKey} generated from the specified coordinates and curve
     * @throws KeyLoadingException if the curve is unsupported or key generation fails
     */
    @Override
    public ECPublicKey loadPublicKey(String xHex, String yHex, String curveName) {
        if (!SUPPORTED_CURVES.contains(curveName)) {
            throw new KeyLoadingException("Given curve is not supported yet.");
        }

        try {
            // Convert hex string coordinates to BigInteger
            var x = new BigInteger(xHex, 16);
            var y = new BigInteger(yHex, 16);

            // Create ECPoint with (x, y)
            var ecPoint = new ECPoint(x, y);

            // Get EC parameter spec for the named curve
            var parameters = AlgorithmParameters.getInstance("EC");
            parameters.init(new ECGenParameterSpec(curveName));
            var ecParameterSpec = parameters.getParameterSpec(ECParameterSpec.class);

            // Create ECPublicKeySpec with point and curve params
            var pubSpec = new ECPublicKeySpec(ecPoint, ecParameterSpec);

            // Generate public key using KeyFactory
            var publicKey = keyFactory.generatePublic(pubSpec);

            if (publicKey instanceof ECPublicKey ecPublicKey) {
                return ecPublicKey;
            } else {
                throw new KeyLoadingException("Cannot load EC public key with given x, y and curve name.");
            }
        } catch (NoSuchAlgorithmException | InvalidParameterSpecException | InvalidKeySpecException e) {
            throw new KeyLoadingException("Cannot load EC public key with given x, y and curve name.", e);
        }
    }
}
