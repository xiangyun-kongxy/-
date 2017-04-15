package com.kxy.general.ß.value.mapping;

import com.kxy.general.ß.value.AbstractValue;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public abstract class AddressValue extends AbstractValue {
    private static final long serialVersionUID = -7641575993728687013L;

    public AddressValue(Serializable object) {
        super(object);
    }

    @Override
    public Object get() {
        // ERROR: use address as a value
        return null;
    }

    @Override
    public Object get(Object object) {
        // Not implemented
        return null;
    }
}
