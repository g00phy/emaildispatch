package com.emaildispatch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.emaildispatch.actor.AdminEventNotifier;
import com.emaildispatch.actor.EmailDispatcher;
import com.emaildispatch.actor.PersistStatisticsActor;
import com.emaildispatch.message.EmailDetails;
import com.emaildispatch.model.EmailCSVBean;
import com.emaildispatch.service.EmailUtil;
import com.emaildispatch.service.EmailValidator;

import javax.mail.Session;
import java.io.IOException;
import java.util.*;

public class EmailDispatchInitiator {




    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create();
        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.host", "localhost");
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        EmailUtil emailUtil = new EmailUtil(session);

        ActorRef adminNotifier = system.actorOf(AdminEventNotifier.props(emailUtil));
        ActorRef persistStatActor = system.actorOf(PersistStatisticsActor.props(adminNotifier),"PersistStatisticsActor");
        ActorRef emailDispatcher = system.actorOf(EmailDispatcher.props(emailUtil, persistStatActor));
        Scanner input = new Scanner(System.in);
        final Set<String> emailAddress = new HashSet<>();
        String subject = null;
        String body;
        final Set<String> ccAddress = new HashSet<>();
        final Set<String> bccAddress = new HashSet<>();
        while(true) {
            System.out.println("##### Press 1 and enter to key in details and 2 read from csv file ");
            int choice = input.nextInt();
            if (choice == 1) {
                System.out.println("#####Please enter the details to compose the mail to and hit enter####");
                System.out.println("Enter at least one email address . Hit enter to key-in a new one. Hit : to finish");
                do {
                    String inputEmailAddress = input.nextLine();
                    if (EmailValidator.validate(inputEmailAddress)) {
                        emailAddress.add(inputEmailAddress);
                    } else {
                        System.out.println("Please enter a valid mail address");
                    }
                } while (!input.nextLine().equals(":"));

                System.out.println("Enter the cc email addresses. Hit enter to key-in a new one. Hit : to finish. Hit ; to skip");
                String inputCCEmailAddress = input.nextLine();
                while (!(inputCCEmailAddress.equals(":") || inputCCEmailAddress.equals(";"))) {

                    if (EmailValidator.validate(inputCCEmailAddress)) {
                        ccAddress.add(inputCCEmailAddress);
                    } else {
                        System.out.println("Please enter a valid mail address");
                    }
                    inputCCEmailAddress = input.nextLine();
                }
                System.out.println("Enter the bcc email addresses. Hit enter to key-in a new one. Hit : to finish. Hit ; to skip");
                String inputBCCEmailAddress = input.nextLine();
                while (!(inputBCCEmailAddress.equals(":") || inputBCCEmailAddress.equals(";"))) {

                    if (EmailValidator.validate(inputBCCEmailAddress)) {
                        bccAddress.add(inputBCCEmailAddress);
                    } else {
                        System.out.println("Please enter a valid mail address");
                    }
                    inputBCCEmailAddress = input.nextLine();
                }
                System.out.println("Enter the subject. Hit enter to finish");
                String inputSubject = input.nextLine();
                if (inputSubject != null && !inputSubject.isEmpty()) {
                    subject = inputSubject;
                }
                System.out.println("Enter the body of the mail. Hit enter to key-in a new line. Hit : to finish");
                StringBuilder inputBodyStr = new StringBuilder();
                do {
                    String inputBody = input.nextLine();
                    if (inputSubject != null && !inputSubject.isEmpty()) {
                        inputBodyStr.append(inputBody);
                    }
                } while (!input.nextLine().equals(":"));
                body = inputBodyStr.toString();
                EmailDetails emailDetails = new EmailDetails(emailAddress, subject, body, ccAddress, bccAddress);
                emailDispatcher.tell(emailDetails, ActorRef.noSender());
            } else {

                try {
                    List<EmailCSVBean> emailDetails = emailUtil.parseCSVWithHeader("emails.csv");
                    emailDetails.forEach(email -> emailDispatcher.tell(email, ActorRef.noSender()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
