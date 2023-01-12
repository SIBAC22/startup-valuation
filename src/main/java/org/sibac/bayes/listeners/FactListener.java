package org.sibac.bayes.listeners;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;

public class FactListener implements RuleRuntimeEventListener {

	//TODO Don't forget to delete System.out.println
	@Override
	public void objectDeleted(ObjectDeletedEvent event) {
	}

	@Override
	public void objectInserted(ObjectInsertedEvent event) {

	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {

	}

}
