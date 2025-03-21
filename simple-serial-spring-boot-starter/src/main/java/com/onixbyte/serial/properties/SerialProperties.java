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

package com.onixbyte.serial.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SerialProperties can modify the start value of a serial.
 *
 * @author siujamo
 */
@ConfigurationProperties(prefix = "onixbyte.serial")
public class SerialProperties {

    /**
     * The start of the serial, default to 0.
     */
    private Long startSerial = 0L;

    /**
     * Get the start of the serial.
     *
     * @return start of the serial
     */
    public Long getStartSerial() {
        return startSerial;
    }

    /**
     * Set the start of the serial.
     *
     * @param startSerial start of the serial
     */
    public void setStartSerial(Long startSerial) {
        this.startSerial = startSerial;
    }

    /**
     * Default constructor.
     */
    public SerialProperties() {
    }

}
