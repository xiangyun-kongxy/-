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
        List<Resource> result = new ArrayList<>(resources);

        for(AttributeFilter filter:filters) {
            result = filter.filter(result, ruleSet);
        }

        return result;
    }
}
