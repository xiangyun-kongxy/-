package com.kxy.general.ß.schedule;

import com.kxy.general.ß.rule.RuleSet;
import com.kxy.general.ß.resource.Resource;

import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public interface Schedule {

    /**
     *
     * @param resources
     * @return
     */
    List<Resource> schedule(List<Resource> resources, RuleSet ruleSet);
}
