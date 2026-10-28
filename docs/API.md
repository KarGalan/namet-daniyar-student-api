# Student Management REST API

## Student Information

- Name: Daniyar Namet
- Group: IT2-2312 SE
- Student ID: 37056

## Project Description

This project is a Spring Boot REST API for managing student records.

Technologies:
- Java 21
- Spring Boot
- Gradle
- Spring Data JPA
- PostgreSQL
- JUnit 5
- Mockito

## Student Model

Each student has the following fields:

| Field | Type | Description |
|-------|------|---------------|
| id | Long | Database-generated identifier |
| fullName | String | Student full name |
| groupName | String | Student group |
| studentId | String | University student ID |

## REST Endpoints

Base URL: /api/students

### Create Student

POST /api/students

Example request:

{
  "fullName": "Daniyar Namet",
  "groupName": "IT2-2312 SE",
  "studentId": "37056"
}

Expected response: HTTP 201 Created.

### Get All Students

GET /api/students

Returns a list of student records.

### Count Students

GET /api/students/count

Returns the total number of students stored in PostgreSQL.
Expected response: HTTP 200 OK with a numeric count.

### Get Student by ID

GET /api/students/1

Returns a single student or HTTP 404 if not found.

### Update Student

PUT /api/students/1

Example request:

{
  "fullName": "Daniyar Namet",
  "groupName": "IT2-2312 SE",
  "studentId": "37056"
}

Updates the existing student record.

### Delete Student

DELETE /api/students/1

Expected response: HTTP 204 No Content.

## Validation

The API validates required student fields using Jakarta Validation.

## Automated Testing

Run the automated tests:

    ./gradlew clean test

Build the executable Spring Boot JAR:

    ./gradlew bootJar

Tests use JUnit 5 and Mockito.

## Database

The application uses PostgreSQL through Spring Data JPA.

Database integration and Docker Compose configuration
will be completed during the Docker part of the project.

## Git Workflow

The project uses separate feature branches:

- feature/namet-daniyar-crud
- feature/namet-daniyar-documentation

The feature branches will be merged into main
after their changes are verified.
