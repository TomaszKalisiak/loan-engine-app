# loan-engine-app
loan-egine-app

## Requirements

For building and running this application you need:
- [JDK 18](https://jdk.java.net/18/)
- [Gradle](https://gradle.org)

# Swagger
- [swagger-ui](http://localhost:8080/swagger-ui/)

## Build
` ./gradlew clean build`

## Run
` ./gradlew bootRun`

## H2 Console
` localhost:8080/h2-console`

## Postman
 Postman collection attached in the project

## Exposure
- post - /loan - creates a new loan. Returns created loan data.
- patch -/loan/{id} - updates the loan by loan id
- get - /loan/{id} - gets loan details by  loan id


Many things could be improved so please keep in mind that this is just to show some basic ideas.
With the current implementation user can create a loan in H2 database and fetch a contract dto object that represents the loan in the database.
Loans can be fetched and due date extension can be performed on the contract.
As far as logic for the principal I have created a simplest service to calculate the final principal to be 10% of the requested amount.

There is an issue with simple http client and patch method - it's not allowed therefore had to configure custom Apache Http Client to enable PATCH method for rest template.

Sample postman collection file for testing is attached to the project.

All the simple classes with static methods could in the future be turned into Components or Services.

Decided not to interface the services as the application is not in need to multiple implementations of the same interface.