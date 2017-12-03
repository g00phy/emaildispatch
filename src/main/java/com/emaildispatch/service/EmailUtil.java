package com.emaildispatch.service;

import com.emaildispatch.message.EmailDetails;
import com.emaildispatch.model.EmailCSVBean;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class EmailUtil {
    private final Session session;

    public EmailUtil(Session session) {
        this.session = session;
    }

    /**
     * Utility method to send simple HTML email
     */
    public void sendEmail(EmailDetails emailDetails){
        try
        {

            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@roguemail.com", "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse("no_reply@roguemail.com", false));

            msg.setSubject(emailDetails.getSubject(), "UTF-8");

            msg.setText(emailDetails.getBody(), "UTF-8");

            msg.setSentDate(new Date());
            String toEmail = emailDetails.getEmailAddress().stream().collect(joining(","));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            if(emailDetails.getCcAddress().size()> 0){
                String ccEmail = emailDetails.getCcAddress().stream().collect(joining(","));
                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
            }
            if(emailDetails.getBccAddress().size()> 0){
                String bccEmail = emailDetails.getBccAddress().stream().collect(joining(","));
                msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccEmail, false));
            }

            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<EmailCSVBean> parseCSVWithHeader(String fileName) throws IOException {
        List<EmailCSVBean> emails;
        try (Reader reader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName))) {
                HeaderColumnNameMappingStrategy<EmailCSVBean> beanStrategy = new HeaderColumnNameMappingStrategy<>();
                beanStrategy.setType(EmailCSVBean.class);
                CsvToBean<EmailCSVBean> csvToBean =
                        new CsvToBeanBuilder(reader).
                                withMappingStrategy(beanStrategy).
                                withSeparator(',').build();
                emails = csvToBean.parse();
                System.out.println(emails);
        }
        return emails;
    }
}