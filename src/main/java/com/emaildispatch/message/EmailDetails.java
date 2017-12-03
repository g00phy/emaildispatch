package com.emaildispatch.message;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Sapient
 */
public class EmailDetails implements Serializable{

    private static final long serialVersionUID = -3463950346558013212L;
    private final Set<String> emailAddress;
    private final String subject;
    private final String body;
    private final Set<String> ccAddress;
    private final Set<String> bccAddress;

    public EmailDetails() {
        emailAddress = null;
        subject = null;
        body = null;
        ccAddress = null;
        bccAddress = null;
    }

    public EmailDetails(Set<String> emailAddress, String subject, String body, Set<String> ccAddress, Set<String> bccAddress) {
        this.emailAddress = emailAddress;
        this.subject = subject;
        this.body = body;
        this.ccAddress = ccAddress;
        this.bccAddress = bccAddress;
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

    public Set<String> getCcAddress() {
        return ccAddress;
    }

    public Set<String> getBccAddress() {
        return bccAddress;
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmailCSVBean{");
        sb.append("emailAddress=").append(emailAddress);
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", body='").append(body).append('\'');
        sb.append(", ccAddress=").append(ccAddress);
        sb.append(", bccAddress=").append(bccAddress);
        sb.append('}');
        return sb.toString();
    }
}
