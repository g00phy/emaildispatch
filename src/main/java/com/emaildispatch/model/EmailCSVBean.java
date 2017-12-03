package com.emaildispatch.model;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;


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
        return  "EmailCSVBean{" + "emailAddress=" + emailAddress +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", ccAddress=" + ccAddress +
                ", bccAddress=" + bccAddress +
                '}';
    }
}
