package com.kxy.general.beta.algorithm;

import com.kxy.general.beta.rule.RuleSet;
import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.algorithm.filter.AttributeFilter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class AttributeFilterAlgorithm implements Algorithm {
    /**
     * attributes will be processed in the computing.
     */
    @Setter
    private List<AttributeFilter> filters;

    /**
     * use attribute processors compute resources.
     * @param resources candidates to compute
     * @param ruleSet the rules to selectors
     * @return matched resources
     */
    public List<Resource> computer(List<Resource> resources, RuleSet ruleSet) {
        List<Resource> result = new ArrayList<>(resources);

        for (AttributeFilter filter:filters) {
            result = filter.filter(result, ruleSet);
        }

        return result;
    }
}
