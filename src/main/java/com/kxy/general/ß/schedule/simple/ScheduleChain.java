package com.kxy.general.ß.schedule.simple;

import com.kxy.general.ß.rule.RuleSet;
import com.kxy.general.ß.resource.Resource;
import com.kxy.general.ß.schedule.Schedule;
import lombok.Setter;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class ScheduleChain implements Schedule {
    @Setter
    private List<Schedule> schedules;

    public List<Resource> schedule(List<Resource> resources, RuleSet ruleSet) {
        List<Resource> result = resources;
        for(Schedule schedule:schedules) {
            result = schedule.schedule(result, ruleSet);
        }
        return result;
    }
}
