package org.sibac.bayes.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conclusion extends FactBayes{
    private static final DecimalFormat FORMAT = new DecimalFormat("0.00");
    private String description;
    private Double scoreCount;
    private List<String> explanation = Collections.emptyList();

    public Conclusion(String description) {
        super();
        this.description = description;
    }

    public Conclusion(String description, Double scoreCount) {
        super();
        this.description = description;
        this.scoreCount = scoreCount;
    }

    public Conclusion(String description, Double scoreCount, List<String> explanation) {
        super();
        this.description = description;
        this.scoreCount = scoreCount;
        this.explanation = explanation;
    }

    public String getDescription() {
        return description;
    }
    public Double getCount() {
        return scoreCount;
    }

    public String toString() {
        return ("Conclusion: " + description + " " + scoreCount + " " + explanation);
    }

    public List<String> getExplanation() {
        return explanation;
    }

    public void setExplanation(List<String> explanation) {
        this.explanation = explanation;
    }

    public List<String> addExplanationToList(List<String> list, String explanation) {
        List<String> list1 = new ArrayList<>(list);
        list1.add(explanation);
        return list1;
    }

    public String getTractionExplanation(double ltv, double interest, double probability){
        return "Your input was LTV = " + ltv + " and Interest/Engagement Rate = " + interest + ". " + "Success probability is " + FORMAT.format(probability * 100) + "%. " + "The probability of market traction is composed of the conditional probability of P(Traction|LTV) and P(Traction|Interest). P(Traction|Interest) has a higher impact on the result than P(Traction|LTV). Imagine that you have a product that has an LTV of 1$, you could say your LTV is not very good, however, if you have 10 million people buying from you, your traction is good. The contrary can be said of a business model with an LTV of 10m $ but only one interested buyer, this is not a very good traction, because your business depends on a sole buyer, your risk is increased. If you have middling values, the traction performance is also not very good, as you are average in respect to the industry, so there is no competitive advantage.";
    }

    public String getProtectYesExplanation(double probability) {
        return "You seem to have thoughts about patents or other forms of intellectual property protection. Success probability is " + FORMAT.format(probability * 100) + "%. ";
    }
}
