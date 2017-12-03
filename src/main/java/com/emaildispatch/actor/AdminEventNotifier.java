package com.emaildispatch.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.japi.Creator;
import akka.persistence.AbstractPersistentActor;
import akka.persistence.SnapshotOffer;
import com.emaildispatch.message.Cmd;
import com.emaildispatch.message.EmailDetails;
import com.emaildispatch.message.Evt;
import com.emaildispatch.message.NotifyEvent;
import com.emaildispatch.model.EmailCSVBean;
import com.emaildispatch.model.EmailState;
import com.emaildispatch.service.EmailUtil;
import scala.concurrent.duration.Duration;

import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

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
		return Props.create(new Creator<AdminEventNotifier>() {
			@Override
			public AdminEventNotifier create() throws Exception {
				return new AdminEventNotifier(emailUtil);
			}
		});
	}

	public AdminEventNotifier(EmailUtil emailUtil) {
		this.emailUtil = emailUtil;
	}

}