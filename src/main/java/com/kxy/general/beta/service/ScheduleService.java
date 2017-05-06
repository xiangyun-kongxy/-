package com.kxy.general.beta.service;

import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.rule.RuleSet;

import java.util.List;

/**
 * Created by xiangyunkong on 03/05/2017.
 */
public interface ScheduleService {
    /**
     * choose resource by rule set.
     * @param ruleSet rule for filter resource
     * @return matched resources
     */
    List<Resource> chooseResource(RuleSet ruleSet);
}
