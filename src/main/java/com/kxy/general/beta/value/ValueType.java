package com.kxy.general.beta.value;

import com.kxy.general.beta.value.constructor.LevelConstructor;
import com.kxy.general.beta.value.constructor.ResourceAddressConstructor;
import com.kxy.general.beta.value.constructor.ResourceNameConstructor;
import com.kxy.general.beta.value.constructor.StringConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public enum ValueType {
    /**
     * ValueType of ResourceAddressValue.
     */
    RESOURCE_ADDRESS("resource_address"),
    /**
     * ValueType of type ResourceNameValue.
     */
    RESOURCE_NAME("resource_name"),

    /**
     * ValueType of type StringValue.
     */
    STRING("string"),

    /**
     * ValueType of type Level.
     */
    LEVEL("level");

    /**
     * type name.
     */
    @Getter
    private String name;

    /**
     * the objects who can serialize/deserialize Value.
     */
    private final Map<String, ValueConstructor> valueConstructors;

    {
        valueConstructors = new HashMap<>();

        valueConstructors.put("resource_address",
                new ResourceAddressConstructor());
        valueConstructors.put("resource_name", new ResourceNameConstructor());
        valueConstructors.put("string", new StringConstructor());
        valueConstructors.put("level", new LevelConstructor());
    }

    /**
     * init ValueType with type name. type name should be predefined
     * @param name type name
     */
    ValueType(String name) {
        this.name = name;
    }

    /**
     * decode an encoded Value. serialized should be the result of
     * ValueConstructor::encode
     * @param serialized it should be the result of ValueConstructor::encode
     * @return the decoded Value
     */
    public Value decode(String serialized) {
        ValueConstructor constructor = valueConstructors.get(this.name);
        if (constructor != null) {
            return constructor.decode(serialized);
        } else {
            return null;
        }
    }

    /**
     * encode a Value.
     * @param value Value to be encoded
     * @return the encoded string
     */
    public String encode(Value value) {
        ValueConstructor constructor = valueConstructors.get(this.name);
        if (constructor != null) {
            return constructor.encode(value);
        } else {
            return "";
        }
    }
}
