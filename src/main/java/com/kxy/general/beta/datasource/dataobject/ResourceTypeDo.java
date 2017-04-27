package com.kxy.general.beta.datasource.dataobject;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class ResourceTypeDo implements Serializable {
    private static final long serialVersionUID = 4772116381439186409L;

    /**
     * type name of the <code>ResourceType</code>.
     */
    @Getter @Setter
    private String typename;

    /**
     * <code>Attribute</code>s the <code>ResourceType</code> contains.
     */
    @Getter @Setter
    private List<String> attributes;
}
