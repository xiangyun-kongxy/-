package com.kxy.general.beta.value.constructor;

import com.kxy.general.beta.value.Value;
import com.kxy.general.beta.value.ValueConstructor;
import com.kxy.general.beta.value.direct.StringValue;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class StringConstructor implements ValueConstructor {
    private static final long serialVersionUID = -7596810961610598288L;

    /**
     * @see ValueConstructor#encode(Value)
     * @param value <code>Value</code> should be <code>StringValue</code>
     */
    @Override
    public String encode(Value value) {
        if (value instanceof StringValue) {
            return ((StringValue) value).getValue();
        } else {
            return "";
        }
    }

    /**
     * @see ValueConstructor#decode(String)
     */
    @Override
    public Value decode(String serialized) {
        return new StringValue(serialized);
    }
}
