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
 * @author Zihlu Wang
 * @version 1.1.0
 * @since 1.0.0
 */
public class GuidCreatorCondition implements Condition {

    private final static Logger log = LoggerFactory.getLogger(GuidCreatorCondition.class);

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
