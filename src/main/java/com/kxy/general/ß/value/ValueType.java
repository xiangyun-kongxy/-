package com.kxy.general.ß.value;

import com.kxy.general.ß.value.constructor.LevelConstructor;
import com.kxy.general.ß.value.constructor.ResourceNameConstructor;
import com.kxy.general.ß.value.constructor.StringConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public enum ValueType {
    RESOURCE_NAME("resource_name"),
    STRING("string"),
    LEVEL("level");

    @Getter
    private String name;

    private final Map<String, ValueConstructor> valueConstructors;

    {
        valueConstructors = new HashMap<>();

        valueConstructors.put("resource_name", new ResourceNameConstructor());
        valueConstructors.put("string", new StringConstructor());
        valueConstructors.put("level", new LevelConstructor());
    }

    ValueType(String name) {
        this.name = name;
    }

    public Value decode(String serialized) {
        ValueConstructor constructor = valueConstructors.get(this.name);
        if(constructor != null) {
            return constructor.decode(serialized);
        } else {
            return null;
        }
    }

    public String encode(Value value) {
        ValueConstructor constructor = valueConstructors.get(this.name);
        if(constructor != null) {
            return constructor.encode(value);
        } else {
            return "";
        }
    }
}
