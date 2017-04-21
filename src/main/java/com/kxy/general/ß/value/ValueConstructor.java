package com.kxy.general.ß.value;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public interface ValueConstructor extends Serializable {
    /**
     *
     * @param value
     * @return
     */
    String encode(Value value);

    /**
     *
     * @param serialized
     * @return
     */
    Value decode(String serialized);
}
