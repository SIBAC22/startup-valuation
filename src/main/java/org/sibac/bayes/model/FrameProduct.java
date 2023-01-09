package org.sibac.bayes.model;

public class FrameProduct extends Evidence {

    private double score;
    public FrameProduct(double probability, String description, String value) {
        super(probability, description, value);
        this.score = calculateTRL(value);
    }

    public double calculateTRL(String value) {
        return Float.parseFloat(value)/9 * 0.22;
    }

    public double getScore() {
        return score;
    }
}
