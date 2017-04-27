package com.kxy.general.beta.algorithm.determiner;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public abstract class AbstractAttributeDeterminer
        implements AttributeDeterminer {

    /**
     * the final score = attributeFactor * computer() + baseScore.
     */
    @Getter
    @Setter
    private Double baseScore;

    /**
     * the final score = attributeFactor * computer() + baseScore.
     */
    @Getter
    @Setter
    private Double attributeFactor;
}
