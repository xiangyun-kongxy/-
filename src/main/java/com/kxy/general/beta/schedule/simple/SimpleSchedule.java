package com.kxy.general.beta.schedule.simple;

import com.kxy.general.beta.rule.RuleSet;
import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.schedule.Schedule;
import com.kxy.general.beta.algorithm.Algorithm;
import lombok.Setter;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class SimpleSchedule implements Schedule {
    /**
     * algorithm used by the scheduler.
     */
    @Setter
    private Algorithm algorithm;

    /**
     * schedule resources by configured algorithm.
     * @param resources Resources to be scheduled
     * @param ruleSet RuleSet for defining which Resource can be chosen
     * @return the scheduled resources;
     */
    public List<Resource> schedule(List<Resource> resources, RuleSet ruleSet) {
        return algorithm.computer(resources, ruleSet);
    }
}
