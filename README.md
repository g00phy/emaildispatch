# emaildispatch

emaildispatch is application written mainly in akka toolkit to dispatch emails. This application lets a user to send a mail through command line or CSV file. For every 30 mins, statistics of the events(i.e no of emails sent till that time and timestamps at which emails were sent) done on application are sent to an admin.

Pre-requisities:
-sbt(latest version)
-any SMTP server(preferably "papercut") to send emails

Steps:
-Install the latest version of sbt and papercut server
-Clone repository and open command prompt in cloned 