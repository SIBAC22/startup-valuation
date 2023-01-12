package org.sibac.bayes.model;

public class Conclusion extends FactBayes{
    private String description;
    private Double scoreCount;

    public Conclusion(String description) {
        super();
        this.description = description;
    }

    public Conclusion(String description, Double scoreCount) {
        super();
        this.description = description;
        this.scoreCount = scoreCount;
    }

    public String getDescription() {
        return description;
    }
    public Double getCount() {
        return scoreCount;
    }

    public String toString() {
        return ("Conclusion: " + description + " " + scoreCount);
    }
}
