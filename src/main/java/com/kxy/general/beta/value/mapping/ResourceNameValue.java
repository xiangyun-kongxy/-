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
 * Created by xiangyunkong on 21/04/2017.
 */
public class ResourceNameValue extends AbstractValue {
    private static final long serialVersionUID = -7104430825460222514L;

    /**
     * get the <code>ValueType</code> of <code>ResourceNameValue</code>.
     * @return <code>ValueType.RESOURCE_NAME</code>
     */
    @Override
    public ValueType getValueType() {
        return ValueType.RESOURCE_NAME;
    }

    /**
     * get the real value: <code>Resource</code> type name.
     * @param object for mapping value, it is the original value
     * @return type name of the <code>Resource</code>
     * @throws ParameterTypeMissMatch <code>object</code> is not an instance of
     *          <code>Resource</code>
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
     * relation <code>Equal</code> is supported by
     * <code>ResourceNameValue</code>. two <code>values</code> both with type of
     * <code>ResourceNameValue</code> are <code>Equal</code>
     * @param relation the relation to compare
     * @param values the values to compare
     * @return if relation come into existence
     * @throws NoSuchOperate <code>relation</code> is not supported
     * @throws ParameterCountError illegal count <code>values</code>
     * @throws ParameterTypeMissMatch illegal type <code>values</code>
     */
    @Override
    public Level operate(Relation relation, List<Value> values)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch {
        if (matchOperator(relation, Equal.class)) {
            checkParameter(values, 1, ResourceNameValue.class);
            return new Level(1L, 0L, 1L);
        }
        return super.operate(relation, values);
    }
}
