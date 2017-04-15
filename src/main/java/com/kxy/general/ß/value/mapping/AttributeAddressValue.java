package com.kxy.general.ß.value.mapping;

import com.kxy.general.ß.attribute.Attribute;
import com.kxy.general.ß.resource.Resource;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class AttributeAddressValue extends AddressValue {
    private static final long serialVersionUID = -7235935287021788420L;

    public AttributeAddressValue(Serializable object) {
        super(object);
    }

    @Override
    public Object get(Object object) {
        if(this.getObject() instanceof String) {
            if(object instanceof Resource) {
                Attribute attribute = ((Resource)object).getAttribute((String)this.getObject());
                if(attribute != null)
                    return attribute.getValue();
            } else if(object instanceof Attribute &&
                    this.getObject().equals(((Attribute) object).getName())) {
                return ((Attribute)object).getValue();
            }
        }
        return null;
    }
}
