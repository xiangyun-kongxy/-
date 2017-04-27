package com.kxy.general.beta.algorithm.determiner.impl;

import com.kxy.general.beta.algorithm.determiner.AbstractAttributeDeterminer;
import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.rule.RuleSet;

/**
 * Created by xiangyunkong on 24/04/2017.
 */
public class ExampleDeterminer extends AbstractAttributeDeterminer {
    /**
     * always return 1.0. just for example.
     * @param resource the resource to compute
     * @param ruleSet the rules to compute
     * @return
     */
    @Override
    public Double determine(Resource resource, RuleSet ruleSet) {
        return 1.0;
    }
}
