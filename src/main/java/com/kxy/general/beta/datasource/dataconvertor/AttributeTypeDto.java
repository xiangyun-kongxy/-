package com.kxy.general.beta.datasource.dataconvertor;

import com.kxy.general.beta.datasource.dataobject.AttributeTypeDo;
import com.kxy.general.beta.resource.AttributeType;
import com.kxy.general.beta.value.ValueType;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public final class AttributeTypeDto {
    /**
     * utility class should not be instanced.
     */
    private AttributeTypeDto() {
    }

    /**
     * convert AttributeTypeDo to AttributeType.
     * @param attributeTypeDo attribute type to be convert
     * @return converted attribute type
     */
    public static AttributeType toAttributeType(
            AttributeTypeDo attributeTypeDo) {
        return new AttributeType(attributeTypeDo.getAttributeName(),
                attributeTypeDo.getValueTypeName());
    }

    /**
     * convert AttributeType to AttributeTypeDo.
     * @param attributeType attribute type to be convert
     * @return converted attribute type
     */
    public static AttributeTypeDo toAttributeTypeDo(
            AttributeType attributeType) {
        ValueType valueType = attributeType.getValueType();
        if (valueType == null) {
            return null;
        }

        AttributeTypeDo attributeTypeDo = new AttributeTypeDo();
        attributeTypeDo.setAttributeName(attributeType.getAttributeName());
        attributeTypeDo.setValueTypeName(valueType.getName());
        return attributeTypeDo;
    }
}
