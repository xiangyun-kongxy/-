package com.kxy.general.beta.datasource.dataconvertor;

import com.kxy.general.beta.datasource.dataaccess.TypeHolder;
import com.kxy.general.beta.datasource.dataobject.AttributeDo;
import com.kxy.general.beta.resource.Attribute;
import com.kxy.general.beta.resource.AttributeType;
import com.kxy.general.beta.value.ValueType;

/**
 * converter between attribute and attribute data object.
 * Created by xiangyunkong on 21/04/2017.
 */
public final class AttributeDto {
    /**
     * utility class, do not create it.
     */
    private AttributeDto() {
    }

    /**
     * convert AttributeDo to Attribute.
     * @param attributeDo object to be convert
     * @return the converted object
     */
    public static Attribute toAttribute(AttributeDo attributeDo) {
        Attribute attribute = new Attribute(attributeDo.getName(),
                attributeDo.getResourceId());
        AttributeType attributeType = TypeHolder.getType(attributeDo.getName(),
                AttributeType.class);
        if (attributeType != null) {
            attribute.setValue(attributeType.getValueType().decode(
                    attributeDo.getValue()));
        } else {
            attribute.setValue(null);
        }
        return attribute;
    }

    /**
     * convert an Attribute to an AttributeDo.
     * @param attribute attribute to convert
     * @return the converted attribute
     */
    public static AttributeDo toAttributeDo(Attribute attribute) {
        ValueType valueType = attribute.getValue().getValueType();

        AttributeDo attributeDo = new AttributeDo();
        attributeDo.setName(attribute.getName());
        attributeDo.setValue(valueType.encode(attribute.getValue()));
        attributeDo.setResourceId(attribute.getResourceId());

        return attributeDo;
    }
}
