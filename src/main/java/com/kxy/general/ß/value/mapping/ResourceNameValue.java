package com.kxy.general.ß.value.mapping;

import com.kxy.general.ß.relation.Relation;
import com.kxy.general.ß.relation.compare.Equal;
import com.kxy.general.ß.resource.Resource;
import com.kxy.general.ß.value.AbstractValue;
import com.kxy.general.ß.value.Value;
import com.kxy.general.ß.value.ValueType;
import com.kxy.general.ß.value.direct.Level;
import com.kxy.general.ß.value.direct.StringValue;
import com.kxy.general.ß.value.exception.NoSuchAddress;
import com.kxy.general.ß.value.exception.NoSuchOperator;
import com.kxy.general.ß.value.exception.ParameterCountError;
import com.kxy.general.ß.value.exception.ParameterTypeMissMatch;

import java.util.List;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class ResourceNameValue extends AbstractValue {
    private static final long serialVersionUID = -7104430825460222514L;

    @Override
    public ValueType getValueType() {
        return ValueType.RESOURCE_NAME;
    }

    @Override
    public Value get(Object object) throws ParameterTypeMissMatch, NoSuchAddress {
        if(object instanceof Resource) {
            Resource resource = (Resource)object;
            if(resource.getResourceType() != null) {
                return new StringValue(resource.getResourceType().getName());
            } else {
                return null;
            }
        }
        throw new ParameterTypeMissMatch();
    }

    @Override
    public Level operator(Relation relation, List<Value> values)
            throws NoSuchOperator, ParameterCountError, ParameterTypeMissMatch {
        if(matchOperator(relation, Equal.class)) {
            checkParameter(values, 1, ResourceNameValue.class);
            return new Level(1L, 0L, 1L);
        }
        return super.operator(relation, values);
    }
}
