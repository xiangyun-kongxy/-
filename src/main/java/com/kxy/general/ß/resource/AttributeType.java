package com.kxy.general.ß.resource;

import com.kxy.general.ß.value.ValueType;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class AttributeType implements Serializable {
    private static final long serialVersionUID = -8909611426025366502L;

    @Getter
    private String AttributeName;
    @Getter
    private ValueType valueType;

    public AttributeType(String attributeName, String typename) {
        this.AttributeName = attributeName;
        valueType = ValueType.valueOf(typename);
    }
}
