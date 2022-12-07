# API Testing Framework 

API Test Automation framework built using Java,Junit,Restssured tech for adding booking functional and regression tests.

# Minimum System Requirements

Java [jdk 1.8],
Maven

# Environmental variables

 1.cookie - secret for accessing the endpoints
 e.g. In Mac OS export cookie="token=<secret>"

# Running tests

Clone this repository and execute the commands `1. mvn clean install` and `2.mvn test` from the project root

To run specific tests, please use tags , e.g. ` mvn test -Dcucumber.filter.tags="@CreateBooking" `