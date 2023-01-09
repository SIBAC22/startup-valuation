package org.sibac.bayes.model;

public class Conclusion extends FactBayes{
    public static final String FATALPIPES = "Fatal blow up";
    private String description;
    private double score;

    public Conclusion(String description) {
        super();
        this.description = description;
    }

    public Conclusion(int trl) {
        this.score = (trl/9) *0.22;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return ("Conclusion: " + description);
    }

}
