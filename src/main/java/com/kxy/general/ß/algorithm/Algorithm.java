package com.kxy.general.ß.algorithm;

import com.kxy.general.ß.rule.RuleSet;
import com.kxy.general.ß.resource.Resource;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Algorithm {
    /**
     *
     * @param resources
     * @param ruleSet
     * @return
     */
    List<Resource> computer(List<Resource> resources, RuleSet ruleSet);
}
