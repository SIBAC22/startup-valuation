package org.sibac.bayes.model;

public class FrameTeam extends Evidence {

    private static final String YES = "yes";

    public FrameTeam(double probability, String description, String value) {
        super(probability, description, value);
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
