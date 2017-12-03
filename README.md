# emaildispatch

emaildispatch is an application written mainly in akka toolkit to dispatch emails. This application lets a user to send a mail through command line or CSV file. For every 30 mins, statistics of the events(i.e no of emails sent till that time and timestamps at which emails were sent) done on application are sent to an admin.

Pre-requisities:
-sbt(latest version)
-any SMTP server(preferably "papercut") to send emails

Steps:
- Install the latest version of sbt and papercut server
- Clone repository and open command prompt in cloned folder
- enter "sbt clean compile run" to build the project
- When the project is running, options to enter via command line or csv will be shown. Select the necessary option.
- With the first choice(to send emails by entering details through the command line)
    a) Enter the main mail addresses. Please note that emails are validated for format.
    b) Enter the cc mail addresses if necessary.
    c) Enter the bcc mail addresses if necessary.
    d) Enter the subject
    e) Enter the body to finally send the mail.
- With the second choice(to send emails by entering details csv)
   a) Update the "emails.csv" same as above under the proper columns before starting up the project.
   b) select the csv choice

Concepts used:
- OpenCSV for parsing CSV files.
- akka persistence to persist the events(i.e no of mails sent since the start of the application)
- akka actors to dispatch mails

Caveats or roadmap
- application needs to be down to update the csv file.
- csv file can only accept single address under main, cc and bcc column
- clearing of statistics at the end of the day.

