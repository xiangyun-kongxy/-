package com.kxy.general.beta.service.impl;

import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.rule.RuleSet;
import com.kxy.general.beta.schedule.Schedule;
import com.kxy.general.beta.service.ResourceService;
import com.kxy.general.beta.service.ScheduleService;
import lombok.Setter;

import java.util.List;

/**
 * Created by xiangyunkong on 03/05/2017.
 */
public class ScheduleServiceImpl implements ScheduleService {
    /**
     * resource service.
     */
    @Setter
    private ResourceService resourceService;
    /**
     * scheduler used by schedule service.
     */
    @Setter
    private Schedule defaultScheduler;

    /**
     * @see ScheduleService#chooseResource(RuleSet)
     */
    @Override
    public List<Resource> chooseResource(RuleSet ruleSet) {
        return defaultScheduler.schedule(
                resourceService.loadAllResources(),
                ruleSet);
    }

}
