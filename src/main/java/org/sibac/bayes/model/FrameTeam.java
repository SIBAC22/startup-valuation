package org.sibac.bayes.model;


import java.util.ArrayList;
import java.util.List;

public class FrameTeam extends Evidence {

    private static final String YES = "yes";
    private static final String NO = "no";


    public FrameTeam(double probability, String description, String value) {
        super(probability, description, value);
    }

    public List<String> getExplanation(String technicalSkills, String analyticalSkills, String marketingSkills, String goals, String experience, String expertise) {
        List<String> explanation = new ArrayList<>(getSkillsExplanation(technicalSkills, analyticalSkills, marketingSkills));

        if (goals.equals(YES) && experience.equals(YES) && expertise.equals(YES)) explanation.add("Your team is well rounded.");
        else if (goals.equals(YES) && experience.equals(YES) && expertise.equals(NO)) explanation.add("It's good that you have drive and working experience but you should consider expertise in the field a bit more.");
        else if (goals.equals(YES) && experience.equals(NO) && expertise.equals(NO)) explanation.add("It's good that you have drive but you should consider experience and expertise in the field.");
        else if (goals.equals(NO) && experience.equals(YES) && expertise.equals(NO)) explanation.add("You should revise if your goals are aligned with company visions and should consider expertise in the field.");
        else if (goals.equals(NO) && experience.equals(NO) && expertise.equals(YES)) explanation.add("You should revise if your goals are aligned with company visions and should consider experience and expertise in the field.");
        else if (goals.equals(NO) && experience.equals(YES) && expertise.equals(YES)) explanation.add("You should revise if your goals are aligned with company visions.");

        else if(explanation.isEmpty()) explanation.add("Your team is not prepared for what you have in mind.");

        return explanation;
    }

    private List<String> getSkillsExplanation(String technicalSkills, String analyticalSkills, String marketingSkills) {
        List<String> explanation = new ArrayList<>();
        if (technicalSkills.equals(YES) && analyticalSkills.equals(YES) && marketingSkills.equals(YES)) explanation.add("You have all the necessary skills you need for success.");
        else if (technicalSkills.equals(YES) && analyticalSkills.equals(YES)) explanation.add("You should consider expanding your Marketing skills.");
        else if (technicalSkills.equals(YES) && marketingSkills.equals(YES)) explanation.add("You should consider expanding your Analytical skills.");
        else if (analyticalSkills.equals(YES) && marketingSkills.equals(YES)) explanation.add("You should consider expanding your Technical skills.");
        else if (technicalSkills.equals(YES)) explanation.add("You should consider expanding your Marketing and Analytical skills.");
        else if (analyticalSkills.equals(YES)) explanation.add("You should consider expanding your Marketing and Technical skills.");
        else if (marketingSkills.equals(YES)) explanation.add("You should consider expanding your Technical and Analytical skills.");

        return explanation;
    }


    public double calculateTeamScore(String technicalSkills, String analyticalSkills, String marketingSkills, String goals, String experience, String expertise) {
        double score = calculateTeamSkills(technicalSkills, analyticalSkills, marketingSkills);
        if(goals.equals(YES)) score += 0.05;
        if(experience.equals(YES)) score += 0.05;
        if(expertise.equals(YES)) score += 0.05;

        return score;
    }

    private double calculateTeamSkills(String technicalSkills, String analyticalSkills, String marketingSkills) {
        if (technicalSkills.equals(YES) && analyticalSkills.equals(YES) && marketingSkills.equals(YES)) return 0.15;
        else if (technicalSkills.equals(YES) && analyticalSkills.equals(YES)) return 0.07;
        else if (technicalSkills.equals(YES) && marketingSkills.equals(YES)) return 0.12;
        else if (analyticalSkills.equals(YES) && marketingSkills.equals(YES)) return 0.09;
        else if (technicalSkills.equals(YES)) return 0.04;
        else if (analyticalSkills.equals(YES)) return 0.03;
        else if (marketingSkills.equals(YES)) return 0.06;
        else return 0;
    }
}