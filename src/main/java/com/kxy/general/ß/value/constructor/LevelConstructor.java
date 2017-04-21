package com.kxy.general.ß.value.constructor;

import com.kxy.general.ß.value.Value;
import com.kxy.general.ß.value.ValueConstructor;
import com.kxy.general.ß.value.direct.Level;
import com.kxy.general.ß.value.exception.InvalidValue;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class LevelConstructor implements ValueConstructor {
    private static final long serialVersionUID = 9069854697759710346L;

    @Override
    public String encode(Value value) {
        if(value instanceof Level) {
            Level level = (Level)value;
            return level.getValue().toString() + " " +
                    level.getLow().toString() + " " +
                    level.getHigh().toString();
        } else {
            return null;
        }
    }

    @Override
    public Value decode(String serialized) {
        String values[] = serialized.split(" ");
        try {
            if (values.length == 0) {
                return new Level(Long.valueOf(values[0]),
                        Long.valueOf(values[2]), Long.valueOf(values[1]));
            }
        } catch (InvalidValue e) {
            // TODO: logging
        }
        return null;
    }
}
