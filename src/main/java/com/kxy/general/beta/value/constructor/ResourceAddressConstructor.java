package com.kxy.general.beta.value.constructor;

import com.kxy.general.beta.value.Value;
import com.kxy.general.beta.value.ValueConstructor;
import com.kxy.general.beta.value.mapping.ResourceAddressValue;

/**
 * Created by xiangyunkong on 03/05/2017.
 */
public class ResourceAddressConstructor implements ValueConstructor {
    private static final long serialVersionUID = -3567402276303061840L;

    /**
     * @see ValueConstructor#encode(Value)
     */
    @Override
    public String encode(Value value) {
        ResourceAddressValue real = (ResourceAddressValue) value;
        return real.getAddressName();
    }

    /**
     * @see ValueConstructor#decode(String)
     */
    @Override
    public Value decode(String serialized) {
        return new ResourceAddressValue(serialized);
    }
}
