package com.kxy.general.ß.value.mapping;

import com.kxy.general.ß.resource.Attribute;
import com.kxy.general.ß.relation.Relation;
import com.kxy.general.ß.relation.compare.Equal;
import com.kxy.general.ß.resource.Resource;
import com.kxy.general.ß.value.AbstractValue;
import com.kxy.general.ß.value.Value;
import com.kxy.general.ß.value.direct.Level;
import com.kxy.general.ß.value.exception.InvalidValue;
import com.kxy.general.ß.value.exception.NoSuchAddress;
import com.kxy.general.ß.value.exception.NoSuchOperator;
import com.kxy.general.ß.value.exception.ParameterCountError;
import com.kxy.general.ß.value.exception.ParameterTypeMissMatch;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public abstract class ResourceAddressValue extends AbstractValue {
    private static final long serialVersionUID = -7641575993728687013L;

    private String addressName;

    public ResourceAddressValue(String name) {
        this.addressName = name;
    }

    @Override
    public Value get(Object object) throws ParameterTypeMissMatch, NoSuchAddress {
        if(!(object instanceof Resource)) {
            throw new ParameterTypeMissMatch();
        }

        Resource resource = (Resource) object;
        Attribute attribute = resource.getAttribute(addressName);
        if(attribute != null) {
            return attribute.getValue();
        }

        throw new NoSuchAddress();
    }

    @Override
    public Level operator(Relation relation, List<Value> values)
            throws NoSuchOperator, ParameterCountError, ParameterTypeMissMatch {

        if(this.matchOperator(relation, Equal.class)) {
            this.checkParameter(values, 1, ResourceAddressValue.class);

            ResourceAddressValue another = (ResourceAddressValue) values.get(0);
            try {
                if (this.addressName.equals(another.addressName)) {
                    return new Level(1L, 0L, 1L);
                } else {
                    return new Level(0L, 0L, 1L);
                }
            } catch(InvalidValue e) {
                throw new UnknownError();
            }
        }

        return super.operator(relation, values);
    }
}
