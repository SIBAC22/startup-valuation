package org.sibac.bayes;

//import org.drools.core.rule.builder.dialect.asm.ClassGenerator;
import org.kie.api.KieServices;
//import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.sibac.bayes.listeners.FactListener;
import org.sibac.bayes.listeners.TrackingAgendaListener;
import org.sibac.bayes.model.Hypothesis;
import org.sibac.bayes.model.Evidence;


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

			kSession.insert(new Evidence(0.0, "nivel_agua", "baixo"));
			kSession.insert(new Evidence(1.0, "luz_aviso", "ligada"));
			kSession.insert(new Evidence(1.0, "temperatura", "alta"));

			kSession.insert(new Hypothesis(0.02, "valvula_escape", "bloqueada"));
			kSession.insert(new Hypothesis(0.1, "pressao", "alta"));
			kSession.insert(new Hypothesis(0.5, "limpar_valvula_escape", "verdadeiro"));

			kSession.fireAllRules();

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}