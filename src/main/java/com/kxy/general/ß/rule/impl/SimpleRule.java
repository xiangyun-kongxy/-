package com.kxy.general.ß.rule.impl;

import com.kxy.general.ß.relation.Relation;
import com.kxy.general.ß.resource.Resource;
import com.kxy.general.ß.rule.Rule;
import com.kxy.general.ß.value.Value;
import com.kxy.general.ß.value.direct.Level;
import com.kxy.general.ß.value.exception.NoSuchAddress;
import com.kxy.general.ß.value.exception.ParameterTypeMissMatch;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class SimpleRule implements Rule {
    private static final long serialVersionUID = 6378203051307630516L;

    @Getter
    private Relation relation;
    @Getter
    private List<Value> values = new ArrayList<>();

    public SimpleRule(Relation relation, Value ...values) {
        this.relation = relation;
        this.values.addAll(Arrays.asList(values));
    }

    public Level match(Resource resource) {
        final List<Value> param = new ArrayList<>();
        values.forEach(value -> {
            try {
                param.add(value.get(resource));
            } catch (ParameterTypeMissMatch | NoSuchAddress parameterTypeMissMatch) {
                parameterTypeMissMatch.printStackTrace();
            }
        });

        return relation.compare(param);
    }

}
