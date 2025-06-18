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

package com.onixbyte.crypto;

import com.onixbyte.crypto.exception.KeyLoadingException;

import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * The {@code PublicKeyLoader} provides utility methods for loading public keys from PEM-formatted
 * key text.
 *
 * @author zihluwang
 * @author siujamo
 * @version 3.0.0
 */
public interface PublicKeyLoader {

    /**
     * Load public key from PEM-formatted key text.
     *
     * @param pemKeyText PEM-formatted key text
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
