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

package com.onixbyte.nums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The {@code ChainedCalcUtil} class provides a convenient way to perform chained high-precision
 * calculations using {@link BigDecimal}. It allows users to perform mathematical operations such
 * as addition, subtraction, multiplication, and division with customisable precision and scale.
 * By using this utility class, developers can achieve accurate results and avoid precision loss
 * in their calculations.
 * <p>
 * <b>Usage:</b>
 * <pre>
 *  // Perform addition: 3 + 4
 *  BigDecimal result1 = ChainedCalcUtil.startWith(3)
 *                                      .add(4)
 *                                      .getValue();
 *
 *  // Perform subtraction: 4 - 2
 *  BigDecimal result2 = ChainedCalcUtil.startWith(4)
 *                                      .subtract(2)
 *                                      .getValue();
 *
 *  // Perform multiplication: 3 * 6
 *  BigDecimal result3 = ChainedCalcUtil.startWith(3)
 *                                      .multiply(6)
 *                                      .getValue();
 *
 *  // Perform division: 6 ÷ 2
 *  BigDecimal result4 = ChainedCalcUtil.startWith(6)
 *                                      .divide(2)
 *                                      .getValue();
 *
 *  // Perform division with specified scale: 13 ÷ 7 with a scale of 2
 *  BigDecimal result5 = ChainedCalcUtil.startWith(13)
 *                                      .divideWithScale(7, 2)
 *                                      .getValue();
 *
 *  // Get int, long, or double results
 *  int intResult = ChainedCalcUtil.startWith(3)
 *                                .add(4)
 *                                .getInteger();
 *
 *  long longResult = ChainedCalcUtil.startWith(4)
 *                                  .subtract(2)
 *                                  .getLong();
 *
 *  double doubleResult = ChainedCalcUtil.startWith(6)
 *                                      .divide(2)
 *                                      .getDouble();
 *
 *  // Get BigDecimal result with specified scale
 *  BigDecimal result6 = ChainedCalcUtil.startWith(13)
 *                                      .divide(7)
 *                                      .getValue(2);
 *  </pre>
 * The above expressions perform various mathematical calculations using the
 * {@code ChainedCalcUtil} class.
 * <p>
 * <b>Note:</b>
 * The {@code ChainedCalcUtil} class internally uses {@link BigDecimal} to handle high-precision
 * calculations. It is important to note that {@link BigDecimal} operations can be memory-intensive
 * and may have performance implications for extremely large numbers or complex calculations.
 *
 * @author sunzsh
 * @version 1.1.0
 * @see BigDecimal
 * @since 1.0.0
 */
public final class ChainedCalcUtil {

    private final static Logger log = LoggerFactory.getLogger(ChainedCalcUtil.class);

    /**
     * Creates a {@code ChainedCalcUtil} instance with the specified initial value.
     *
     * @param value the initial value for the calculation
     */
    private ChainedCalcUtil(Number value) {
        this.value = convertBigDecimal(value, null);
    }

    /**
     * Starts a chained calculation with the specified initial value.
     *
     * @param value the initial value for the calculation
     * @return a {@code ChainedCalcUtil} instance for performing chained calculations
     */
    public static ChainedCalcUtil startWith(Number value) {
        return new ChainedCalcUtil(value);
    }

    /**
     * Adds the specified value to the current value.
     *
     * @param other the value to be added
     * @return a {@code ChainedCalcUtil} instance with the updated value
     */
    public ChainedCalcUtil add(Number other) {
        return operator(BigDecimal::add, other);
    }

    /**
     * Adds the specified value to the current value with a specified scale before the operation.
     *
     * @param other              the value to be added
     * @param beforeOperateScale the scale to be applied before the operation
     * @return a {@code ChainedCalcUtil} instance with the updated value
     */
    public ChainedCalcUtil add(Number other, Integer beforeOperateScale) {
        return operator(BigDecimal::add, other, beforeOperateScale);
    }

    /**
     * Subtracts the specified value from the current value.
     *
     * @param other the value to be subtracted
     * @return a {@code ChainedCalcUtil} instance with the updated value
     */
    public ChainedCalcUtil subtract(Number other) {
        return operator(BigDecimal::subtract, other);
    }

    /**
     * Subtracts the specified value from the current value with a specified scale before
     * the operation.
     *
     * @param other              the value to be subtracted
     * @param beforeOperateScale the scale to be applied before the operation
     * @return a {@code ChainedCalcUtil} instance with the updated value
     */
    public ChainedCalcUtil subtract(Number other, Integer beforeOperateScale) {
        return operator(BigDecimal::subtract, other, beforeOperateScale);
    }

    /**
     * Multiplies the current value by the specified value.
     *
     * @param other the value to be multiplied by
     * @return a {@code ChainedCalcUtil} instance with the updated value
     */
    public ChainedCalcUtil multiply(Number other) {
        return operator(BigDecimal::multiply, other);
    }

    /**
     * Multiplies the current value by the specified value with a specified scale before
     * the operation.
     *
     * @param other              the value to be multiplied by
     * @param beforeOperateScale the scale to be applied before the operation
     * @return a {@code ChainedCalcUtil} instance with the updated value
     */
    public ChainedCalcUtil multiply(Number other, Integer beforeOperateScale) {
        return operator(BigDecimal::multiply, other, beforeOperateScale);
    }

    /**
     * Divides the current value by the specified value.
     *
     * @param other the value to divide by
     * @return a {@code ChainedCalcUtil} instance with the updated value
     */
    public ChainedCalcUtil divide(Number other) {
        return operator(BigDecimal::divide, other);
    }

    /**
     * Divides the current value by the specified value with a specified scale before the operation.
     *
     * @param other              the value to divide by
     * @param beforeOperateScale the scale to be applied before the operation
     * @return a {@code ChainedCalcUtil} instance with the updated value
     */
    public ChainedCalcUtil divide(Number other, Integer beforeOperateScale) {
        return operator(BigDecimal::divide, other, beforeOperateScale);
    }

    /**
     * Divides the current value by the specified value with a specified scale.
     *
     * @param other the value to divide by
     * @param scale the scale for the result
     * @return a {@code ChainedCalcUtil} instance with the updated value
     */
    public ChainedCalcUtil divideWithScale(Number other, Integer scale) {
        return baseOperator(otherValue ->
                this.value.divide(otherValue, scale, RoundingMode.HALF_UP), other, null);
    }

    /**
     * Divides the current value by the specified value with a specified scale and a scale applied
     * before the operation.
     *
     * @param other              the value to divide by
     * @param scale              the scale for the result
     * @param beforeOperateScale the scale to be applied before the operation
     * @return a {@code ChainedCalcUtil} instance with the updated value
     */
    public ChainedCalcUtil divideWithScale(Number other, Integer scale, Integer beforeOperateScale) {
        return baseOperator((otherValue) ->
                        this.value.divide(otherValue, scale, RoundingMode.HALF_UP),
                other, beforeOperateScale);
    }

    /**
     * Returns the current value as a {@link BigDecimal} with the specified scale.
     *
     * @param scale the scale for the result
     * @return the current value as a {@link BigDecimal} with the specified scale
     */
    public BigDecimal getValue(int scale) {
        return value.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * Returns the current value as a {@link BigDecimal}.
     *
     * @return the current value as a {@link BigDecimal}
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Returns the current value as a {@link Double}.
     *
     * @return the current value as a {@link Double}
     */
    public double getDouble() {
        return getValue().doubleValue();
    }

    /**
     * Returns the current value as a {@link Double} with the specified scale.
     *
     * @param scale the scale for the result
     * @return the current value as a {@link Double} with the specified scale
     */
    public double getDouble(int scale) {
        return getValue(scale).doubleValue();
    }

    /**
     * Returns the current value as a {@link Long}.
     *
     * @return the current value as a {@link Long}
     */
    public long getLong() {
        return getValue().longValue();
    }

    /**
     * Returns the current value as an {@link Integer}.
     *
     * @return the current value as an {@link Integer}
     */
    public int getInteger() {
        return getValue().intValue();
    }

    /**
     * Applies the specified operator function to the current value and another value.
     *
     * @param operator   the operator function to apply
     * @param otherValue the value to apply the operator with
     * @return a ChainedCalcUtil instance with the updated value
     */
    private ChainedCalcUtil operator(BiFunction<BigDecimal, BigDecimal, BigDecimal> operator, Object otherValue) {
        return operator(operator, otherValue, 9);
    }

    /**
     * Applies the specified operator function to the current value and another value with a
     * specified scale before the operation.
     *
     * @param operator           the operator function to apply
     * @param other              the value to apply the operator with
     * @param beforeOperateScale the scale to be applied before the operation,
     *                           or null if not applicable
     * @return a ChainedCalcUtil instance with the updated value
     */
    private ChainedCalcUtil operator(BiFunction<BigDecimal, BigDecimal, BigDecimal> operator,
                                     Object other,
                                     Integer beforeOperateScale) {
        return baseOperator((otherValue) ->
                        operator.apply(this.value, otherValue),
                other,
                beforeOperateScale);
    }

    /**
     * Applies the specified operator function to the current value and another
     * value.
     *
     * @param operatorFunction   the operator function to apply
     * @param anotherValue       the value to apply the operator with
     * @param beforeOperateScale the scale to be applied before the operation,
     *                           or null if not applicable
     * @return a ChainedCalcUtil instance with the updated value
     */
    private synchronized ChainedCalcUtil baseOperator(Function<BigDecimal, BigDecimal> operatorFunction,
                                                      Object anotherValue,
                                                      Integer beforeOperateScale) {
        if (Objects.isNull(anotherValue)) {
            return this;
        }
        if (anotherValue instanceof ChainedCalcUtil) {
            this.value = operatorFunction.apply(((ChainedCalcUtil) anotherValue).getValue());
            return this;
        }
        this.value = operatorFunction.apply(convertBigDecimal(anotherValue, beforeOperateScale));
        return this;
    }

    /**
     * Converts the specified value to a {@link BigDecimal}.
     *
     * @param value the value to convert
     * @param scale the scale to apply to the resulting BigDecimal, or null if not applicable
     * @return the converted BigDecimal value
     */
    private BigDecimal convertBigDecimal(Object value, Integer scale) {
        if (Objects.isNull(value)) {
            return BigDecimal.ZERO;
        }
        BigDecimal res;
        if (value instanceof Number num) {
            res = BigDecimal.valueOf(num.doubleValue());
        } else {
            try {
                res = new BigDecimal(value.toString());
            } catch (NumberFormatException ignored) {
                return BigDecimal.ZERO;
            }
        }

        if (Objects.nonNull(scale)) {
            return res.setScale(scale, RoundingMode.HALF_UP);
        }
        return res;
    }

    /**
     * -- GETTER --
     * Returns the current value as a BigDecimal.
     */
    private BigDecimal value;

}
