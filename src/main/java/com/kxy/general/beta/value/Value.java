package com.kxy.general.beta.value;

import com.kxy.general.beta.relation.Relation;
import com.kxy.general.beta.value.direct.Level;
import com.kxy.general.beta.value.exception.NoSuchAddress;
import com.kxy.general.beta.value.exception.NoSuchOperate;
import com.kxy.general.beta.value.exception.ParameterCountError;
import com.kxy.general.beta.value.exception.ParameterTypeMissMatch;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Value extends Serializable {
    /**
     * get the type of the Value.
     * @return ValueType of the object
     */
    ValueType getValueType();

    /**
     * get the real value of this.
     * 1. this is a mapping Value
     *      the computed value should be returned
     * 2. this is not a mapping Value
     *      return this itself
     * @param object if this is a mapping Value, object is the real object
     *               context, this is the address of the real value
     * @return the real value
     * @throws ParameterTypeMissMatch Illegal type of object
     * @throws NoSuchAddress object doesn't have address this pointed to
     */
    Value get(Object object) throws ParameterTypeMissMatch, NoSuchAddress;

    /**
     * implement the operators(relation) the Value supports.
     * @param relation operation to compare(Equal for example)
     * @param value operators to the operation. relation(this, ...value)
     * @return result of the operation
     * @throws NoSuchOperate Value doesn't support relation
     * @throws ParameterCountError illegal count of value
     * @throws ParameterTypeMissMatch illegal type of value
     */
    Level operate(Relation relation, Value... value)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch;

    /**
     * implement the operators(relation) the Value supports.
     * @param relation operation to compare(Equal for example)
     * @param values operators to the operation. relation(this, values)
     * @return result of the operation
     * @throws NoSuchOperate Value doesn't support relation
     * @throws ParameterCountError illegal count of value
     * @throws ParameterTypeMissMatch illegal type of value
     */
    Level operate(Relation relation, List<Value> values)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch;
}
