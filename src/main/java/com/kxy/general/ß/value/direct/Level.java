package com.kxy.general.ß.value.direct;

import com.kxy.general.ß.relation.Relation;
import com.kxy.general.ß.relation.compare.Equal;
import com.kxy.general.ß.relation.compare.Greater;
import com.kxy.general.ß.value.AbstractValue;
import com.kxy.general.ß.value.Value;
import com.kxy.general.ß.value.ValueType;
import com.kxy.general.ß.value.exception.InvalidValue;
import com.kxy.general.ß.value.exception.NoSuchOperator;
import com.kxy.general.ß.value.exception.ParameterCountError;
import com.kxy.general.ß.value.exception.ParameterTypeMissMatch;
import lombok.Getter;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class Level extends AbstractValue {
    private static final long serialVersionUID = 8699202972768078788L;

    @Getter
    private Long low = -0L;
    @Getter
    private Long high = +0L;

    @Getter
    private Long value;

    public void setBound(Long low, Long high) throws InvalidValue {
        if(value == null || low == null || high == null ||
                value < low || value > high) {
            throw new InvalidValue();
        }
        this.low = low;
        this.high = high;
    }

    public Level(Long value) {
        this.value = value;
    }

    public Level(Long value, Long low, Long high) {
        this(value);
        if(low != null && high != null && value >= low && value <= high) {
            this.low = low;
            this.high = high;
        }
    }

    public Double numeric() {
        if(low == -0L && high == +0L) {
            return this.value / 1.0;
        } else {
            return (this.value - low) * 1.0 / (high - low);
        }
    }

    @Override
    public ValueType getValueType() {
        return null;
    }

    @Override
    public Level operator(Relation relation, List<Value> values)
            throws NoSuchOperator, ParameterCountError, ParameterTypeMissMatch {
        if(matchOperator(relation, Equal.class)) {
            checkParameter(values, 1, Level.class);

            Level another = (Level)values.get(0);
            if (this.numeric().compareTo(another.numeric()) == 0) {
                return new Level(1L, 0L, 1L);
            } else {
                return new Level(0L, 0L, 1L);
            }
        } else if(matchOperator(relation, Greater.class)) {
            checkParameter(values, 1, Level.class);

            Level another = (Level)values.get(0);
            if (this.numeric().compareTo(another.numeric()) > 0) {
                return new Level(1L, 0L, 1L);
            } else {
                return new Level(0L, 0L, 1L);
            }
        }

        return super.operator(relation, values);
    }
}
