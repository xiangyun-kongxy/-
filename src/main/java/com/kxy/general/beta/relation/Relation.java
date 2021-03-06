package com.kxy.general.beta.relation;

import com.kxy.general.beta.value.Value;
import com.kxy.general.beta.value.direct.Level;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Relation extends Serializable {
    /**
     * check the relationship of values. at this case, a Relation is a
     * operator, and Values are operands. Value should implement this
     * `operate` in it's implement if it supports
     * @param values operands of the operation
     * @return result of the operation check
     */
    Level compare(List<Value> values);
}
