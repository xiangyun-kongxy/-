package com.kxy.general.beta.value;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public interface ValueConstructor extends Serializable {
    /**
     * encode a Value.
     * @param value Value to be encoded
     * @return the encoded string
     */
    String encode(Value value);

    /**
     * decode an encoded Value. serialized should be the result of
     * ValueConstructor::encode
     * @param serialized it should be the result of ValueConstructor::encode
     * @return the decoded Value
     */
    Value decode(String serialized);
}
