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

package com.onixbyte.common.util;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * The {@link BranchUtil} class provides static methods to simplify conditional logic in Java
 * development by leveraging lambda expressions. It offers convenient methods to replace verbose
 * {@code if...else} statements with more concise and expressive functional constructs.
 * <p>
 * Developers can use methods in this utility class to streamline their code, enhance readability,
 * and promote a more functional style of programming when dealing with branching logic and
 * conditional statements.
 * <p>
 * <b>Example:</b>
 * <pre>
 * // If you want to simplify an if (exp1 || exp2), you can use the following code:
 * String r1 = BranchUtil.or(1 == 1, 2 == 1)
 *     .handle(() -> "1 is equal to 1 or 2 is equal to 1.");
 *
 * // If you have an else branch, you can use the following code:
 * String r2 = BranchUtil.or(1 == 1, 2 == 1)
 *     .handle(() -> "1 is equal to 1 or 2 is equal to 1.",
 *             () -> "1 is not equal to 1 and 2 is not equal to 1.");
 *
 * // If you only need to execute code without a return value:
 * BranchUtil.or(1 == 1, 2 == 1)
 *     .handle(() -> {
 *         // do something
 *     }, () -> {
 *         // do something
 *     });
 * // If you only need an if branch, you can remove the second Supplier instance.
 *
 * // To check if all boolean expressions are true, use the 'and' method:
 * BranchUtil.and(1 == 1, 2 == 1)
 *     .handle(() -> {
 *         // do something
 *     }, () -> {
 *         // do something
 *     });
 * </pre>
 * <p>
 * <b>Note:</b>
 * The {@link #and(Boolean...)} and {@link #or(Boolean...)} methods accept any number of boolean
 * expressions.
 *
 * @author zihluwang
 * @version 3.0.0
 * @see java.util.function.Supplier
 * @see java.util.function.BooleanSupplier
 * @see java.lang.Runnable
 */
public final class BranchUtil {

    /**
     * The final result of the boolean expression.
     */
    private final boolean result;

    /**
     * Create a {@code BranchUtil} instance.
     *
     * @param result the result of the boolean expressions.
     */
    private BranchUtil(boolean result) {
        this.result = result;
    }

    /**
     * Creates a {@code BranchUtil} instance to evaluate a logical OR operation on the provided
     * boolean expressions.
     *
     * @param values the boolean expressions to be evaluated
     * @return a {@code BranchUtil} instance representing the result of the logical OR operation
     */
    public static BranchUtil or(Boolean... values) {
        return new BranchUtil(BoolUtil.or(values));
    }

    /**
     * Creates a {@code BranchUtil} instance to evaluate a logical AND operation on the provided
     * boolean expressions.
     *
     * @param values the boolean expressions to be evaluated
     * @return a {@code BranchUtil} instance representing the result of the logical AND operation
     */
    public static BranchUtil and(Boolean... values) {
        return new BranchUtil(BoolUtil.and(values));
    }

    /**
     * Creates a {@code BranchUtil} instance to evaluate a logical OR operation on the provided
     * boolean suppliers.
     *
     * @param valueSuppliers the boolean suppliers to be evaluated
     * @return a {@code BranchUtil} instance representing the result of the
     * logical OR operation
     */
    public static BranchUtil or(BooleanSupplier... valueSuppliers) {
        return new BranchUtil(BoolUtil.or(valueSuppliers));
    }

    /**
     * Creates a {@code BranchUtil} instance to evaluate a logical AND operation on the provided
     * boolean suppliers.
     *
     * @param valueSuppliers the boolean suppliers to be evaluated
     * @return a {@code BranchUtil} instance representing the result of the
     * logical AND operation
     */
    public static BranchUtil and(BooleanSupplier... valueSuppliers) {
        return new BranchUtil(BoolUtil.and(valueSuppliers));
    }

    /**
     * Handles the result of the boolean expressions by executing the appropriate handler based
     * on the result.
     * <p>
     * If the result is {@code true}, the {@code trueSupplier} is executed. If the result is
     * {@code false} and an {@code falseSupplier} is provided, it is executed.
     * <p>
     * Returns the result of the executed supplier.
     *
     * @param <T>           the type of the result to be handled by the methods
     * @param trueSupplier  the supplier to be executed if the result is {@code true}
     * @param falseSupplier the supplier to be executed if the result is {@code false} (optional)
     * @return the result of the executed supplier, or {@code null} if no {@code falseSupplier} is
     * provided and the result of the evaluation is {@code false}
     */
    public <T> T thenSupply(Supplier<T> trueSupplier, Supplier<T> falseSupplier) {
        if (this.result && Objects.nonNull(trueSupplier)) {
            return trueSupplier.get();
        }

        if (Objects.isNull(falseSupplier)) {
            return null;
        }

        return falseSupplier.get();
    }

    /**
     * Handles the result of the boolean expressions by executing the provided handler if the
     * result is {@code true}.
     * <p>
     * Returns the result of the executed handler.
     *
     * @param <T>          the type of the result to be handled by the methods
     * @param trueSupplier the supplier to be executed if the result is {@code true}
     * @return the result of the executed handler, or {@code null} if result of evaluation
     * is {@code false}
     */
    public <T> T thenSupply(Supplier<T> trueSupplier) {
        return thenSupply(trueSupplier, null);
    }

    /**
     * Handles the result of the boolean expressions by executing the appropriate handler based
     * on the result.
     * <p>
     * If the result is {@code true}, the {@code ifHandler} is executed. If the result is
     * {@code false} and an {@code elseHandler} is provided, it is executed.
     *
     * @param trueHandler  the handler to be executed if the result is {@code true}
     * @param falseHandler the handler to be executed if the result is {@code false} (optional)
     */
    public void then(Runnable trueHandler, Runnable falseHandler) {
        if (this.result && Objects.nonNull(trueHandler)) {
            trueHandler.run();
            return;
        }

        if (Objects.isNull(falseHandler)) {
            return;
        }

        falseHandler.run();
    }

    /**
     * Handles the result of the boolean expressions by executing the provided handler if the
     * result is {@code true}.
     *
     * @param trueHandler the handler to be executed if the result is {@code true}
     */
    public void then(Runnable trueHandler) {
        then(trueHandler, null);
    }
}
