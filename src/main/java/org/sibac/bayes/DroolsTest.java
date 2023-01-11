package org.sibac.bayes;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.sibac.bayes.listeners.FactListener;
import org.sibac.bayes.listeners.TrackingAgendaListener;
import org.sibac.bayes.model.*;


/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

	public static final void main(String[] args) {
		try {
			// load up the knowledge base
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			KieSession kSession = kContainer.newKieSession("ksession-rules");

			// Agenda listener
			kSession.addEventListener(new TrackingAgendaListener());

			// Facts listener
			kSession.addEventListener(new FactListener());

			// go !
			kSession.insert(new Evidence(1.0, "Do you know what your business needs?", "yes"));
			kSession.insert(new FrameProduct(1.0,"What is your TRL? [value 1-4]", "4" ));

			kSession.insert(new Evidence(1.0, "Do you have the technical skills?", "yes"));
			kSession.insert(new Evidence(1.0, "Do you have the analytical skills?", "yes"));
			kSession.insert(new Evidence(1.0, "Do you have the marketing skills?", "no"));
			kSession.insert(new Evidence(1.0, "Do you have previous experience as a team?", "yes"));
			kSession.insert(new Evidence(1.0, "Are your personal goals aligned with the start-up vision?", "yes"));
			kSession.insert(new Evidence(1.0, "Do your team or advisors have industry expertise?", "no"));

			kSession.insert(new FrameProduct(1.0,"What is your TRL? [value 1-9]", "9" ));

			//this can be either yes or no
			kSession.insert(new Evidence(1.0, "Do you have competitors?", "yes"));

			kSession.insert(new Evidence(1.0, "From 1 to 10, how good is your LTV?", "9"));
			kSession.insert(new Evidence(1.0, "From 1 to 10, how good is your engagement rate?", "10"));


			kSession.insert(new Evidence(1.0, "Can you protect your technology?", "yes"));

			kSession.insert(new Evidence(1.0, "What’s your SAM within 5 years?", "10"));
			kSession.insert(new Evidence(1.0, "How much money do you need to enter a market?", "1"));
			kSession.insert(new Evidence(1.0, "What’s your TAM within 5 years?", "10"));

			kSession.fireAllRules();

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}