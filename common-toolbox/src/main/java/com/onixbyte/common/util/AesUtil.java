/*
 * Copyright (c) 2024-2026 OnixByte
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
 * @author hubin
 * @version 3.0.0
 */
public final class AesUtil {

    /**
     * The algorithm AES.
     */
    private static final String AES = "AES";

    /**
     * The algorithm AES/CBC/PKCS5Padding.
     */
    private static final String AES_CBC_CIPHER = "AES/CBC/PKCS5Padding";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private AesUtil() {
    }

    /**
     * Encrypts the specified data using the AES algorithm with the provided secret key.
     *
     * @param data    the data to be encrypted
     * @param secret  the secret key used for encryption
     * @param ivParam the iv param
     * @return the encrypted data as a byte array
     * @throws GeneralSecurityException if any cryptographic error occurs during encryption
     */
    public static byte[] encrypt(byte[] data, byte[] secret, byte[] ivParam) throws GeneralSecurityException {
        var secretKeySpec = new SecretKeySpec(new SecretKeySpec(secret, AES).getEncoded(), AES);
        var cipher = Cipher.getInstance(AES_CBC_CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(ivParam));
        return cipher.doFinal(data);
    }

    /**
     * Encrypts the specified data using the AES algorithm with the provided secret key.
     *
     * @param data   the data to be encrypted
     * @param secret the secret key used for encryption
     * @return the encrypted data as a byte array
     * @throws GeneralSecurityException if any cryptographic error occurs during encryption
     */
    public static byte[] encrypt(byte[] data, byte[] secret) throws GeneralSecurityException {
        return encrypt(data, secret, secret);
    }

    /**
     * Decrypts the specified data using the AES algorithm with the provided secret key.
     *
     * @param data    the data to be decrypted
     * @param secret  the secret key used for decryption
     * @param ivParam the iv param
     * @return the decrypted data as a byte array
     * @throws GeneralSecurityException if any cryptographic error occurs during decryption
     */
    public static byte[] decrypt(byte[] data, byte[] secret, byte[] ivParam) throws GeneralSecurityException {
        var secretKeySpec = new SecretKeySpec(new SecretKeySpec(secret, AES).getEncoded(), AES);
        var cipher = Cipher.getInstance(AES_CBC_CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivParam));
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
        return decrypt(data, secret, secret);
    }

    /**
     * Encrypts the specified string data using the AES algorithm with the provided secret key.
     *
     * @param data    the string data to be encrypted
     * @param secret  the secret key used for encryption
     * @param ivParam the iv param
     * @return the encrypted data encoded in Base64
     * @throws GeneralSecurityException if any cryptographic error occurs during encryption
     */
    public static String encrypt(String data, String secret, String ivParam) throws GeneralSecurityException {
        return Base64.getEncoder().encodeToString(encrypt(
            data.getBytes(StandardCharsets.UTF_8),
            secret.getBytes(StandardCharsets.UTF_8),
            ivParam.getBytes(StandardCharsets.UTF_8)
        ));
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
        return encrypt(data, secret, secret);
    }

    /**
     * Decrypts the specified Base64-encoded string data using the AES algorithm with the provided secret key.
     *
     * @param data    the Base64-encoded string data to be decrypted
     * @param secret  the secret key used for decryption
     * @param ivParam the initialization vector parameter used for AES decryption
     * @return the decrypted string data
     * @throws GeneralSecurityException if any cryptographic error occurs during decryption
     */
    public static String decrypt(String data, String secret, String ivParam) throws GeneralSecurityException {
        var decrypted = decrypt(
            Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8)),
            secret.getBytes(StandardCharsets.UTF_8),
            ivParam.getBytes(StandardCharsets.UTF_8)
        );
        return new String(decrypted, StandardCharsets.UTF_8);
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
        return decrypt(data, secret, secret);
    }

    /**
     * Generates 16-character random secret.
     *
     * @return the generated secure secret
     */
    public static String generateRandomSecret() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }
}
