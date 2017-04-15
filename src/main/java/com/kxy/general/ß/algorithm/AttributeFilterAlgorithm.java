package com.kxy.general.ß.algorithm;

import com.kxy.general.ß.rule.RuleSet;
import com.kxy.general.ß.resource.Resource;
import com.kxy.general.ß.algorithm.filter.AttributeFilter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class AttributeFilterAlgorithm implements Algorithm {
    @Setter
    private List<AttributeFilter> filters;

    public List<Resource> computer(List<Resource> resources, RuleSet ruleSet) {
        List<Resource> result = new ArrayList<Resource>();

        for(Resource resource:resources) {
            Resource iteration = resource;
            for(AttributeFilter filter:filters) {
                while (iteration != null) {
                    iteration = filter.filter(iteration, ruleSet);
                }
            }
            if(iteration != null) {
                result.add(iteration);
            }
        }

        return result;
    }
}
