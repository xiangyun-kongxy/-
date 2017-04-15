package com.kxy.general.ß.schedule.simple;

import com.kxy.general.ß.rule.RuleSet;
import com.kxy.general.ß.resource.Resource;
import com.kxy.general.ß.schedule.Schedule;
import com.kxy.general.ß.algorithm.Algorithm;
import lombok.Setter;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class SimpleSchedule implements Schedule {
    @Setter
    private Algorithm algorithm;

    public List<Resource> schedule(List<Resource> resources, RuleSet ruleSet) {
        return algorithm.computer(resources, ruleSet);
    }
}
