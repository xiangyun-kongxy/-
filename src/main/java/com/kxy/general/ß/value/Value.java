package com.kxy.general.ß.value;

import com.kxy.general.ß.relation.Relation;
import com.kxy.general.ß.value.direct.Level;
import com.kxy.general.ß.value.exception.NoSuchAddress;
import com.kxy.general.ß.value.exception.NoSuchOperator;
import com.kxy.general.ß.value.exception.ParameterCountError;
import com.kxy.general.ß.value.exception.ParameterTypeMissMatch;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Value extends Serializable {
    /**
     *
     * @return
     */
    ValueType getValueType();

    /**
     *
     * @param object
     * @return
     * @throws ParameterTypeMissMatch
     * @throws NoSuchAddress
     */
    Value get(Object object) throws ParameterTypeMissMatch, NoSuchAddress;

    /**
     *
     * @param relation
     * @param value
     * @return
     * @throws NoSuchOperator
     * @throws ParameterCountError
     * @throws ParameterTypeMissMatch
     */
    Level operator(Relation relation, Value ...value)
            throws NoSuchOperator, ParameterCountError, ParameterTypeMissMatch;

    /**
     *
     * @param relation
     * @param values
     * @return
     * @throws NoSuchOperator
     * @throws ParameterCountError
     * @throws ParameterTypeMissMatch
     */
    Level operator(Relation relation, List<Value> values)
            throws NoSuchOperator, ParameterCountError, ParameterTypeMissMatch;
}
