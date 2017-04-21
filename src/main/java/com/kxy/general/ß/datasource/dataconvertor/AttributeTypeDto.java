package com.kxy.general.ß.datasource.dataconvertor;

import com.kxy.general.ß.datasource.dataobject.AttributeTypeDo;
import com.kxy.general.ß.resource.AttributeType;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class AttributeTypeDto {
    public static AttributeType toAttributeType(AttributeTypeDo attributeTypeDo) {
        return new AttributeType(attributeTypeDo.getAttributeName(),
                attributeTypeDo.getValueTypeName());
    }

    public static AttributeTypeDo toAttributeTypeDo(AttributeType attributeType) {
        AttributeTypeDo attributeTypeDo = new AttributeTypeDo();
        attributeTypeDo.setAttributeName(attributeType.getAttributeName());
        attributeTypeDo.setValueTypeName(attributeType.getValueType().getName());
        return attributeTypeDo;
    }
}
