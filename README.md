# Lunch Decision App - Spring Boot Backend

## Overview

The **Lunch Decision App** backend service facilitates group lunch decisions by creating sessions where users can suggest restaurants and vote for their preferred options. This backend is built using **Spring Boot** and **Spring Security** to ensure secure user authentication and session management.

## Features

- **User Management**: Register and authenticate users.
- **Session Management**: Create and join lunch decision sessions.
- **Restaurant Suggestion**: Add restaurant suggestions within a session.
- **Session Conclusion**: End a session and pick a restaurant.

## Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database** (for development)
- **Maven** (for build and dependency management)
- **JWT** (for stateless authentication)

## Prerequisites

- **Java 17** or later
- **Maven 3.6.3** or later
- **IDE** (e.g., IntelliJ, Eclipse) or **Text Editor** (e.g., VSCode)
- **Postman** (optional, for API testing)
- **Git** (optional, for version control)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/sajithgunarathna/lunch-decision-app-backend.git
cd lunch-decision-app-backend


Build and Run the Application
Navigate to the Project Directory:

bash
Copy code
cd lunch-decision-app-backend
Run the Application using Maven:

bash
Copy code
mvn spring-boot:run
Access the Application:

The application will be available at http://localhost:8080.
Running Tests
To run the tests, use:

bash
Copy code
mvn test
API Endpoints
User Management
Signup

Endpoint: POST /api/users
Request Body:
json
Copy code
{
  "name": "string",
  "email": "string",
  "password": "string"
}
Response: 201 Created, with the newly created user in the response body.
Error: 400 Bad Request if the username already exists.
Login

Endpoint: POST /login
Authentication: Basic Authentication using email and password.
Session Management
Create Session

Endpoint: POST /api/sessions
Request Body:
json
Copy code
{
  "name": "string"
}
Response: 201 Created, with the newly created session in the response body.
Join Session

Endpoint: POST /api/sessions/{sessionId}/user/{userId}
Path Variables:
sessionId: The ID of the session to join.
userId: The ID of the user joining the session.
Response: 200 OK if successful.
Restaurant Management
Add Restaurant
Endpoint: POST /api/restaurants/session/{sessionId}/user/{userId}
Path Variables:
sessionId: The ID of the session to which the restaurant is being added.
userId: The ID of the user adding the restaurant.
Request Body:
json
Copy code
{
  "name": "string"
}
Response: 201 Created, with the newly added restaurant in the response body.
End Session
End Session
Endpoint: POST /api/sessions/{sessionId}/end
Path Variables:
sessionId: The ID of the session to end.
Response: 200 OK, with the selected restaurant for the session in the response body.
Configuration
Database Configuration
The application uses an in-memory H2 database for development. Access the H2 console at http://localhost:8080/h2-console using the credentials defined in application.properties.

Default H2 Console Configuration:

JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password:
To change the database or use a different RDBMS in production, modify the application.properties file with appropriate JDBC configurations.

Security Configuration
Spring Security is configured to use Basic Authentication for securing endpoints. User-related endpoints (/api/users/**) and the H2 console (/h2-console/**) are accessible without authentication. All other endpoints require authenticated access.

java
Copy code
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
            .requestMatchers(AntPathRequestMatcher.antMatcher("/api/users/**")).permitAll()
            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults()) // Enables HTTP Basic Authentication with default settings.
        .headers(headers -> headers.frameOptions().disable()) // Disables frame options to allow H2 console display.
        .csrf(csrf -> csrf
            .ignoringRequestMatchers(
                AntPathRequestMatcher.antMatcher("/h2-console/**"),
                AntPathRequestMatcher.antMatcher("/api/users/**")
            ).disable()) // Disables CSRF protection for specified endpoints.
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Configures session management to be stateless.
    return http.build(); // Builds and returns the SecurityFilterChain.
}
Default User
A default user is created upon application startup for testing purposes. You can modify or remove this user configuration in the DataLoader.java class.

Deployment
To deploy the application, build a runnable JAR file using:

bash
Copy code
mvn clean package
The JAR file will be located in the target directory. You can run it using:

bash
Copy code
java -jar target/lunch-decision-app-backend-0.0.1-SNAPSHOT.jar
Deployment in a Cloud Environment
For cloud deployment (e.g., AWS, Azure, Heroku), follow the cloud provider's guidelines for deploying Java applications. Typically, this involves:

Provisioning a server (e.g., EC2 instance, App Service, Dyno).
Transferring the JAR file to the server.
Configuring environment variables for any production database or security settings.
Starting the application on the server.
License
This project is licensed under the MIT License. See the LICENSE file for more details.

Contact
For any questions, support, or contributions, please contact:

Email: [sajith.gunarathna@gmail.com]
GitHub: sajithgunarathna