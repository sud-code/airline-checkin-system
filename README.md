# Relog Airline Check-in System

A web-based airline check-in system that allows passengers to check in for their flights, select seats, and receive boarding passes.

## Features

- PNR-based passenger authentication
- Interactive seat selection
- Boarding pass generation
- Responsive web interface

## Technologies Used

- **Backend**: Java with Spring Boot
- **Database**: H2 in-memory database
- **Frontend**: Thymeleaf, Bootstrap, JavaScript
- **Build Tool**: Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Running the Application

1. Clone the repository:
   ```
   git clone https://github.com/sud-code/airline-checkin-system.git
   cd airline-checkin-system
   ```

2. Build and run the application:
   ```
   mvn spring-boot:run
   ```

3. Access the application in your web browser:
   ```
   http://localhost:8083
   ```

### Sample PNR

Use the following PNR for testing:
- PNR: ABC123

## Project Structure

```
airline_checkin_system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/relog/checkin/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── exception/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── CheckinApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       ├── application.properties
│   │       └── schema.sql
│   └── test/
│       └── java/
│           └── com/relog/checkin/
├── pom.xml
└── README.md
```

## Recent Updates

- Fixed Thymeleaf template issues by converting from layout fragments to standalone templates
- Resolved JAXB API dependency issues for Java 24 compatibility
- Changed server port to 8083 to avoid conflicts

## License

This project is licensed under the MIT License - see the LICENSE file for details.