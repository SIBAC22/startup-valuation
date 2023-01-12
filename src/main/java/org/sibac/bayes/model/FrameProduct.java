package org.sibac.bayes.model;

public class FrameProduct extends Evidence {

    private double score;
    public FrameProduct(double probability, String description, String value) {
        super(probability, description, value);
        this.score = Float.parseFloat(value)/9 * 0.22;
    }

    public double getScore() {
        return score;
    }

    public String getExplanation(double score) {
        return "You current success probability based on TRL is " + score * 100 + "%.";
    }
}
