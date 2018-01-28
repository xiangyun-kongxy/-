package com.kxy.general.common.datamining.fitting.sin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * f(x) = sum(FSin)
 * Created by kongxiangyun on 1/28/18.
 */
@Getter @Setter
public class SinFit {
    /**
     * FSin list.
     */
    private List<FSin> function;

    /**
     * init construction.
     * @param function FSins
     */
    public SinFit(List<FSin> function) {
        this.function = function;
    }

    /**
     * calculate the result of sum(FSin).
     * @param cond value of x
     * @return sum(FSin)
     */
    public double apply(double cond) {
        double result = 0.0;
        for (FSin sin : function) {
            result += sin.f(cond);
        }
        return result;
    }

    public void print() {
        for (FSin sin : function) {
            sin.print();
            System.out.print(" + ");
        }
    }
}
