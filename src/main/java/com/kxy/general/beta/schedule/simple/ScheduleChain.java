package com.kxy.general.beta.schedule.simple;

import com.kxy.general.beta.rule.RuleSet;
import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.schedule.Schedule;
import lombok.Setter;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class ScheduleChain implements Schedule {
    /**
     * Schedules in the chain.
     */
    @Setter
    private List<Schedule> schedules;

    /**
     * schedule using the chain(Schedule list) one by one.
     * @param resources Resources to be scheduled
     * @param ruleSet RuleSet for defining which Resource can be chosen
     * @return the scheduled Resources
     */
    public List<Resource> schedule(List<Resource> resources, RuleSet ruleSet) {
        List<Resource> result = resources;
        for (Schedule schedule:schedules) {
            result = schedule.schedule(result, ruleSet);
        }
        return result;
    }
}
