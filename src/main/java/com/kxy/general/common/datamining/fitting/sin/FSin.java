package com.kxy.general.common.datamining.fitting.sin;

import lombok.Getter;
import lombok.Setter;

/**
 * function sin.
 * Created by kongxiangyun on 1/28/18.
 */
@Getter @Setter
public class FSin {
    /**
     * a of function f(x) = a*sin(bx+c)+d.
     */
    private double a;
    /**
     * b of function f(x) = a*sin(bx+c)+d.
     */
    private double b;
    /**
     * c of function f(x) = a*sin(bx+c)+d.
     */
    private double c;
    /**
     * d of function f(x) = a*sin(bx+c)+d.
     */
    private double d;

    /**
     * init construction.
     * @param a a
     * @param b b
     * @param c c
     * @param d d
     */
    public FSin(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * calculate the result of a*sin(bx+c)+d.
     * @param x value of x
     * @return a*sin(bx+c)+d
     */
    public double f(double x) {
        return a * Math.sin(b * x + c) + d;
    }

    public void print() {
        System.out.print("" + a + "sin(" + b + "x+" + c + ")+" + d);
    }
}
