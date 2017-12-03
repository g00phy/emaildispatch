package com.emaildispatch.actor;


import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import com.emaildispatch.message.Cmd;
import com.emaildispatch.message.EmailDetails;
import com.emaildispatch.message.NotifyEvent;
import com.emaildispatch.model.EmailCSVBean;
import com.emaildispatch.service.EmailUtil;
import scala.concurrent.duration.Duration;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class EmailDispatcher extends AbstractActor{
    private final EmailUtil emailUtil;
    private final ActorRef persistStatisticsActor;
    private final Cancellable cancellable;

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(EmailDetails.class,this::dispatchEmail).
                match(EmailCSVBean.class,this::dispatchEmailFromCSVFile).
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

    private void dispatchEmailFromCSVFile(EmailCSVBean csvBean) {
        dispatchEmail(new EmailDetails(new HashSet<String>(){{add(csvBean.getEmailAddress());}},
                csvBean.getSubject(),
                csvBean.getBody(),
                new HashSet<String>(){{add(csvBean.getCcAddress());}},
                new HashSet<String>(){{add(csvBean.getBccAddress());}}));
    }

    public static Props  props(EmailUtil emailUtil, ActorRef persistStatisticsActor){
        return Props.create(EmailDispatcher.class, () -> new EmailDispatcher(emailUtil, persistStatisticsActor));
    }

    private EmailDispatcher(EmailUtil emailUtil, ActorRef persistStatisticsActor) {
        this.emailUtil = emailUtil;
        this.persistStatisticsActor = persistStatisticsActor;
        this.cancellable =  getContext().getSystem().scheduler().schedule(Duration.create(30, TimeUnit.MINUTES),
                Duration.create(10, TimeUnit.MINUTES),persistStatisticsActor ,"notifyadmin",
                getContext().dispatcher(), null);
    }

    private void dispatchEmail(EmailDetails emailDetails) {
        System.out.println("Received email details to send"+emailDetails);
        emailUtil.sendEmail(emailDetails);
        persistStatisticsActor.tell(new Cmd(new Date(),1L),ActorRef.noSender());
    }
    @Override
    public void aroundPostStop() {
        cancellable.cancel();
        super.aroundPostStop();
    }
}
