package com.emaildispatch.message;

import java.util.Set;

/**
 * Created by Sapient
 */
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
        final StringBuilder sb = new StringBuilder("EmailCSVBean{");
        sb.append("emailAddress=").append(emailAddress);
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", body='").append(body).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
