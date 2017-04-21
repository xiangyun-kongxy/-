package com.kxy.general.ß.algorithm.filter;

import com.kxy.general.ß.resource.Resource;
import com.kxy.general.ß.rule.RuleSet;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface AttributeFilter {
    List<Resource> filter(List<Resource> resources, RuleSet ruleSet);
}
