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
import com.onixbyte.identitygenerator.exceptions.TimingException;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * The {@code SnowflakeIdentityGenerator} generates unique identifiers using the
 * Snowflake algorithm, which combines a timestamp, worker ID, and data centre ID to create 64-bit
 * long integers. The bit distribution for the generated IDs is as follows:
 * <ul>
 *     <li>1 bit for sign</li>
 *     <li>41 bits for timestamp (in milliseconds)</li>
 *     <li>5 bits for data centre ID</li>
 *     <li>5 bits for worker ID</li>
 *     <li>12 bits for sequence number (per millisecond)</li>
 * </ul>
 * <p>
 * When initialising a {@link SnowflakeIdentityGenerator}, you must provide the worker ID and data
 * centre ID, ensuring they are within the valid range defined by the bit size. The generator
 * maintains an internal sequence number that increments for IDs generated within the
 * same millisecond. If the system clock moves backward, an exception is thrown to prevent
 * generating IDs with repeated timestamps.
 *
 * @author zihluwang
 * @version 3.0.0
 */
public final class SnowflakeIdentityGenerator implements IdentityGenerator<Long> {

    /**
     * Default custom epoch.
     *
     * @value 2015-01-01T00:00:00Z
     */
    private static final long DEFAULT_CUSTOM_EPOCH = 1_420_070_400_000L;

    /**
     * The start epoch timestamp to generate IDs from.
     */
    private final long startEpoch;

    /**
     * The number of bits reserved for the data centre ID.
     */
    private static final long DATA_CENTRE_ID_BITS = 5L;

    /**
     * The number of bits reserved for the worker ID.
     */
    private static final long WORKER_ID_BITS = 5L;

    /**
     * The worker ID assigned to this generator.
     */
    private final long workerId;

    /**
     * The data centre ID assigned to this generator.
     */
    private final long dataCentreId;

    /**
     * The current sequence number.
     */
    private long sequence = 0L;

    /**
     * The timestamp of the last generated ID.
     */
    private long lastTimestamp = -1L;

    /**
     * Constructs a SnowflakeGuidGenerator with the default start epoch and custom worker ID, data
     * centre ID.
     *
     * @param dataCentreId the data centre ID (between 0 and 31)
     * @param workerId     the worker ID (between 0 and 31)
     */
    public SnowflakeIdentityGenerator(long dataCentreId, long workerId) {
        this(dataCentreId, workerId, DEFAULT_CUSTOM_EPOCH);
    }

    /**
     * Constructs a SnowflakeGuidGenerator with a custom epoch, worker ID, and data centre ID.
     *
     * @param dataCentreId the data centre ID (between 0 and 31)
     * @param workerId     the worker ID (between 0 and 31)
     * @param startEpoch   the custom epoch timestamp (in milliseconds) to start generating IDs from
     * @throws IllegalArgumentException if the start epoch is greater than the current timestamp,
     *                                  or if the worker ID or data centre ID is out of range
     */
    public SnowflakeIdentityGenerator(long dataCentreId, long workerId, long startEpoch) {
        if (startEpoch > currentTimestamp()) {
            throw new IllegalArgumentException("Start Epoch can not be greater than current timestamp!");
        }

        var maxWorkerId = ~(-1L << WORKER_ID_BITS);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker Id can't be greater than %d or less than 0",
                maxWorkerId));
        }

        var maxDataCentreId = ~(-1L << DATA_CENTRE_ID_BITS);
        if (dataCentreId > maxDataCentreId || dataCentreId < 0) {
            throw new IllegalArgumentException(String.format("Data Centre Id can't be greater than %d or less than 0",
                maxDataCentreId));
        }

        this.startEpoch = startEpoch;
        this.workerId = workerId;
        this.dataCentreId = dataCentreId;
    }

    /**
     * Generates the next unique ID.
     *
     * @return the generated unique ID
     * @throws TimingException if the system clock moves backwards, indicating an invalid sequence
     *                         of timestamps.
     */
    @Override
    public synchronized Long nextId() {
        var timestamp = currentTimestamp();

        // if the current time is less than the timestamp of the last ID generation, it means that
        // the system clock has been set back and an exception should be thrown
        if (timestamp < lastTimestamp) {
            throw new TimingException("Clock moved backwards. Refusing to generate id for %d milliseconds"
                .formatted(lastTimestamp - timestamp));
        }

        // if generated at the same time, perform intra-millisecond sequences
        var sequenceBits = 12L;
        if (lastTimestamp == timestamp) {
            var sequenceMask = ~(-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;
            // sequence overflow in milliseconds
            if (sequence == 0) {
                // block to the next millisecond, get a new timestamp
                timestamp = awaitToNextMillis(lastTimestamp);
            }
        } else {
            // timestamp change, sequence reset in milliseconds
            sequence = 0L;
        }

        // timestamp of last ID generation
        lastTimestamp = timestamp;

        // shifted and put together by or operations to form a 64-bit ID
        var timestampLeftShift = sequenceBits + WORKER_ID_BITS + DATA_CENTRE_ID_BITS;
        var dataCentreIdShift = sequenceBits + WORKER_ID_BITS;
        return ((timestamp - startEpoch) << timestampLeftShift)
            | (dataCentreId << dataCentreIdShift)
            | (workerId << sequenceBits)
            | sequence;
    }

    /**
     * Blocks until the next millisecond to obtain a new timestamp.
     *
     * @param lastTimestamp the timestamp when the last ID was generated
     * @return the current timestamp
     */
    private long awaitToNextMillis(long lastTimestamp) {
        var timestamp = currentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimestamp();
        }
        return timestamp;
    }

    /**
     * Returns the current timestamp in milliseconds.
     *
     * @return the current timestamp
     */
    private long currentTimestamp() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}

