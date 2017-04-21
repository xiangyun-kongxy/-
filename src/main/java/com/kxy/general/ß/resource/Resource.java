package com.kxy.general.ß.resource;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class Resource implements Serializable {
    private static final long serialVersionUID = -3923420049158741839L;

    @Getter
    private Map<String, Attribute> attributes = new HashMap<>();

    @Getter
    private ResourceType resourceType;
    @Getter
    private String resourceId;

    public Resource(String resourceId, ResourceType resourceType) {
        this.resourceId = resourceId;
        this.resourceType = resourceType;
    }

    public Attribute getAttribute(String name) {
        return attributes.get(name);
    }

    public void addAttribute(Attribute attribute) {
        attributes.put(attribute.getName(), attribute);
    }
}
