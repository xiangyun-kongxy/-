package com.kxy.general.beta.datasource.dataobject;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class AttributeTypeDo implements Serializable {
    private static final long serialVersionUID = 7385200757849851231L;

    /**
     * type name.
     */
    @Getter @Setter
    private String attributeName;

    /**
     * value type name.
     */
    @Getter @Setter
    private String valueTypeName;
}
