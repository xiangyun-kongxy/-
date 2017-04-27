package com.kxy.general.beta.algorithm.filter;

import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.rule.RuleSet;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface AttributeFilter {
    /**
     * some attributes of a resource can be computed. this is a processor to
     * compute one field of a resource
     * @param resources resource to be computed
     * @param ruleSet rules for checking resource
     * @return matched resources
     */
    List<Resource> filter(List<Resource> resources, RuleSet ruleSet);
}
