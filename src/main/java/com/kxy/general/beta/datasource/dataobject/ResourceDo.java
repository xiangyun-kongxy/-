package com.kxy.general.beta.datasource.dataobject;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class ResourceDo implements Serializable {
    private static final long serialVersionUID = -5081602032361511981L;

    /**
     * id of the <code>Resource</code>.
     */
    @Getter @Setter
    private String resourceId;

    /**
     * type name of the <code>Resource</code>.
     */
    @Getter @Setter
    private String resourceType;
}
