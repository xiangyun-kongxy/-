package com.kxy.general.beta.rule;

import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.value.direct.Level;

import java.io.Serializable;

/**
 * resource requirement rule.
 *
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Rule extends Serializable {
    /**
     * check if the resource matches the Rule. and how much it matches.
     * @param resource Resource to be checked
     * @return how much the Resource matches the Rule
     */
    Level match(Resource resource);
}
