package com.kxy.general.ß.algorithm.determiner;

import com.kxy.general.ß.resource.Resource;
import com.kxy.general.ß.rule.RuleSet;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface AttributeDeterminer {
    /**
     *
     * @param resource
     * @param ruleSet
     * @return
     */
    Double determine(Resource resource, RuleSet ruleSet);
}
