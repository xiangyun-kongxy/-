package com.kxy.general.beta.rule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class RuleSet implements Serializable {
    private static final long serialVersionUID = -7474298614082423988L;

    /**
     * Rules the RuleSet contains.
     */
    @Getter @Setter
    private List<Rule> rules = new ArrayList<>();

    /**
     * add rule.
     * @param rule new rule
     */
    public void addRule(Rule rule) {
        rules.add(rule);
    }
}
