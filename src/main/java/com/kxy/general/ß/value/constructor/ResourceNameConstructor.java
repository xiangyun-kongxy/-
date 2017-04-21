package com.kxy.general.ß.value.constructor;

import com.kxy.general.ß.value.Value;
import com.kxy.general.ß.value.ValueConstructor;
import com.kxy.general.ß.value.mapping.ResourceNameValue;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class ResourceNameConstructor implements ValueConstructor {
    private static final long serialVersionUID = 324059197236784342L;

    @Override
    public String encode(Value value) {
        return "";
    }

    @Override
    public Value decode(String serialized) {
        return new ResourceNameValue();
    }
}
