package org.sibac.bayes.model;

public class Evidence extends FactBayes{

	public Evidence(double probability, String description, String value) {
		super(probability, description, value);
	}
	
	public String toString() {
		return super.toString();
	}
}
