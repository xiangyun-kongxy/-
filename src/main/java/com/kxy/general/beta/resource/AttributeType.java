package com.kxy.general.beta.resource;

import com.kxy.general.beta.value.ValueType;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class AttributeType implements Serializable {
    private static final long serialVersionUID = -8909611426025366502L;

    /**
     * type name of the attribute.
     */
    @Getter
    private String attributeName;

    /**
     * value type of the attribute.
     */
    @Getter
    private ValueType valueType;

    /**
     * init attribute type by type name and value type name.
     * value type name should be predefined in java code
     * @param attributeName type name
     * @param typename value type name
     */
    public AttributeType(String attributeName, String typename) {
        this.attributeName = attributeName;
        valueType = ValueType.valueOf(typename);
    }
}
