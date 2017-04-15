package com.kxy.general.ß.relation.compare;

import com.kxy.general.ß.relation.Relation;
import com.kxy.general.ß.value.direct.Level;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class Equal implements Relation {
    private static final long serialVersionUID = 3725502491351449616L;

    @Override
    public Boolean match(List<Object> values) {
        if(values == null || values.size() != 2)
            return Boolean.FALSE;
        return values.get(0).equals(values.get(1));
    }

    @Override
    public Level different(List<Object> values) {
        return null;
    }
}
