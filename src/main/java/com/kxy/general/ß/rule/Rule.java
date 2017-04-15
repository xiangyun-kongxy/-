package com.kxy.general.ß.rule;

import com.kxy.general.ß.resource.Resource;
import com.kxy.general.ß.value.direct.Level;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Rule extends Serializable {
    /**
     *
     * @param resource
     * @return
     */
    Level match(Resource resource);
}
