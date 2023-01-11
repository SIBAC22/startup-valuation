package org.sibac.bayes.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.rule.FactHandle;
import org.sibac.bayes.listeners.RuleType;
import org.sibac.bayes.listeners.TrackingAgendaListener;

public class FactBayes implements Comparable<FactBayes>, Uncertainty {
	private double probability;
	private String description;
	private String value;
	private Double doubleValue;

	public static void update(Class c, String description, String value) {
		Collection<Object> myfacts = (Collection<Object>) TrackingAgendaListener.getKieSession().getObjects( new ClassObjectFilter(c) );
		Iterator<Object> iterator = myfacts.iterator();
		while (iterator.hasNext()) {
			FactBayes fact = (FactBayes)iterator.next();
			String factDesc = fact.getDescription();
			String factVal = fact.getValue();
			if (factDesc.compareTo(description) == 0 && factVal.compareTo(value) == 0) {
				// update fact
				fact.update();
				return;
			}
		}
	}

	public FactBayes(double probability, String description, String value) {
		super();
		this.probability = probability;
		this.description = description;
		this.value = value;
	}

	public FactBayes() {
		this(0, "", "");
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return this.value;
	}
	public Double getDouble() {
		return this.doubleValue;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int compareTo(FactBayes f) {
		if(this.getProbability() < f.getProbability()) {
			return -1;
		}
		else if(this.getProbability() > f.getProbability()) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return this.getClass().getName() +
				"[Probability=" + probability + " ; " +
				"description=" + description + " ; " + "Value=" + value + "]";
	}

	@Override
	public void update() {
		Map<String, Double> supportFactors = null;
		double newProbability = 0;

		// get conclusion fact handle
		FactHandle fHandle = TrackingAgendaListener.getKieSession().getFactHandle(this);

		// get map with LHS evidences' probabilities
		List<Double> lhsProbabilities = TrackingAgendaListener.getLHSprobabilities();

		if (TrackingAgendaListener.getRuleType() == RuleType.PROBABILISTIC) {
			// get map with LS and LN values
			try {
				supportFactors = TrackingAgendaListener.getSupportFactors();
			}
			catch(Exception E) {
				System.out.println(E);
				System.exit(0);
			}

			// adjust LS and LN values
			for (int i = 1; i <= lhsProbabilities.size(); i++) {
				if (lhsProbabilities.get(i-1) < 1.0 && lhsProbabilities.get(i-1) >= 0.5) {
					// adjust LSi
					supportFactors.put("LS" + i, getAdjustedLS(lhsProbabilities.get(i-1), supportFactors.get("LS" + i)));
				}
				if (lhsProbabilities.get(i-1) >= 0.0 && lhsProbabilities.get(i-1) < 0.5) {
					// adjust LNi
					supportFactors.put("LN" + i, getAdjustedLN(lhsProbabilities.get(i-1), supportFactors.get("LN" + i)));
				}
			}

			// calculate new conclusion's probability value
			double odd = prob2odd(getProbability());
			for (int i = 1; i <= lhsProbabilities.size(); i++) {
				if (lhsProbabilities.get(i-1) >= 0.5) {
					odd = odd * supportFactors.get("LS" + i);
				} else {
					odd = odd * supportFactors.get("LN" + i);
				}
			}
			newProbability = odd2prob(odd);

		} else if (TrackingAgendaListener.getRuleType() == RuleType.DETERMINISTIC) {
			// calculate new conclusion's probability value
			newProbability = 1;
			for (int i = 1; i <= lhsProbabilities.size(); i++) {
				newProbability = newProbability * lhsProbabilities.get(i-1);
			}
		}

		// update conclusion's probability value
		this.setProbability(newProbability);
		TrackingAgendaListener.getKieSession().update(fHandle, this);

	}

	private double getAdjustedLS(double prob, double ls) {
		return (2 * (ls-1) * prob) + 2 - ls;
	}

	private double getAdjustedLN(double prob, double ln) {
		return (2 * (1 - ln) * prob) + ln;
	}

	//Probability 1.0 can not be substituted so we are using 99.99 instead by the limits of 1.0 approximated by 0.99...
	private double prob2odd(double prob) { return prob == 1.0 ? 99.99 : prob / (1 - prob); }

	private double odd2prob(double odd) {
		return odd / (odd + 1);
	}

}
