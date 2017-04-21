package com.kxy.general.ß.value.constructor;

import com.kxy.general.ß.value.Value;
import com.kxy.general.ß.value.ValueConstructor;
import com.kxy.general.ß.value.direct.StringValue;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class StringConstructor implements ValueConstructor {
    private static final long serialVersionUID = -7596810961610598288L;

    @Override
    public String encode(Value value) {
        if(value instanceof StringValue) {
            return ((StringValue)value).getValue();
        } else {
            return "";
        }
    }

    @Override
    public Value decode(String serialized) {
        return new StringValue(serialized);
    }
}
