package com.kxy.general.ß.algorithm;

import com.kxy.general.ß.algorithm.determiner.AbstractAttributeDeterminer;
import com.kxy.general.ß.resource.Resource;
import com.kxy.general.ß.rule.RuleSet;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class AttributePolynomialAlgorithm implements Algorithm {
    @Setter
    private List<AbstractAttributeDeterminer> determiners;
    @Setter
    private Integer returnCount = 1;
    @Setter
    private Double minScore = -100000000.0;

    public List<Resource> computer(List<Resource> resources, RuleSet ruleSet) {
        SortedMap<Double, List<Resource>> scoredResources;
        scoredResources = new TreeMap<>(Comparator.reverseOrder());

        for(Resource resource:resources) {
            Double score = 0.0;
            for (AbstractAttributeDeterminer determiner : determiners) {
                score += determiner.getAttributeFactor() *
                        (determiner.determine(resource, ruleSet) + determiner.getBaseScore());
            }

            List<Resource> tmp = scoredResources.computeIfAbsent(score, k -> new ArrayList<>());
            tmp.add(resource);
        }

        Integer count = returnCount;
        List<Resource> result = new ArrayList<Resource>();
        for(SortedMap.Entry<Double,List<Resource>> entry:scoredResources.entrySet()) {
            if(count > 1 && entry.getKey() > minScore) {
                result.addAll(entry.getValue());
                count -= entry.getValue().size();
            } else {
                break;
            }
        }

        // if count < 0, remove | count | elements
        return result.subList(0, result.size() - 1 + count);
    }
}
