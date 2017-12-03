package com.emaildispatch.message;

import java.util.Set;


public class NotifyEvent {
    private static final long serialVersionUID = -3463950346558013212L;
    private final Set<String> emailAddress;
    private final String subject;
    private final String body;

    public NotifyEvent() {
        emailAddress = null;
        subject = null;
        body = null;
    }

    public NotifyEvent(Set<String> emailAddress, String subject, String body) {
        this.emailAddress = emailAddress;
        this.subject = subject;
        this.body = body;
    }

    public Set<String> getEmailAddress() {
        return emailAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }


    @Override
    public String toString() {
        String sb = "EmailCSVBean{" + "emailAddress=" + emailAddress +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
        return sb;
    }
}
