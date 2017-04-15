package com.kxy.general.ß.algorithm.determiner;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public abstract class AbstractAttributeDeterminer implements AttributeDeterminer {
    @Getter
    @Setter
    private Double baseScore;

    @Getter
    @Setter
    private Double attributeFactor;
}
