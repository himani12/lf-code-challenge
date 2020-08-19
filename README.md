# Labforward Code Challenge for Backend Engineer Candidate

## Task
You need to add a new endpoint to the API to allow users to *update the greetings they created*.

## Solution
I have listed all the changes and enhancements below. I spent 7-8 hours analyzing and implementing the solution.

## Definition of Done
1. Implement the required update endpoint.
2. Add required test cases for each of CRUD functionality (Update and Delete) for controller and service.
3. Add delete endpoint and fetch all greetings to the application.
4. Refactor the existing code by adding some classes and interfaces and remove some line of code to make the code dry.
5. Add Swagger UI as part of UI or as a documentation.
6. RequestAcceptContentTypeInterceptor was not being used, so I have implemented the WebConfig class implementing WebMvcConfigurer so I could add Interceptor.
7. Fixed Dependency Injection problem on EntityValidator by adding Qualifier on constructor (SpringValidatorAdapter).
8. Added configuration for CORS

#### IDE -> Intellij
You can run the application directly from intellij but make sure you are using Java 8
## Introduction
	./gradlew bootRun

This will start up a Spring Boot application with Tomcat server running on 8080.

## Available end-points
There are five end-points available in this project

| Method        | Endpoint              | Body            |
| ------------- |:---------------------:| --------------- |   
| GET           | localhost:8080/hello  | no body         |
| GET           | localhost:8080/hello  | ID              |
| POST          | localhost:8080/hello  | message         |
| PATCH           | localhost:8080/hello| ID, message     |
| DELETE        | localhost:8080/hello  | ID              |

## Swagger API documentation
The available end-point is documented by swagger through this link 

- `http://localhost:8080/swagger-ui.html#/hello-controller`

Thank you for sending this coding challenge. Tried my best to provide a feasible solution.

