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

package com.onixbyte.identitygenerator.impl;

import com.onixbyte.identitygenerator.IdentityGenerator;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * A {@code SequentialUuidGenerator} is responsible for generating UUIDs following the
 * UUID version 7 specification, which combines a timestamp with random bytes to create time-ordered
 * unique identifiers.
 * <p>
 * This implementation utilises a cryptographically strong {@link SecureRandom} instance to produce
 * the random component of the UUID. The first 6 bytes of the UUID encode the current timestamp in
 * milliseconds, ensuring that generated UUIDs are roughly ordered by creation time.
 * <p>
 * The generated UUID adheres strictly to the layout and variant bits of UUID version 7 as defined
 * in the specification.
 *
 * @author zihluwang
 * @author siujamo
 * @version 3.0.0
 */
public class SequentialUuidGenerator implements IdentityGenerator<UUID> {

    private final SecureRandom random;

    /**
     * Constructs a new {@code SequentialUuidGenerator} with its own {@link SecureRandom} instance.
     */
    public SequentialUuidGenerator() {
        this.random = new SecureRandom();
    }

    /**
     * Generates and returns the next UUID version 7 identifier.
     *
     * @return a {@link UUID} instance representing a unique, time-ordered identifier
     */
    @Override
    public UUID nextId() {
        var value = randomBytes();
        var buf = ByteBuffer.wrap(value);
        var high = buf.getLong();
        var low = buf.getLong();
        return new UUID(high, low);
    }

    /**
     * Produces a byte array representation of a UUID version 7,
     * combining the current timestamp with random bytes.
     *
     * @return a 16-byte array conforming to UUIDv7 layout and variant bits
     */
    private byte[] randomBytes() {
        var value = new byte[16];
        random.nextBytes(value);

        var timestamp = ByteBuffer.allocate(Long.BYTES);
        timestamp.putLong(System.currentTimeMillis());

        System.arraycopy(timestamp.array(), 2, value, 0, 6);

        // Set version to 7 (UUIDv7)
        value[6] = (byte) ((value[6] & 0x0F) | 0x70);

        // Set variant bits as per RFC 4122
        value[8] = (byte) ((value[8] & 0x3F) | 0x80);

        return value;
    }
}
