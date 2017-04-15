package com.kxy.general.ß.rule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
@Setter
@Getter
public class RuleSet implements Serializable {
    private static final long serialVersionUID = -7474298614082423988L;

    private List<Rule> rules;
}
