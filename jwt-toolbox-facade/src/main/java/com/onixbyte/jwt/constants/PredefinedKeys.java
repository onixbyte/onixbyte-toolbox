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

package com.onixbyte.jwt.constants;

import java.util.List;

/**
 * The {@code PredefinedKeys} class contains constants for standard JSON Web Token (JWT) claims. These constants
 * represent the names of the standard claims that can be included in a JWT payload. Developers can use these constants
 * when working with JWTs to ensure consistent naming of the claims.
 * <p>
 * The class provides the following standard JWT claim constants:
 * <ul>
 *   <li>{@link #ISSUER}: Represents the "iss" (Issuer) claim.</li>
 *   <li>{@link #SUBJECT}: Represents the "sub" (Subject) claim.</li>
 *   <li>{@link #AUDIENCE}: Represents the "aud" (Audience) claim.</li>
 *   <li>{@link #EXPIRATION_TIME}: Represents the "exp" (Expiration Time) claim.</li>
 *   <li>{@link #NOT_BEFORE}: Represents the "nbf" (Not Before) claim.</li>
 *   <li>{@link #ISSUED_AT}: Represents the "iat" (Issued At) claim.</li>
 *   <li>{@link #JWT_ID}: Represents the "jti" (JWT ID) claim.</li>
 * </ul>
 * <p>
 * The class also contains a list of all the standard claim constants, accessible via the {@link
 * #KEYS} field. This list can be useful for iterating through all the standard claims or checking
 * for the presence of specific claims.
 * <p>
 * Note: This class is final and cannot be instantiated. It only serves as a utility class to hold
 * the standard JWT claim constants.
 *
 * @author zihluwang
 * @version 3.0.0
 * @since 1.0.0
 */
public final class PredefinedKeys {

    /**
     * Constant representing the "iss" (Issuer) claim in a JWT payload.
     */
    public static final String ISSUER = "iss";

    /**
     * Constant representing the "sub" (Subject) claim in a JWT payload.
     */
    public static final String SUBJECT = "sub";

    /**
     * Constant representing the "aud" (Audience) claim in a JWT payload.
     */
    public static final String AUDIENCE = "aud";

    /**
     * Constant representing the "exp" (Expiration Time) claim in a JWT payload.
     */
    public static final String EXPIRATION_TIME = "exp";

    /**
     * Constant representing the "nbf" (Not Before) claim in a JWT payload.
     */
    public static final String NOT_BEFORE = "nbf";

    /**
     * Constant representing the "iat" (Issued At) claim in a JWT payload.
     */
    public static final String ISSUED_AT = "iat";

    /**
     * Constant representing the "jti" (JWT ID) claim in a JWT payload.
     */
    public static final String JWT_ID = "jti";

    /**
     * List containing all the standard JWT claim constants.
     */
    public static final List<String> KEYS = List.of(
        ISSUER,
        SUBJECT,
        AUDIENCE,
        EXPIRATION_TIME,
        NOT_BEFORE,
        ISSUED_AT,
        JWT_ID
    );

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private PredefinedKeys() {
    }
}

