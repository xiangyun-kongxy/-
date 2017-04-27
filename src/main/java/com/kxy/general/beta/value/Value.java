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
     * get the type of the <code>Value</code>.
     * @return <code>ValueType</code> of the object
     */
    ValueType getValueType();

    /**
     * get the real value of <code>this</code>.
     * 1. <code>this</code> is a mapping <code>Value</code>
     *      the computed value should be returned
     * 2. <code>this</code> is not a mapping <code>Value</code>
     *      return <code>this</code> itself
     * @param object if <code>this</code> is a mapping <code>Value</code>,
     *               object is the real object context, <code>this</code> is
     *               the address of the real value
     * @return the real value
     * @throws ParameterTypeMissMatch Illegal type of <code>object</code>
     * @throws NoSuchAddress <code>object</code> doesn't have address
     *          <code>this</code> pointed to
     */
    Value get(Object object) throws ParameterTypeMissMatch, NoSuchAddress;

    /**
     * implement the operators(relation) the <code>Value</code> supports.
     * @param relation operation to compare(<code>Equal</code> for example)
     * @param value operators to the operation. relation(this, ...value)
     * @return result of the operation
     * @throws NoSuchOperate <code>Value</code> doesn't support
     *          <code>relation</code>
     * @throws ParameterCountError illegal count of <code>value</code>
     * @throws ParameterTypeMissMatch illegal type of <code>value</code>
     */
    Level operate(Relation relation, Value... value)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch;

    /**
     * implement the operators(relation) the <code>Value</code> supports.
     * @param relation operation to compare(<code>Equal</code> for example)
     * @param values operators to the operation. relation(this, values)
     * @return result of the operation
     * @throws NoSuchOperate <code>Value</code> doesn't support
     *          <code>relation</code>
     * @throws ParameterCountError illegal count of <code>value</code>
     * @throws ParameterTypeMissMatch illegal type of <code>value</code>
     */
    Level operate(Relation relation, List<Value> values)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch;
}
