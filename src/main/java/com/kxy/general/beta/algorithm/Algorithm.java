package com.kxy.general.beta.algorithm;

import com.kxy.general.beta.rule.RuleSet;
import com.kxy.general.beta.resource.Resource;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Algorithm {
    /**
     * compute the given resources using the given ruleSet. and return the
     * resources who matches ruleSet.
     * @param resources candidates to compute
     * @param ruleSet the rules to selectors
     * @return the matched resources
     */
    List<Resource> computer(List<Resource> resources, RuleSet ruleSet);
}
