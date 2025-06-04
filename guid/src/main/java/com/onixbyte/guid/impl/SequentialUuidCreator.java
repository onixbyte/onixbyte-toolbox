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

package com.onixbyte.guid.impl;

import com.onixbyte.guid.GuidCreator;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * A {@code SequentialUuidCreator} is responsible for generating UUIDs following the UUID version 7 specification, which
 * combines a timestamp with random bytes to create time-ordered unique identifiers.
 * <p>
 * This implementation utilises a cryptographically strong {@link SecureRandom} instance to produce the random
 * component of the UUID. The first 6 bytes of the UUID encode the current timestamp in milliseconds, ensuring that
 * generated UUIDs are roughly ordered by creation time.
 * <p>
 * The generated UUID adheres strictly to the layout and variant bits of UUID version 7 as defined in the specification.
 * </p>
 */
public class SequentialUuidCreator implements GuidCreator<UUID> {

    private final SecureRandom random;

    /**
     * Constructs a new {@code SequentialUuidCreator} with its own {@link SecureRandom} instance.
     */
    public SequentialUuidCreator() {
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
