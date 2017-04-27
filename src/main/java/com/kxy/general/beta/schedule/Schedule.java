package com.kxy.general.beta.schedule;

import com.kxy.general.beta.rule.RuleSet;
import com.kxy.general.beta.resource.Resource;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Schedule {

    /**
     * schedule the <code>Resource</code>es by <code>RuleSet</code>.
     * @param resources <code>Resource</code>s to be scheduled
     * @param ruleSet <code>RuleSet</code> for defining which
     *                <code>Resource</code> can be chosen
     * @return the chosen <code>Resource</code>s
     */
    List<Resource> schedule(List<Resource> resources, RuleSet ruleSet);
}
