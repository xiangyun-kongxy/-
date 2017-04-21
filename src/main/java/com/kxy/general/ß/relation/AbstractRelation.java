package com.kxy.general.ß.relation;

import com.kxy.general.ß.value.Value;
import com.kxy.general.ß.value.direct.Level;

import java.util.List;

/**
 * Created by xiangyunkong on 17/04/2017.
 */
public class AbstractRelation implements Relation {
    private static final long serialVersionUID = 528635457906800309L;

    @Override
    public Level compare(List<Value> values) {
        if(values == null || values.size() == 0) {
            return new Level(0L);
        }

        List<Value> param = values.subList(1, values.size() - 1);
        try {
            return values.get(0).operator(this, param);
        } catch(Exception e) {
            return new Level(0L);
        }
    }
}
