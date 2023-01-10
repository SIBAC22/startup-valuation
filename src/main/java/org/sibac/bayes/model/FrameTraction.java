package org.sibac.bayes.model;

public class FrameTraction extends Evidence {

    public FrameTraction(double probability, String description, String value) {
        super(probability, description, value);
    }

    public double calculateLTV(int averageValue, int numberOfTransactions, int timePeriod) {
        return averageValue * numberOfTransactions * timePeriod;
    }
}
