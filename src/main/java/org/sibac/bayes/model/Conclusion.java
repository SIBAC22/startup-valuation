package org.sibac.bayes.model;

import java.text.DecimalFormat;
import java.util.*;

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

    public List<String> addExplanationToList(List<String> previousList, String explanation) {
        List<String> copy = new ArrayList<>(previousList);
        copy.add(explanation);
        return copy;
    }

    public List<String> addExplanationListToList(List<String> previousList, List<String> explanation) {
        List<String> copy = new ArrayList<>(previousList);
        copy.addAll(explanation);
        return copy;
    }

    public String setBusinessNeedsNoExplanation(double probability){
        return "You current succcess probability based on TRL is " + FORMAT.format(probability * 100) + "%. Try identifying what your business needs and try again!";
    }

    public String setTractionExplanation(double ltv, double interest, double probability){
        return "Your input was LTV = " + ltv + " and Interest/Engagement Rate = " + interest + ". " + "Success probability is " + FORMAT.format(probability * 100) + "%. " + "The probability of market traction is composed of the conditional probability of P(Traction|LTV) and P(Traction|Interest). P(Traction|Interest) has a higher impact on the result than P(Traction|LTV). Imagine that you have a product that has an LTV of 1$, you could say your LTV is not very good, however, if you have 10 million people buying from you, your traction is good. The contrary can be said of a business model with an LTV of 10m $ but only one interested buyer, this is not a very good traction, because your business depends on a sole buyer, your risk is increased. If you have middling values, the traction performance is also not very good, as you are average in respect to the industry, so there is no competitive advantage.";
    }

    public String setProtectYesExplanation(double probability) {
        return "You seem to have thoughts about patents or other forms of intellectual property protection. Success probability is " + FORMAT.format(probability * 100) + "%. ";
    }

    public String setOpportunityExplanation(double probability, double samRatio, double tamRatio) {
        return "Your total accumulated probability of success is " + FORMAT.format(probability * 100) + "%. Your SAM : Money to Market ratio is " + samRatio + ". A ratio above 10 is considered good, as the ROI is the short term is 10x. Your TAM : SAM ratio is " + tamRatio + ". This ratio only serves as weight to the final output probability of your market opportunity. A ratio of above 10 will increase your chances if you scored a low probability in the SAM/Money ratio, as the promise of future opportunity (TAM) is good.";
    }

}
