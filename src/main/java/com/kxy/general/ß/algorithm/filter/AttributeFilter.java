package com.kxy.general.ß.algorithm.filter;

import com.kxy.general.ß.rule.RuleSet;
import com.kxy.general.ß.resource.Resource;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface AttributeFilter {
    Resource filter(Resource resource, RuleSet ruleSet);
}
