package com.kxy.general.ß.value;

import com.kxy.general.ß.relation.Relation;
import com.kxy.general.ß.value.direct.Level;
import com.kxy.general.ß.value.exception.NoSuchAddress;
import com.kxy.general.ß.value.exception.NoSuchOperator;
import com.kxy.general.ß.value.exception.ParameterCountError;
import com.kxy.general.ß.value.exception.ParameterTypeMissMatch;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public abstract class AbstractValue implements Value {
    private static final long serialVersionUID = 991253607836803448L;

    @Override
    public Value get(Object object) throws ParameterTypeMissMatch, NoSuchAddress {
        return this;
    }

    @Override
    public Level operator(Relation relation, Value... value)
            throws NoSuchOperator, ParameterCountError, ParameterTypeMissMatch {
        return operator(relation, Arrays.asList(value));
    }

    @Override
    public Level operator(Relation relation, List<Value> values)
            throws NoSuchOperator, ParameterCountError, ParameterTypeMissMatch {
        throw new NoSuchOperator();
    }

    protected Boolean matchOperator(Relation relation, Class type) {
        return type.isInstance(relation);
    }

    protected void checkParameter(List<Value> params, int count, Class type)
        throws ParameterTypeMissMatch, ParameterCountError {
        if(params == null || params.size() != count) {
            throw new ParameterCountError();
        }
        for(Value param:params) {
            if(!type.isInstance(param)) {
                throw new ParameterTypeMissMatch();
            }
        }
    }
}
