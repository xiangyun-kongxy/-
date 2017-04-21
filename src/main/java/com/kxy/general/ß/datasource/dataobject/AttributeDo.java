package com.kxy.general.ß.datasource.dataobject;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class AttributeDo implements Serializable {
    private static final long serialVersionUID = 4599615617428634454L;

    @Getter @Setter
    private String name;
    @Getter @Setter
    private String value;
    @Getter @Setter
    private String resourceId;
}
