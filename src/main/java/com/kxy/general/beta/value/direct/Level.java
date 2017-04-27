package com.kxy.general.beta.value.direct;

import com.kxy.general.beta.relation.Relation;
import com.kxy.general.beta.relation.compare.Equal;
import com.kxy.general.beta.relation.compare.Greater;
import com.kxy.general.beta.value.AbstractValue;
import com.kxy.general.beta.value.Value;
import com.kxy.general.beta.value.ValueType;
import com.kxy.general.beta.value.exception.InvalidValue;
import com.kxy.general.beta.value.exception.NoSuchOperate;
import com.kxy.general.beta.value.exception.ParameterCountError;
import com.kxy.general.beta.value.exception.ParameterTypeMissMatch;
import lombok.Getter;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class Level extends AbstractValue {
    private static final long serialVersionUID = 8699202972768078788L;

    /**
     * min value.
     */
    @Getter
    private Long low = 0L;

    /**
     * max value.
     */
    @Getter
    private Long high = 0L;

    /**
     * the actual value.
     */
    @Getter
    private Long value;

    /**
     * set <code>low</code> and <code>high</code>.
     * @param low min value
     * @param high max value
     * @throws InvalidValue not match the rule: <code>low ≤ value ≤ high</code>
     */
    public void setBound(Long low, Long high) throws InvalidValue {
        if (value == null || low == null || high == null || value < low
                || value > high) {
            throw new InvalidValue();
        }
        this.low = low;
        this.high = high;
    }

    /**
     * init value. low and high are undefined
     * @param value the value of the Object
     */
    public Level(Long value) {
        this.value = value;
    }

    /**
     * init by value, low and high. if low and high doesn't match rule:
     * <code>low ≤ value ≤ high</code>, low and high are ignored
     * @param value actual value of the object
     * @param low the possible min value of the object
     * @param high the possible max value of the object
     */
    public Level(Long value, Long low, Long high) {
        this(value);
        try {
            this.setBound(low, high);
        } catch (InvalidValue invalidValue) {
            invalidValue.printStackTrace();
        }
    }

    /**
     * if low and high are not set, just return the real value. else return a
     * number between [0.0, 1.0] to show the position of percent of the value
     * range(normalization).
     * @return the value(normalized) of the object
     */
    public Double numeric() {
        if (low == 0L && high == 0L) {
            return value / 1.0;
        } else {
            return (value - low) * 1.0 / (high - low);
        }
    }

    /**
     * get value type of the object.
     * @return the value type of the object
     */
    @Override
    public ValueType getValueType() {
        return ValueType.LEVEL;
    }

    /**
     * define operators which Level supports.
     * @param relation the relation to compare
     * @param values the values to compare
     * @return compute result of the operate <code>relation</code>
     * @throws NoSuchOperate <code>Level</code> doesn't support this relation
     * @throws ParameterCountError illegal count of <code>values</code>
     * @throws ParameterTypeMissMatch illegal type of <code>values</code>
     */
    @Override
    public Level operate(Relation relation, List<Value> values)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch {
        if (matchOperator(relation, Equal.class)) {
            checkParameter(values, 1, Level.class);

            Level another = (Level) values.get(0);
            if (this.numeric().compareTo(another.numeric()) == 0) {
                return new Level(1L, 0L, 1L);
            } else {
                return new Level(0L, 0L, 1L);
            }
        } else if (matchOperator(relation, Greater.class)) {
            checkParameter(values, 1, Level.class);

            Level another = (Level) values.get(0);
            if (this.numeric().compareTo(another.numeric()) > 0) {
                return new Level(1L, 0L, 1L);
            } else {
                return new Level(0L, 0L, 1L);
            }
        }

        return super.operate(relation, values);
    }
}
