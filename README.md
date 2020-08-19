# Labforward Code Challenge for Backend Engineer Candidate

This is a simple Hello World API for recruiting purposes. You, as a candidate, should work on the challenge on your own account. Please clone the repo to your account and create a PR with your solution. 

## Introduction

You can run the application by typing

	./gradlew bootRun

This will start up a Spring Boot application with Tomcat server running on 8080.

Show all other possible tasks:

	./gradlew tasks
	
## Your Task	

You need to add a new endpoint to the API to allow users to *update the greetings they created*. 

## Definition of Done
1. Implement the required update endpoint.
2. Add required test cases for each of CRUD functionality (Update and Delete) for controller and service.
3. Add delete endpoint to the application.
4. Refactor the existing code by adding some classes and interfaces and remove some line of code to make the code dry.
5. Add Swagger UI as part of UI or as a documentation.
6. Realized that the RequestAcceptContentTypeInterceptor was not being used, so I have implemented the WebConfig class implementing WebMvcConfigurer so I could add Interceptor.
7. Fixed Dependency Injection problem on EntityValidator by adding Qualifier on constructor (SpringValidatorAdapter).
8. Added configuration for CORS

#### IDE -> Intellij
You can run the application directly from intellij but make sure you are using Java 8

## Usage & available end-points
There are five end-points available in this project

| Method        | Endpoint              | Body            |
| ------------- |:---------------------:| --------------- |   
| GET           | localhost:8080/hello  | no body         |
| GET           | localhost:8080/hello  | ID              |
| POST          | localhost:8080/hello  | message         |
| PUT           | localhost:8080/hello  | Greeting object |
| DELETE        | localhost:8080/hello  | ID              |

## Swagger API documentation
The available end-point is documented by swagger through this link 

- `http://localhost:8080/swagger-ui.html#/hello-controller`


## Acceptance Criteria

This task is purposefully open-ended. You are free to come up with your own implementation based on your assumptions. You are also welcome to improve the existing code by refactoring, cleaning up, etc. where necessary. Hint: there is a missing core piece in the application :) 

Extra points for describing a user interface which utilizes the API with the new endpoint. This can be a text document, simple mock-ups, or even an interactive HTML proof-of-concept. Be creative and show us how you approach UI problems.

We understand that not everyone has the same amount of "extra" time. It is also up to you to determine the amount of time you spend on the exercise. So that the reviewer understands how you are defining the scope of work, please clearly indicate your own “Definition of Done” for the task in a README file along with any other pertinent information.

Regardless of how far you take the solution towards completion, please assume you are writing production code. Your solution should clearly communicate your development style, abilities, and approach to problem solving. 

Let us know if you have any questions, and we look forward to seeing your approach.

Good Luck!
