package com.kxy.general.beta.resource;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class ResourceType implements Serializable {
    private static final long serialVersionUID = 8337154481602086789L;

    /**
     * type name.
     */
    @Getter @Setter
    private String name;

    /**
     * <code>Attribute</code>s (name) the resource type contains.
     */
    @Getter
    private List<String> attributes;

    /**
     * to check if this type contains <code>Attribute</code> named
     * <code>name</code>.
     * @param name attribute name
     * @return true if exists
     */
    public Boolean hasAttribute(String name) {
        return this.attributes.contains(name);
    }

    /**
     * add <code>Attribute</code> into the <code>ResourceType</code>.
     * @param name attribute name
     */
    public void addAttribute(String name) {
        this.attributes.add(name);
    }
}
