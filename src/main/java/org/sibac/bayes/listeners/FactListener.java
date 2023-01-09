package org.sibac.bayes.listeners;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
//import org.kie.api.runtime.rule.FactHandle;

public class FactListener implements RuleRuntimeEventListener {

	@Override
	public void objectDeleted(ObjectDeletedEvent event) {
		// FactHandle fact = event.getFactHandle();
		System.out.println(
						"Retracted: " +
						//fact.toString() +
						event.getOldObject().toString()
						);
	}

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		// FactHandle fact = event.getFactHandle();
		System.out.println(
						"Asserted: " +
						//fact.toString() + 
						event.getObject().toString()
						);
	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		
		System.out.println(
				"Updated: " +
				event.getObject().toString()
				);
		
	}

}
