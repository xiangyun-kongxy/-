package com.kxy.general.beta.algorithm;

import com.kxy.general.beta.algorithm.determiner.AbstractAttributeDeterminer;
import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.rule.RuleSet;
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
    /**
     * the default min score.
     */
    private static final Double DEFAULT_MIN_SCORE = -100000000.0;

    /**
     * processors will be used in computing.
     */
    @Setter
    private List<AbstractAttributeDeterminer> determiners;

    /**
     * how may(max) resources should be return in a computing.
     */
    @Setter
    private Integer returnCount = 1;

    /**
     * what score a resource get can be returned.
     */
    @Setter
    private Double minScore = DEFAULT_MIN_SCORE;

    /**
     * compute resources' score using configured processor
     * <code>determiners</code> and return resources whose score exceeds
     * <code>minScore</code>.
     * @param resources candidates to compute
     * @param ruleSet the rules to selectors
     * @return resources whose score exceeds <code>minScore</code>
     */
    public List<Resource> computer(List<Resource> resources, RuleSet ruleSet) {
        SortedMap<Double, List<Resource>> scoredResources;
        scoredResources = new TreeMap<>(Comparator.reverseOrder());

        for (Resource resource:resources) {
            Double score = 0.0;
            for (AbstractAttributeDeterminer determiner : determiners) {
                score += determiner.getAttributeFactor()
                        * (determiner.determine(resource, ruleSet)
                        + determiner.getBaseScore());
            }

            List<Resource> tmp = scoredResources.computeIfAbsent(score,
                    k -> new ArrayList<>());
            tmp.add(resource);
        }

        Integer count = returnCount;
        List<Resource> result = new ArrayList<>();
        for (SortedMap.Entry<Double, List<Resource>> entry
                : scoredResources.entrySet()) {
            if (count > 1 && entry.getKey() > minScore) {
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
