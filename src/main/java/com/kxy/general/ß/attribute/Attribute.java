package com.kxy.general.ß.attribute;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
@Getter
@Setter
public class Attribute implements Serializable {
    private static final long serialVersionUID = -8436017243908862992L;

    private String name;
    private Serializable value;
}
