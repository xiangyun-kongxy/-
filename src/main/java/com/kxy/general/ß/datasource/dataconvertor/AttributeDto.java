package com.kxy.general.ß.datasource.dataconvertor;

import com.kxy.general.ß.datasource.dataaccess.TypeHolder;
import com.kxy.general.ß.datasource.dataobject.AttributeDo;
import com.kxy.general.ß.resource.Attribute;
import com.kxy.general.ß.value.ValueType;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class AttributeDto {
    public static Attribute toAttribute(AttributeDo attributeDo) {
        Attribute attribute = new Attribute(attributeDo.getName(), attributeDo.getResourceId());
        ValueType valueType = TypeHolder.getType(attributeDo.getName(), ValueType.class);
        if(valueType != null) {
            attribute.setValue(valueType.decode(attributeDo.getValue()));
        } else {
            // TODO: logging
        }
        return attribute;
    }

    public static AttributeDo toAttributeDo(Attribute attribute) {
        ValueType valueType = attribute.getValue().getValueType();

        AttributeDo attributeDo = new AttributeDo();
        attributeDo.setName(attribute.getName());
        attributeDo.setValue(valueType.encode(attribute.getValue()));
        attributeDo.setResourceId(attribute.getResourceId());

        return attributeDo;
    }
}
