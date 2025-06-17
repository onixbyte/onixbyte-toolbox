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

package com.onixbyte.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.UUID;

/**
 * The {@link AesUtil} class provides utility methods for encrypting and decrypting data using the
 * AES algorithm. This class supports both byte array and string data, and uses a specified secret
 * key for encryption and decryption.
 * <p>
 * The utility methods in this class are useful for scenarios where data needs to be securely
 * encrypted and decrypted.
 * </p>
 *
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * // Encrypting and decrypting byte array data
 * byte[] secretKey = "43f72073956d4c81".getBytes(StandardCharsets.UTF_8);
 * byte[] data = "Hello World".getBytes(StandardCharsets.UTF_8);
 * byte[] encryptedData = AesUtil.encrypt(data, secretKey);
 * byte[] decryptedData = AesUtil.decrypt(encryptedData, secretKey);
 * System.out.println(new String(decryptedData, StandardCharsets.UTF_8)); // Output: Hello World
 *
 * // Encrypting and decrypting string data
 * String secret = "43f72073956d4c81";
 * String encryptedString = AesUtil.encrypt("Hello World", secret);
 * String decryptedString = AesUtil.decrypt(encryptedString, secret);
 * System.out.println(decryptedString); // Output: Hello World
 *
 * // Generating a random secret key
 * String randomSecret = AesUtil.generateRandomSecret();
 * System.out.println(randomSecret); // Output: A randomly generated 16-character long secret
 * }</pre>
 *
 * @author hubin@baomidou
 * @version 1.1.0
 * @since 1.1.0
 */
public final class AesUtil {

    /**
     * Encrypts the specified data using the AES algorithm with the provided secret key.
     *
     * @param data   the data to be encrypted
     * @param secret the secret key used for encryption
     * @return the encrypted data as a byte array
     * @throws GeneralSecurityException if any cryptographic error occurs during encryption
     */
    public static byte[] encrypt(byte[] data, byte[] secret) throws GeneralSecurityException {
        var secretKeySpec = new SecretKeySpec(new SecretKeySpec(secret, AES).getEncoded(), AES);
        var cipher = Cipher.getInstance(AES_CBC_CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(secret));
        return cipher.doFinal(data);
    }

    /**
     * Decrypts the specified data using the AES algorithm with the provided secret key.
     *
     * @param data   the data to be decrypted
     * @param secret the secret key used for decryption
     * @return the decrypted data as a byte array
     * @throws GeneralSecurityException if any cryptographic error occurs during decryption
     */
    public static byte[] decrypt(byte[] data, byte[] secret) throws GeneralSecurityException {
        var secretKeySpec = new SecretKeySpec(new SecretKeySpec(secret, AES).getEncoded(), AES);
        var cipher = Cipher.getInstance(AES_CBC_CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(secret));
        return cipher.doFinal(data);
    }

    /**
     * Encrypts the specified string data using the AES algorithm with the provided secret key.
     *
     * @param data   the string data to be encrypted
     * @param secret the secret key used for encryption
     * @return the encrypted data encoded in Base64
     * @throws GeneralSecurityException if any cryptographic error occurs during encryption
     */
    public static String encrypt(String data, String secret) throws GeneralSecurityException {
        return Base64.getEncoder().encodeToString(encrypt(data.getBytes(StandardCharsets.UTF_8),
                secret.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Decrypts the specified Base64-encoded string data using the AES algorithm with the provided secret key.
     *
     * @param data   the Base64-encoded string data to be decrypted
     * @param secret the secret key used for decryption
     * @return the decrypted string data
     * @throws GeneralSecurityException if any cryptographic error occurs during decryption
     */
    public static String decrypt(String data, String secret) throws GeneralSecurityException {
        var decrypted = decrypt(Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8)),
                secret.getBytes(StandardCharsets.UTF_8));
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * Generates 16-character random secret.
     *
     * @return the generated secure secret
     */
    public static String generateRandomSecret() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private AesUtil() {
    }

    /**
     * The algorithm AES.
     */
    private static final String AES = "AES";

    /**
     * The algorithm AES/CBC/PKCS5Padding.
     */
    private static final String AES_CBC_CIPHER = "AES/CBC/PKCS5Padding";

}
