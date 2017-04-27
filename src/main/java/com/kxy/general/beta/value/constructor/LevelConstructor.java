package com.kxy.general.beta.value.constructor;

import com.kxy.general.beta.value.Value;
import com.kxy.general.beta.value.ValueConstructor;
import com.kxy.general.beta.value.direct.Level;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class LevelConstructor implements ValueConstructor {
    private static final long serialVersionUID = 9069854697759710346L;

    /**
     * encoding type <code>Level</code>.
     * @param value <code>Value</code> to be encoded
     * @return the encoded string
     */
    @Override
    public String encode(Value value) {
        if (value instanceof Level) {
            Level level = (Level) value;
            return level.getValue().toString() + " "
                    + level.getLow().toString() + " "
                    + level.getHigh().toString();
        } else {
            return null;
        }
    }

    /**
     * decoding type <code>Level</code>.
     * @param serialized it should be the result of
     *                   <code>ValueConstructor::encode</code>
     * @return the decoded <code>Level</code> object
     */
    @Override
    public Value decode(String serialized) {
        String[] values = serialized.split(" ");
        if (values.length == 0) {
            return new Level(Long.valueOf(values[0]),
                    Long.valueOf(values[2]), Long.valueOf(values[1]));
        } else {
            return null;
        }
    }
}
