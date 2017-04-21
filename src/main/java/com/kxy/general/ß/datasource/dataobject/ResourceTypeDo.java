package com.kxy.general.ß.datasource.dataobject;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class ResourceTypeDo implements Serializable {
    private static final long serialVersionUID = 4772116381439186409L;

    @Getter @Setter
    private String typename;
    @Getter @Setter
    private List<String> attributes;
}
