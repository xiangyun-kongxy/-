package com.kxy.general.ß.value;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Value extends Serializable {
    Object get();
    Object get(Object object);
}
