package com.emaildispatch.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Creator;
import akka.persistence.AbstractPersistentActor;
import akka.persistence.SnapshotOffer;
import com.emaildispatch.message.Cmd;
import com.emaildispatch.message.Evt;
import com.emaildispatch.message.NotifyEvent;
import com.emaildispatch.model.EmailState;

import java.util.HashSet;

public class PersistStatisticsActor extends AbstractPersistentActor {
	private final ActorRef emailDispatcher;
	private EmailState state = new EmailState();
	private final int snapShotInterval = 1000;


	public static Props props(ActorRef emailDispatcher){
		return Props.create((Creator<PersistStatisticsActor>) () -> new PersistStatisticsActor(emailDispatcher));
	}

	private PersistStatisticsActor(ActorRef emailDispatcher) {
		this.emailDispatcher = emailDispatcher;

	}

	@Override
	public String persistenceId() { return "sample-id-1"; }

	@Override
	public Receive createReceiveRecover() {
		return receiveBuilder()
				.match(Evt.class, state::update)
				.match(String.class, message -> notify(message))
				.match(SnapshotOffer.class, ss -> state = (EmailState) ss.snapshot())
				.build();
	}

	private void notify(String message) {
		emailDispatcher.tell(new NotifyEvent(new HashSet<String>(){{add("admin@papercut.com");}},"statistics",state.toString()),ActorRef.noSender());
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Cmd.class, c -> {
					final Evt evt = new Evt(c.getTimestamp(), c.getCount());
					persist(evt, (Evt e) -> {
						state.update(e);
						getContext().getSystem().eventStream().publish(e);
						if (lastSequenceNr() % snapShotInterval == 0 && lastSequenceNr() != 0)
							// IMPORTANT: create a copy of snapshot because EmailState is mutable
							saveSnapshot(state.copy());
					});
				})
				.matchEquals("notifyadmin", message -> notify())
				.matchEquals("print", s -> System.out.println(state))

				.build();
	}


}