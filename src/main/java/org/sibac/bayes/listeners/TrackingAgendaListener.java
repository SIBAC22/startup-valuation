package org.sibac.bayes.listeners;

import org.sibac.bayes.model.FactBayes;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;

import java.util.*;


public class TrackingAgendaListener implements AgendaEventListener {
	
	private static KieSession kieSession;
	private static List<Object> activations;
	private static String ruleName;
	private static Map<String, Object> metaData;
	private static RuleType rType;

	//From TrackingAgendaEventListener
	private List<FactBayes> lhs = new ArrayList<>();
	private List<FactBayes> rhs = new ArrayList<>();

	public void resetLhs() {
		lhs.clear();
	}

	public void addLhs(FactBayes f) {
		lhs.add(f);
	}

	public void resetRhs() {
		rhs.clear();
	}

	public void addRhs(FactBayes f) {
		rhs.add(f);
	}


	static public KieSession getKieSession() {
		return kieSession;
	}
	
	static public List<Object> getActivations() {
		return activations;
	}
	
	static public String getRuleName() {
		return ruleName;
	}
	
	static public Map<String, Object> getMetaData() {
		return metaData;
	}
	
	static public RuleType getRuleType() {
		return rType;
	}
	
	static public List<Double> getLHSprobabilities() {
		ArrayList<Double> probabilityList = new ArrayList<>();
		for(int i=0; i<activations.size(); i++) {
			FactBayes fact = (FactBayes)activations.get(i);
			probabilityList.add(fact.getProbability());
		}
		return probabilityList;
	}
	
	static public Map<String, Double> getSupportFactors() throws Exception {
		Map<String, Double> supportFactors = new HashMap<>();
		
		if (TrackingAgendaListener.rType == RuleType.DETERMINISTIC) {
			return supportFactors;
		}
		
		if (TrackingAgendaListener.activations.size() * 2 != metaData.size()) {
			throw new Exception(ruleName + ": " + "Number of pairs LS,LN diferent from number of premises in LHS");
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
		Collection<FactBayes> myfacts = (Collection<FactBayes>) kieSession.getObjects( new ClassObjectFilter(c) );
		Iterator<FactBayes> iterator = myfacts.iterator();
		while (iterator.hasNext()) {
			FactBayes fact = iterator.next();
			String factDesc = fact.getDescription();
			String factVal = fact.getValue();
			if (factDesc.compareTo(description) == 0 && factVal.compareTo(value) == 0) {
				return fact;
			}
		}
		return null;
	}

	public TrackingAgendaListener() {
		super();
		TrackingAgendaListener.kieSession = null;
		TrackingAgendaListener.activations = new ArrayList<Object>();
		TrackingAgendaListener.ruleName = null;
		TrackingAgendaListener.metaData = new HashMap<String, Object>(); 
	}

	@Override
	public void afterMatchFired(AfterMatchFiredEvent event) {
		// Clear activation object list to the next activation
		TrackingAgendaListener.activations.clear();
	}

	@Override
	public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void agendaGroupPushed(AgendaGroupPushedEvent event) {
		// TODO Auto-generated method stub

	}

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
	public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void matchCancelled(MatchCancelledEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void matchCreated(MatchCreatedEvent event) {
		// TODO Auto-generated method stub

	}
	
}
