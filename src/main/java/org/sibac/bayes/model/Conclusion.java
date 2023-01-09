package org.sibac.bayes.model;

public class Conclusion extends FactBayes{
    public static final String FATALPIPES = "Fatal blow up";
    private String description;

    public Conclusion(String description) {
        super();
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return ("Conclusion: " + description);
    }

}
