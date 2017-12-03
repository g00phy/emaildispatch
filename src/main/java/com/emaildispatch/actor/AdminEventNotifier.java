package com.emaildispatch.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.emaildispatch.message.EmailDetails;
import com.emaildispatch.message.NotifyEvent;
import com.emaildispatch.service.EmailUtil;

import java.util.Collections;

public class AdminEventNotifier extends AbstractActor {
	private final EmailUtil emailUtil;

	@Override
	public Receive createReceive() {
		return receiveBuilder().
				match(NotifyEvent.class,this::notifyAdmin).
				build();
	}

	private void notifyAdmin(NotifyEvent notifyEvent) {
		emailUtil.sendEmail((new EmailDetails(notifyEvent.getEmailAddress(),
				notifyEvent.getSubject(),
				notifyEvent.getBody(),
				Collections.emptySet(),
				Collections.emptySet())));
	}



	public static Props  props(EmailUtil emailUtil){
		return Props.create(AdminEventNotifier.class, () -> new AdminEventNotifier(emailUtil));
	}

	private AdminEventNotifier(EmailUtil emailUtil) {
		this.emailUtil = emailUtil;
	}

}