package com.kxy.general.beta.algorithm.determiner;

import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.rule.RuleSet;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface AttributeDeterminer {
    /**
     * compute the score of resource by ruleSet.
     * @param resource the resource to compute
     * @param ruleSet the rules to compute
     * @return the score of the resource
     */
    Double determine(Resource resource, RuleSet ruleSet);
}
