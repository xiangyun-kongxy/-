package com.kxy.general.ß.value.direct;

import com.kxy.general.ß.relation.Relation;
import com.kxy.general.ß.value.AbstractValue;
import com.kxy.general.ß.value.Value;
import com.kxy.general.ß.value.ValueType;
import com.kxy.general.ß.value.exception.NoSuchOperator;
import com.kxy.general.ß.value.exception.ParameterCountError;
import com.kxy.general.ß.value.exception.ParameterTypeMissMatch;
import lombok.Getter;

import java.util.List;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class StringValue extends AbstractValue {
    private static final long serialVersionUID = -7260143953180622992L;

    private final static String TYPENAME = "string";

    @Getter
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.valueOf(TYPENAME);
    }

    @Override
    public Level operator(Relation relation, List<Value> values)
            throws NoSuchOperator, ParameterCountError, ParameterTypeMissMatch {
        // TODO: add supported operations
        return super.operator(relation, values);
    }
}
