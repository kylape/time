#Time Tracking App

My intention is for this to be used to track my time during work.  Yes I know
that there are a bazillion time tracking apps, but I feel this is a decent side
project to get exactly what I want in a time tracking app.

##High-level Architecture
- A RESTful service to manage the data
- A web application to display data, reports, and update what I'm doing
- A greasemonkey script to integrate the Salesforce case view with the time
  tracking app.

##Building

`mvn clean install`

Development is against JBoss EAP 6, so you could also do this if you have a
running instance:

`mvn clean jboss-as:deploy`

**Disclaimer:** This is not really working yet.
