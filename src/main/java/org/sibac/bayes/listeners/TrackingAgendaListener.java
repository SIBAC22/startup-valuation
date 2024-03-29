package org.sibac.bayes.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.sibac.bayes.model.FactBayes;

public class TrackingAgendaListener implements AgendaEventListener {

	private static KieSession kieSession;
	private static List<Object> activations;
	private static String ruleName;
	private static Map<String, Object> metaData;
	private static RuleType rType;

	static public KieSession getKieSession() {
		return kieSession;
	}

	static public RuleType getRuleType() {
		return rType;
	}

	static public List<Double> getLHSProbabilities() {
		ArrayList<Double> probabilityList = new ArrayList<>();

		IntStream.range(0, activations.size()).forEach(index -> {
			FactBayes fact = (FactBayes)activations.get(index);
			probabilityList.add(fact.getProbability());
		});

		return probabilityList;
	}

	static public Map<String, Double> getSupportFactors() throws Exception {

		if (TrackingAgendaListener.activations.size() * 2 != metaData.size()) {
			throw new Exception(ruleName + ": " + "Number of pairs LS,LN different from number of premises in LHS");
		}

		Map<String, Double> supportFactors = new HashMap<>();
		if (TrackingAgendaListener.rType == RuleType.DETERMINISTIC) {
			return supportFactors;
		}

		for (int i=1; i <= TrackingAgendaListener.activations.size(); i++) {
			if (metaData.containsKey("LS" + i)) {
				supportFactors.put("LS" + i, (Double)metaData.get("LS" + i));
			} else {
				throw new Exception(ruleName + ": " + "LS" + i + " factor not defined");
			}
			if (metaData.containsKey("LN" + i)) {
				supportFactors.put("LN" + i, (Double)metaData.get("LN" + i));
			} else {
				throw new Exception(ruleName + ": " + "LN" + i + " factor not defined");
			}
		}

		return supportFactors;
	}

	static public FactBayes getFactRef(Class<?> c, String description, String value) {
		Collection<FactBayes> facts = (Collection<FactBayes>) kieSession.getObjects( new ClassObjectFilter(c) );
		Iterator<FactBayes> iterator = facts.iterator();
		while (iterator.hasNext()) {
			FactBayes fact = iterator.next();
			if (fact.getDescription().compareTo(description) == 0 && fact.getValue().compareTo(value) == 0) {
				return fact;
			}
		}
		return null;
	}

	public TrackingAgendaListener() {
		super();
		TrackingAgendaListener.kieSession = null;
		TrackingAgendaListener.activations = new ArrayList<>();
		TrackingAgendaListener.ruleName = null;
		TrackingAgendaListener.metaData = new HashMap<>();
	}

	@Override
	public void afterMatchFired(AfterMatchFiredEvent event) { TrackingAgendaListener.activations.clear(); }

	@Override
	public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) { }

	@Override
	public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) { }

	@Override
	public void agendaGroupPopped(AgendaGroupPoppedEvent event) { }

	@Override
	public void agendaGroupPushed(AgendaGroupPushedEvent event) { }

	@Override
	public void beforeMatchFired(BeforeMatchFiredEvent event) {
		TrackingAgendaListener.kieSession = (KieSession) event.getKieRuntime().getKieBase().getKieSessions()
				.toArray()[0];
		TrackingAgendaListener.activations.addAll(event.getMatch().getObjects());
		TrackingAgendaListener.ruleName = event.getMatch().getRule().getName();
		TrackingAgendaListener.metaData = event.getMatch().getRule().getMetaData();
		if (metaData.size() == 0) {
			TrackingAgendaListener.rType = RuleType.DETERMINISTIC;
		} else {
			TrackingAgendaListener.rType = RuleType.PROBABILISTIC;
		}
	}

	@Override
	public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) { }

	@Override
	public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) { }

	@Override
	public void matchCancelled(MatchCancelledEvent event) { }

	@Override
	public void matchCreated(MatchCreatedEvent event) { }

}