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

package com.onixbyte.security;

import com.onixbyte.security.exception.KeyLoadingException;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * The {@code KeyLoader} class provides utility methods for loading keys pairs from PEM-formatted
 * key text. This class supports loading both private and public keys.
 * <p>
 * The utility methods in this class are useful for scenarios where ECDSA keys need to be loaded
 * from PEM-formatted strings for cryptographic operations.
 *
 * @author zihluwang
 * @version 2.0.0
 * @since 1.6.0
 */
public interface KeyLoader {

    /**
     * Load private key from pem-formatted key text.
     *
     * @param pemKeyText pem-formatted key text
     * @return loaded private key
     */
    PrivateKey loadPrivateKey(String pemKeyText);

    /**
     * Load public key from pem-formatted key text.
     *
     * @param pemKeyText pem-formatted key text
     * @return loaded private key
     */
    PublicKey loadPublicKey(String pemKeyText);

    /**
     * Loads an RSA public key using the provided modulus and exponent.
     * <p>
     * This default implementation throws a {@link KeyLoadingException} to signify that this key loader does not support
     * loading an RSA public key. Implementing classes are expected to override this method to supply their own
     * loading logic.
     *
     * @param modulus  the modulus value of the RSA public key, usually represented in hexadecimal or Base64
     *                 string format
     * @param exponent the public exponent value of the RSA public key, usually represented in hexadecimal or Base64
     *                 string format
     * @return the loaded {@link RSAPublicKey} instance
     * @throws KeyLoadingException if loading is not supported or fails
     */
    default RSAPublicKey loadPublicKey(String modulus, String exponent) {
        throw new KeyLoadingException("This key loader does not support loading an RSA public key.");
    }

    /**
     * Loads an EC public key using the provided x and y coordinates together with the curve name.
     * <p>
     * This default implementation throws a {@link KeyLoadingException} to signify that this key loader does not support
     * loading an EC public key. Implementing classes are expected to override this method to supply their own
     * loading logic.
     *
     * @param xHex      the hexadecimal string representing the x coordinate of the EC point
     * @param yHex      the hexadecimal string representing the y coordinate of the EC point
     * @param curveName the name of the elliptic curve
     * @return the loaded {@link ECPublicKey} instance
     * @throws KeyLoadingException if loading is not supported or fails
     */
    default ECPublicKey loadPublicKey(String xHex, String yHex, String curveName) {
        throw new KeyLoadingException("This key loader does not support loading an EC public key.");
    }

    /**
     * Retrieves the raw content of a PEM formatted key by removing unnecessary headers, footers,
     * and new line characters.
     *
     * <p>
     * This method processes the provided PEM key text to return a cleaned string that contains
     * only the key content. The method strips away the
     * {@code "-----BEGIN (EC )?(PRIVATE|PUBLIC) KEY-----"} and
     * {@code "-----END (EC )?(PRIVATE|PUBLIC) KEY-----"} lines, as well as any new line characters,
     * resulting in a continuous string representation of the key, which can be used for further
     * cryptographic operations.
     *
     * @param pemKeyText the PEM formatted key as a string, which may include headers, footers and
     *                   line breaks
     * @return a string containing the raw key content devoid of  any unnecessary formatting
     * or whitespace
     */
    default String getRawContent(String pemKeyText) {
        // remove all unnecessary parts of the pem key text
        return pemKeyText
                .replaceAll("-----BEGIN ((EC )|(RSA ))?(PRIVATE|PUBLIC) KEY-----", "")
                .replaceAll("-----END ((EC )|(RSA ))?(PRIVATE|PUBLIC) KEY-----", "")
                .replaceAll("\n", "");
    }

}
