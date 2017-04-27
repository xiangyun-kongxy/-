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
 * Created by xiangyunkong on 20/04/2017.
 */
public class StringValue extends AbstractValue {
    private static final long serialVersionUID = -7260143953180622992L;

    /**
     * the actual string.
     */
    @Getter
    private String value;

    /**
     * init <code>StringValue</code> by a string.
     * @param value actual string
     */
    public StringValue(String value) {
        this.value = value;
    }

    /**
     * @see Value#getValueType()
     */
    @Override
    public ValueType getValueType() {
        return ValueType.STRING;
    }

    /**
     * @see Value#operate(Relation, List)
     * @param relation the relation to compare. supported relations:
     *                 Equal, Greater
     */
    @Override
    public Level operate(Relation relation, List<Value> values)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch {
        if (matchOperator(relation, Equal.class)) {
            checkParameter(values, 1, StringValue.class);

            StringValue another = (StringValue) values.get(0);
            if (this.value.equals(another.value)) {
                return new Level(1L, 0L, 1L);
            } else {
                return new Level(0L, 0L, 1L);
            }
        } else if (matchOperator(relation, Greater.class)) {
            checkParameter(values, 1, StringValue.class);

            StringValue another = (StringValue) values.get(0);
            if (this.value.compareTo(another.value) > 0) {
                return new Level(1L, 0L, 1L);
            } else {
                return new Level(0L, 0L, 1L);
            }
        }

        return super.operate(relation, values);
    }
}
