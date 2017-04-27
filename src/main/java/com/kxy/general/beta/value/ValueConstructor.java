package com.kxy.general.beta.value;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public interface ValueConstructor extends Serializable {
    /**
     * encode a <code>Value</code>.
     * @param value <code>Value</code> to be encoded
     * @return the encoded string
     */
    String encode(Value value);

    /**
     * decode an encoded <code>Value</code>. <code>serialized</code> should
     * be the result of <code>ValueConstructor::encode</code>
     * @param serialized it should be the result of
     *                   <code>ValueConstructor::encode</code>
     * @return the decoded <code>Value</code>
     */
    Value decode(String serialized);
}
