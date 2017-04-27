package com.kxy.general.beta.value.mapping;

import com.kxy.general.beta.relation.Relation;
import com.kxy.general.beta.relation.compare.Equal;
import com.kxy.general.beta.resource.Attribute;
import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.value.AbstractValue;
import com.kxy.general.beta.value.Value;
import com.kxy.general.beta.value.direct.Level;
import com.kxy.general.beta.value.exception.NoSuchAddress;
import com.kxy.general.beta.value.exception.NoSuchOperate;
import com.kxy.general.beta.value.exception.ParameterCountError;
import com.kxy.general.beta.value.exception.ParameterTypeMissMatch;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public abstract class ResourceAddressValue extends AbstractValue {
    private static final long serialVersionUID = -7641575993728687013L;

    /**
     * address of the <code>Value</code>. actually, it is the attribute name
     * of a resource.
     */
    private String addressName;

    /**
     * init <code>ResourceAddressValue</code> with an attribute name.
     * @param name attribute name of a resource.
     */
    public ResourceAddressValue(String name) {
        this.addressName = name;
    }

    /**
     * get the actual value the address point to.
     * @param object for mapping value, it is the original value
     * @return <code>object</code>'s attribute value, whose name is
     * <code>this.addressName</code>
     * @throws ParameterTypeMissMatch <code>object</code> is not a resource
     * @throws NoSuchAddress the resource doesn't have a attribute named
     *          <code>this.addressName</code>
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
     * define <code>ResourceAddressValue</code> supported operations.
     * @param relation the relation to compare
     * @param values the values to compare
     * @return result of the operation
     * @throws NoSuchOperate <code>ResourceAddressValue</code> doesn't support
     *          <code>Relation</code>
     * @throws ParameterCountError illegal <code>values</code> count
     * @throws ParameterTypeMissMatch illegal type <code>values</code>
     */
    @Override
    public Level operate(Relation relation, List<Value> values)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch {

        if (this.matchOperator(relation, Equal.class)) {
            this.checkParameter(values, 1, ResourceAddressValue.class);

            ResourceAddressValue another = (ResourceAddressValue) values.get(0);
            if (this.addressName.equals(another.addressName)) {
                return new Level(1L, 0L, 1L);
            } else {
                return new Level(0L, 0L, 1L);
            }
        }

        return super.operate(relation, values);
    }
}
