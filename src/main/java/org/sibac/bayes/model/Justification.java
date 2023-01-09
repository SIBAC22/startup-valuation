package org.sibac.bayes.model;

import java.util.ArrayList;
import java.util.List;

public class Justification {
    private String rule;
    private List<FactBayes> lhs;
    private FactBayes conclusion;

    public Justification(String rule, List<FactBayes> lhs, FactBayes conclusion) {
        this.rule = rule;
        this.lhs = new ArrayList<FactBayes>(lhs);
        this.conclusion = conclusion;
    }

    public String getRuleName() {
        return this.rule;
    }

    public List<FactBayes> getLhs() {
        return this.lhs;
    }

    public FactBayes getConclusion() {
        return this.conclusion;
    }
}
