package com.kxy.general.beta.datasource.dataobject;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class AttributeDo implements Serializable {
    private static final long serialVersionUID = 4599615617428634454L;

    /**
     * the attribute's name. also the type name
     */
    @Getter @Setter
    private String name;

    /**
     * the serialized value of the attribute.
     */
    @Getter @Setter
    private String value;

    /**
     * the owner id.
     */
    @Getter @Setter
    private String resourceId;
}
