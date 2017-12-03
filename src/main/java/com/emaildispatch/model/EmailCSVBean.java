package com.emaildispatch.model;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;

/**
 * Created by Sapient
 */
public class EmailCSVBean implements Serializable{

    private static final long serialVersionUID = -3463950346558013212L;
    @CsvBindByName
    private final String emailAddress;
    @CsvBindByName
    private final String subject;
    @CsvBindByName
    private final String body;
    @CsvBindByName
    private final String ccAddress;
    @CsvBindByName
    private final String bccAddress;

    public EmailCSVBean() {
        emailAddress = null;
        subject = null;
        body = null;
        ccAddress = null;
        bccAddress = null;
    }

    public EmailCSVBean(String emailAddress, String subject, String body, String ccAddress, String bccAddress) {
        this.emailAddress = emailAddress;
        this.subject = subject;
        this.body = body;
        this.ccAddress = ccAddress;
        this.bccAddress = bccAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public String getBccAddress() {
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
