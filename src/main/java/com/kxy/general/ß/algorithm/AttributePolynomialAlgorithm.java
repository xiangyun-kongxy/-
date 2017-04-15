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
        scoredResources = new TreeMap<Double, List<Resource>>(new Comparator<Double>() {
            public int compare(Double o1, Double o2) {
                return o2.compareTo(o1);
            }
        });

        for(Resource resource:resources) {
            Double score = 0.0;
            for (AbstractAttributeDeterminer determiner : determiners) {
                score += determiner.getAttributeFactor() *
                        (determiner.determine(resource, ruleSet) + determiner.getBaseScore());
            }

            List<Resource> tmp = scoredResources.get(score);
            if(tmp == null) {
                tmp = new ArrayList<Resource>();
                scoredResources.put(score, tmp);
            }
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

        return result;
    }
}
