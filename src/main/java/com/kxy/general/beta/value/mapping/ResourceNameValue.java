package com.kxy.general.beta.value.mapping;

import com.kxy.general.beta.relation.Relation;
import com.kxy.general.beta.relation.compare.Equal;
import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.value.AbstractValue;
import com.kxy.general.beta.value.Value;
import com.kxy.general.beta.value.ValueType;
import com.kxy.general.beta.value.direct.Level;
import com.kxy.general.beta.value.direct.StringValue;
import com.kxy.general.beta.value.exception.NoSuchOperate;
import com.kxy.general.beta.value.exception.ParameterCountError;
import com.kxy.general.beta.value.exception.ParameterTypeMissMatch;

import java.util.List;

/**
 *
 * Created by xiangyunkong on 21/04/2017.
 */
public class ResourceNameValue extends AbstractValue {
    private static final long serialVersionUID = -7104430825460222514L;

    /**
     * get the ValueType of ResourceNameValue.
     * @return ValueType.RESOURCE_NAME
     */
    @Override
    public ValueType getValueType() {
        return ValueType.RESOURCE_NAME;
    }

    /**
     * get the real value: Resource type name.
     * @param object for mapping value, it is the original value
     * @return type name of the Resource
     * @throws ParameterTypeMissMatch object is not an instance of Resource
     */
    @Override
    public Value get(Object object) throws ParameterTypeMissMatch {
        if (object instanceof Resource) {
            Resource resource = (Resource) object;
            if (resource.getResourceType() != null) {
                return new StringValue(resource.getResourceType().getName());
            } else {
                return null;
            }
        }
        throw new ParameterTypeMissMatch();
    }

    /**
     * relation Equal is supported by ResourceNameValue. two values both with
     * type of ResourceNameValue are Equal
     * @param relation the relation to compare
     * @param values the values to compare
     * @return if relation come into existence
     * @throws NoSuchOperate relation is not supported
     * @throws ParameterCountError illegal count values
     * @throws ParameterTypeMissMatch illegal type values
     */
    @Override
    public Level operate(Relation relation, List<Value> values)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch {
        if (matchOperator(relation, Equal.class)) {
            checkParameter(values, 1, ResourceNameValue.class);
            return new Level(1L);
        }
        return super.operate(relation, values);
    }
}
