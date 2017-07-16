package com.kxy.general.beta.resource;

import com.kxy.general.beta.value.Value;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * attribute of object.
 * Created by xiangyunkong on 14/04/2017.
 */
public class Attribute implements Serializable {
    private static final long serialVersionUID = -8436017243908862992L;

    /**
     * attribute name. also the type name
     */
    @Getter
    private String name;

    /**
     * value of the attribute.
     */
    @Getter @Setter
    private Value value;

    /**
     * the owner id.
     */
    @Getter
    private String resourceId;

    /**
     * init attribute by name and owner. the value must be set later
     * @param name the attribute name(type)
     * @param resourceId the owner id
     */
    public Attribute(final String name, final String resourceId) {
        this.name = name;
        this.resourceId = resourceId;
    }

    /**
     * init attribute by name, value and owner.
     * @param name the attribute name(type)
     * @param resourceId the owner id
     * @param value the attribute value
     */
    public Attribute(final String name, final String resourceId,
                     final Value value) {
        this(name, resourceId);
        this.value = value;
    }
}
