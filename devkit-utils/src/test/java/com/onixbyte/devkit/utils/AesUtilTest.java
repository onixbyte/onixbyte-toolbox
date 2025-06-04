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

package com.onixbyte.devkit.utils;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;

class AesUtilTest {

    @Test
    void testEncryptAndDecryptByte() throws GeneralSecurityException {
        byte[] secretKey = "43f72073956d4c81".getBytes(StandardCharsets.UTF_8);
        byte[] originalData = "Hello World".getBytes(StandardCharsets.UTF_8);

        byte[] encryptedData = AesUtil.encrypt(originalData, secretKey);
        assertNotNull(encryptedData);

        byte[] decryptedData = AesUtil.decrypt(encryptedData, secretKey);
        assertNotNull(decryptedData);

        assertArrayEquals(originalData, decryptedData);
    }

    @Test
    void testEncryptAndDecryptString() throws GeneralSecurityException {
        var secret = "43f72073956d4c81";
        var originalData = "Hello World";

        var encryptedData = AesUtil.encrypt(originalData, secret);
        assertNotNull(encryptedData);
        assertNotEquals(originalData, encryptedData);

        var decryptedData = AesUtil.decrypt(encryptedData, secret);
        assertNotNull(decryptedData);
        assertEquals(originalData, decryptedData);
    }

    @Test
    void testEncryptWithWrongKeyFails() throws GeneralSecurityException {
        var secret = "43f72073956d4c81";
        var wrongSecret = "0000000000000000";
        var originalData = "Hello World";

        var encryptedData = AesUtil.encrypt(originalData.getBytes(StandardCharsets.UTF_8),
                secret.getBytes(StandardCharsets.UTF_8));
        assertNotNull(encryptedData);

        // When decrypting with the wrong key, a BadPaddingException or IllegalBlockSizeException is expected to be thrown
        assertThrows(GeneralSecurityException.class, () -> {
            AesUtil.decrypt(encryptedData, wrongSecret.getBytes(StandardCharsets.UTF_8));
        });
    }

    @Test
    void testGenerateRandomSecret() {
        var randomSecret = AesUtil.generateRandomSecret();
        assertNotNull(randomSecret);
        assertEquals(16, randomSecret.length());
    }
}
