package com.kxy.general.ß.relation;

import com.kxy.general.ß.value.direct.Level;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Relation extends Serializable {
    /**
     *
     * @param values
     * @return
     */
    Boolean match(List<Object> values);

    /**
     *
     * @param values
     * @return
     */
    Level different(List<Object> values);
}
