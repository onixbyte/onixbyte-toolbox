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

package com.onixbyte.jwt.autoconfiguration.conditions;

import com.onixbyte.identitygenerator.IdentityGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

/**
 * The conditions to create bean {@code jtiCreator}.
 *
 * @author zihluwang
 * @version 3.0.0
 * @since 1.0.0
 */
public class GuidCreatorCondition implements Condition {

    /**
     * Default constructor.
     */
    public GuidCreatorCondition() {
    }

    /**
     * The condition to create bean {@code jtiCreator}.
     * <p>
     * If Spring does not have a bean of type {@link IdentityGenerator} named {@code jtiCreator} in the
     * application context, then create {@code jtiCreator}.
     *
     * @param context  the spring application context
     * @param metadata the metadata of the {@link org.springframework.core.type.AnnotationMetadata
     *                 class} or {@link org.springframework.core.type.MethodMetadata method}
     *                 being checked
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        final var beanFactory = Objects.requireNonNull(context.getBeanFactory());
        var isContainJtiCreator = beanFactory.containsBean("jtiCreator");
        if (isContainJtiCreator) {
            return !(beanFactory.getBean("jtiCreator") instanceof IdentityGenerator<?>);
        }
        return true;
    }
}
