package com.kxy.general.beta.value.direct;

import com.kxy.general.beta.relation.Relation;
import com.kxy.general.beta.relation.compare.Equal;
import com.kxy.general.beta.relation.compare.Greater;
import com.kxy.general.beta.value.AbstractValue;
import com.kxy.general.beta.value.Value;
import com.kxy.general.beta.value.ValueType;
import com.kxy.general.beta.value.exception.NoSuchOperate;
import com.kxy.general.beta.value.exception.ParameterCountError;
import com.kxy.general.beta.value.exception.ParameterTypeMissMatch;
import lombok.Getter;

import java.util.List;

/**
 *
 * Created by xiangyunkong on 14/04/2017.
 */
public class Level extends AbstractValue {
    private static final long serialVersionUID = 8699202972768078788L;

    /**
     * min value.
     */
    @Getter
    private static final Long LOW = 0L;

    /**
     * max value.
     */
    @Getter
    private static final Long HIGH = 100L;

    /**
     * the actual value.
     */
    @Getter
    private Long value;

    /**
     * init value. low and high are undefined
     * @param value the value of the Object
     */
    public Level(Long value) {
        if (value < LOW || value > HIGH) {
            this.value = LOW;
        } else {
            this.value = value;
        }
    }

    /**
     * if low and high are not set, just return the real value. else return a
     * number between [0.0, 1.0] to show the position of percent of the value
     * range(normalization).
     * @return the value(normalized) of the object
     */
    public Long numeric() {
        return value;
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
     * @return compute result of the operate relation
     * @throws NoSuchOperate Level doesn't support this relation
     * @throws ParameterCountError illegal count of values
     * @throws ParameterTypeMissMatch illegal type of values
     */
    @Override
    public Level operate(Relation relation, List<Value> values)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch {
        if (matchOperator(relation, Equal.class)) {
            checkParameter(values, 1, Level.class);

            Level another = (Level) values.get(0);
            if (this.numeric().compareTo(another.numeric()) == 0) {
                return new Level(1L);
            } else {
                return new Level(0L);
            }
        } else if (matchOperator(relation, Greater.class)) {
            checkParameter(values, 1, Level.class);

            Level another = (Level) values.get(0);
            if (this.numeric().compareTo(another.numeric()) > 0) {
                return new Level(1L);
            } else {
                return new Level(0L);
            }
        }

        return super.operate(relation, values);
    }
}
