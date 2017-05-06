package com.kxy.general.beta.schedule;

import com.kxy.general.beta.rule.RuleSet;
import com.kxy.general.beta.resource.Resource;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Schedule {

    /**
     * schedule the Resources by RuleSet.
     * @param resources Resources to be scheduled
     * @param ruleSet RuleSet for defining which Resource can be chosen
     * @return the chosen Resources
     */
    List<Resource> schedule(List<Resource> resources, RuleSet ruleSet);
}
