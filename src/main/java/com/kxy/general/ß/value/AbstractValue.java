package com.kxy.general.ß.value;

import lombok.Getter;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class AbstractValue implements Value {
    private static final long serialVersionUID = 991253607836803448L;

    @Getter
    private Serializable object;

    public AbstractValue(Serializable object) {
        this.object = object;
    }

    @Override
    public Object get() {
        return this.object;
    }

    @Override
    public Object get(Object object) {
        return get();
    }
}
