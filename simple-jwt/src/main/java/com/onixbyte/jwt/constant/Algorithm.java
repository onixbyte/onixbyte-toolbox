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

package com.onixbyte.jwt.constant;

/**
 *
 */
public enum Algorithm {
    HS256(1, 256, "HmacSHA256"),
    HS384(1, 384, "HmacSHA384"),
    HS512(1, 512, "HmacSHA512"),
    RS256(2, 256, "SHA256withRSA"),
    RS384(2, 384, "SHA384withRSA"),
    RS512(2, 512, "SHA512withRSA"),
    ES256(3, 256, "SHA256withECDSA"),
    ES384(3, 384, "SHA384withECDSA"),
    ES512(3, 512, "SHA512withECDSA");

    /**
     *
     */
    private static final int HS_FLAG = 1; // 001

    /**
     *
     */
    private static final int RS_FLAG = 2; // 010

    /**
     *
     */
    private static final int ES_FLAG = 3; // 011

    /**
     *
     */
    private final int typeFlag;
    private final int shaLength;
    private final String algorithm;

    /**
     *
     * @param typeFlag
     * @param shaLength
     * @param algorithm
     */
    Algorithm(int typeFlag, int shaLength, String algorithm) {
        this.typeFlag = typeFlag;
        this.shaLength = shaLength;
        this.algorithm = algorithm;
    }

    /**
     *
     * @return
     */
    public boolean isHmac() {
        return (this.typeFlag & HS_FLAG) != 0;
    }

    /**
     *
     * @return
     */
    public boolean isRsa() {
        return (this.typeFlag & RS_FLAG) != 0;
    }

    /**
     *
     * @return
     */
    public boolean isEcdsa() {
        return (this.typeFlag & ES_FLAG) != 0;
    }

    /**
     *
     * @return
     */
    public int getShaLength() {
        return shaLength;
    }

    /**
     *
     * @return
     */
    public int getTypeFlag() {
        return typeFlag;
    }

    /**
     *
     * @return
     */
    public String getAlgorithm() {
        return algorithm;
    }
}
