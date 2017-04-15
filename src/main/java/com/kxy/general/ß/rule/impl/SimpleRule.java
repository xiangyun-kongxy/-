package com.kxy.general.ß.rule.impl;

import com.kxy.general.ß.relation.Relation;
import com.kxy.general.ß.resource.Resource;
import com.kxy.general.ß.rule.Rule;
import com.kxy.general.ß.value.direct.Level;
import com.kxy.general.ß.value.Value;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class SimpleRule implements Rule {
    private static final long serialVersionUID = 6378203051307630516L;

    @Setter
    private Relation relation;
    private List<Value> values = new ArrayList<>();

    public Level match(Resource resource) {
        final List<Object> param = new ArrayList<>();
        values.forEach(value -> param.add(value.get()));
        if(relation.match(param)) {
            return relation.different(param);
        }
        return null;
    }

    public void addValue(Integer index, Value value) {
        values.add(index, value);
    }
}
