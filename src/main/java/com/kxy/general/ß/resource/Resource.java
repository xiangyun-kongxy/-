package com.kxy.general.ß.resource;

import com.kxy.general.ß.attribute.Attribute;
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

    public Attribute getAttribute(String name) {
        return attributes.get(name);
    }

    public void addAttribute(Attribute attribute) {
        attributes.put(attribute.getName(), attribute);
    }
}
