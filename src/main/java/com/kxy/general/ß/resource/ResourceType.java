package com.kxy.general.ß.resource;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class ResourceType implements Serializable {
    private static final long serialVersionUID = 8337154481602086789L;

    @Getter @Setter
    private String name;

    @Getter
    private List<String> attributes;

    public Boolean hasAttribute(String name) {
        return this.attributes.contains(name);
    }

    public void addAttribute(String name) {
        this.attributes.add(name);
    }
}
