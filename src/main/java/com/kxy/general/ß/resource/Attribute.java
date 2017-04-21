package com.kxy.general.ß.resource;

import com.kxy.general.ß.value.Value;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class Attribute implements Serializable {
    private static final long serialVersionUID = -8436017243908862992L;

    @Getter
    private String name;
    @Getter
    private String resourceId;
    @Getter @Setter
    private Value value;

    public Attribute(String name, String resourceId) {
        this.name = name;
        this.resourceId = resourceId;
    }

    public Attribute(String name, String resourceId, Value value) {
        this(name, resourceId);
        this.value = value;
    }
}
