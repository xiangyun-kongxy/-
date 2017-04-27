package com.kxy.general.beta.resource;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class Resource implements Serializable {
    private static final long serialVersionUID = -3923420049158741839L;

    /**
     * attributes of the <code>Resource</code>.
     */
    @Getter
    private Map<String, Attribute> attributes = new HashMap<>();

    /**
     * the type of the resource.
     */
    @Getter
    private ResourceType resourceType;

    /**
     * uuid of the resource.
     */
    @Getter
    private String resourceId;

    /**
     * init <code>Resource</code> with id and type. attributes should be set
     * later.
     * @param resourceId resource id
     * @param resourceType resource type
     */
    public Resource(String resourceId, ResourceType resourceType) {
        this.resourceId = resourceId;
        this.resourceType = resourceType;
    }

    /**
     * get attribute by attribute name.
     * @param name attribute name
     * @return <code>Attribute</code> if exist
     */
    public Attribute getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * add an attribute into the <code>Resource</code>.
     * @param attribute attribute to be added
     */
    public void addAttribute(Attribute attribute) {
        attributes.put(attribute.getName(), attribute);
    }
}
