package com.kxy.general.beta.value.mapping;

import com.kxy.general.beta.relation.Relation;
import com.kxy.general.beta.relation.compare.Equal;
import com.kxy.general.beta.resource.Attribute;
import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.value.AbstractValue;
import com.kxy.general.beta.value.Value;
import com.kxy.general.beta.value.ValueType;
import com.kxy.general.beta.value.direct.Level;
import com.kxy.general.beta.value.exception.NoSuchAddress;
import com.kxy.general.beta.value.exception.NoSuchOperate;
import com.kxy.general.beta.value.exception.ParameterCountError;
import com.kxy.general.beta.value.exception.ParameterTypeMissMatch;
import lombok.Getter;

import java.util.List;

/**
 *
 * Created by xiangyunkong on 14/04/2017.
 */
public class ResourceAddressValue extends AbstractValue {
    private static final long serialVersionUID = -7641575993728687013L;

    /**
     * address of the Value. actually, it is the attribute name of a resource.
     */
    @Getter
    private String addressName;

    /**
     * init ResourceAddressValue with an attribute name.
     * @param name attribute name of a resource.
     */
    public ResourceAddressValue(String name) {
        this.addressName = name;
    }

    /**
     * @see AbstractValue#getValueType()
     */
    @Override
    public ValueType getValueType() {
        return ValueType.RESOURCE_ADDRESS;
    }

    /**
     * get the actual value the address point to.
     * @param object for mapping value, it is the original value
     * @return object's attribute value, whose name is this.addressName
     * @throws ParameterTypeMissMatch object is not a resource
     * @throws NoSuchAddress the resource doesn't have a attribute named
     *          this.addressName
     */
    @Override
    public Value get(Object object)
            throws ParameterTypeMissMatch, NoSuchAddress {
        if (!(object instanceof Resource)) {
            throw new ParameterTypeMissMatch();
        }

        Resource resource = (Resource) object;
        Attribute attribute = resource.getAttribute(addressName);
        if (attribute != null) {
            return attribute.getValue();
        }

        throw new NoSuchAddress();
    }

    /**
     * define ResourceAddressValue supported operations.
     * @param relation the relation to compare
     * @param values the values to compare
     * @return result of the operation
     * @throws NoSuchOperate ResourceAddressValue doesn't support Relation
     * @throws ParameterCountError illegal values count
     * @throws ParameterTypeMissMatch illegal type values
     */
    @Override
    public Level operate(Relation relation, List<Value> values)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch {

        if (this.matchOperator(relation, Equal.class)) {
            this.checkParameter(values, 1, ResourceAddressValue.class);

            ResourceAddressValue another = (ResourceAddressValue) values.get(0);
            if (this.addressName.equals(another.addressName)) {
                return new Level(1L);
            } else {
                return new Level(0L);
            }
        }

        return super.operate(relation, values);
    }
}
