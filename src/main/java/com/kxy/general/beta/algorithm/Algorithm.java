package com.kxy.general.beta.algorithm;

import com.kxy.general.beta.rule.RuleSet;
import com.kxy.general.beta.resource.Resource;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Algorithm {
    /**
     * compute the given <code>resources</code> using the given
     * <code>ruleSet</code>. and return the resources who matches
     * <code>ruleSet</code>
     * @param resources candidates to compute
     * @param ruleSet the rules to selectors
     * @return the matched resources
     */
    List<Resource> computer(List<Resource> resources, RuleSet ruleSet);
}
