package com.kxy.general.beta.relation;

import com.kxy.general.beta.value.Value;
import com.kxy.general.beta.value.direct.Level;

import java.util.List;

/**
 * Created by xiangyunkong on 17/04/2017.
 */
public class AbstractRelation implements Relation {
    private static final long serialVersionUID = 528635457906800309L;

    /**
     * compute how much match the relation the values are.
     * @param values the operators to the relation
     * @return the value of matching
     */
    @Override
    public Level compare(final List<Value> values) {
        if (values == null || values.size() == 0) {
            return new Level(0L);
        }

        List<Value> param = values.subList(1, values.size() - 1);
        try {
            return values.get(0).operate(this, param);
        } catch (Exception e) {
            return new Level(0L);
        }
    }
}
