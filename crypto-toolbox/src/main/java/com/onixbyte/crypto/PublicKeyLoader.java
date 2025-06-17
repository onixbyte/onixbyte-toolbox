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

package com.onixbyte.crypto;

import com.onixbyte.crypto.exception.KeyLoadingException;

import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 *
 * @author siujamo
 * @version 3.0.0
 */
public interface PublicKeyLoader {

    /**
     * Load public key from pem-formatted key text.
     *
     * @param pemKeyText pem-formatted key text
     * @return loaded private key
     */
    PublicKey loadPublicKey(String pemKeyText);

    /**
     * Loads an EC public key using the provided x and y coordinates together with the curve name.
     * <p>
     * This default implementation throws a {@link KeyLoadingException} to signify that this key
     * loader does not support loading an EC public key. Implementing classes are expected to
     * override this method to supply their own loading logic.
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
     * Loads an RSA public key using the provided modulus and exponent.
     * <p>
     * This default implementation throws a {@link KeyLoadingException} to signify that this key
     * loader does not support loading an RSA public key. Implementing classes are expected to
     * override this method to supply their own loading logic.
     *
     * @param modulus  the modulus value of the RSA public key, usually represented in hexadecimal
     *                 or Base64 string format
     * @param exponent the public exponent value of the RSA public key, usually represented in
     *                 hexadecimal or Base64 string format
     * @return the loaded {@link RSAPublicKey} instance
     * @throws KeyLoadingException if loading is not supported or fails
     */
    default RSAPublicKey loadPublicKey(String modulus, String exponent) {
        throw new KeyLoadingException("This key loader does not support loading an RSA public key.");
    }
}
