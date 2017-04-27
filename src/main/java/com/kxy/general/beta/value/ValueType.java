package com.kxy.general.beta.value;

import com.kxy.general.beta.value.constructor.LevelConstructor;
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
     * <code>ValueType</code> of type <code>ResourceNameValue</code>.
     */
    RESOURCE_NAME("resource_name"),

    /**
     * <code>ValueType</code> of type <code>StringValue</code>.
     */
    STRING("string"),

    /**
     * <code>ValueType</code> of type <code>Level</code>.
     */
    LEVEL("level");

    /**
     * type name.
     */
    @Getter
    private String name;

    /**
     * the objects who can serialize/deserialize <code>Value</code>.
     */
    private final Map<String, ValueConstructor> valueConstructors;

    {
        valueConstructors = new HashMap<>();

        valueConstructors.put("resource_name", new ResourceNameConstructor());
        valueConstructors.put("string", new StringConstructor());
        valueConstructors.put("level", new LevelConstructor());
    }

    /**
     * init <code>ValueType</code> with type name. type name should be
     * predefined
     * @param name type name
     */
    ValueType(String name) {
        this.name = name;
    }

    /**
     * decode an encoded <code>Value</code>. <code>serialized</code> should
     * be the result of <code>ValueConstructor::encode</code>
     * @param serialized it should be the result of
     *                   <code>ValueConstructor::encode</code>
     * @return the decoded <code>Value</code>
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
     * encode a <code>Value</code>.
     * @param value <code>Value</code> to be encoded
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
