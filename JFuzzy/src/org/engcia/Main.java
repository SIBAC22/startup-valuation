package org.engcia;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.LinguisticTerm;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import net.sourceforge.jFuzzyLogic.rule.Rule;

import java.util.HashMap;

//import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

public class Main {

    public static void main(String[] args) {
        // Load from 'FCL' file
        String fileName = "src/fcl/prescricao.fcl";
        FIS fis = FIS.load(fileName,true);

        // Error while loading?
        if( fis == null ) {
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }

        // Show
        JFuzzyChart.get().chart(fis);

        // Set inputs
        fis.setVariable("psa", 3);
        fis.setVariable("sd", 50);

        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        Variable sl = fis.getVariable("sl");
        JFuzzyChart.get().chart(sl, sl.getDefuzzifier(), true);

        // Show output variable value
        System.out.println("Output value: " + sl.defuzzify());

        // Print ruleSet
        //System.out.println(fis);

        // Show membership values for input variables
        System.out.println("Fuzzyfication:");
        Variable v;
        HashMap<String, LinguisticTerm> linguisticTerms;
        v = fis.getFunctionBlock("prescricao").getVariable("psa");
        System.out.println(v.getName());
        linguisticTerms = v.getLinguisticTerms();
        for( String linguisticTerm: linguisticTerms.keySet()) {
            System.out.println("\t" + linguisticTerm + " : " + v.getMembership(linguisticTerm));
        }
        v = fis.getFunctionBlock("prescricao").getVariable("sd");
        System.out.println(v.getName());
        linguisticTerms = v.getLinguisticTerms();
        for( String linguisticTerm: linguisticTerms.keySet()) {
            System.out.println("\t" + linguisticTerm + " : " + v.getMembership(linguisticTerm));
        }

        // Show each rule (and degree of support)
        System.out.println("Inference:");
        for(Rule r: fis.getFunctionBlock("prescricao").getFuzzyRuleBlock("No1").getRules() ) {
            System.out.println(r);
        }
    }
}
