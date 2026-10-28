# Jenkins CI Automation

## Student Information

- Name: Daniyar Namet
- Group: IT2-2312 SE
- Student ID: 37056

## Project

Repository: namet-daniyar-student-api

Application: Spring Boot Student CRUD REST API

Technologies:
- Java 21
- Gradle Wrapper
- PostgreSQL
- Docker
- Docker Compose
- GitHub
- Jenkins

## Jenkins Configuration

Jenkins URL: http://localhost:8091/

Main Job:
Namet_Daniyar_Student_API

Source Control:
GitHub repository, main branch

Pipeline Definition:
Pipeline script from SCM

Script Path:
Jenkinsfile

## Automatic Build Trigger

Trigger: Poll SCM

Schedule:
H/30 * * * *

Jenkins periodically checks GitHub for new commits
in the main branch.

When a new commit is detected, Jenkins automatically
starts the Pipeline.

Manual Build Now is not required.

## Pipeline Stages

1. Checkout
   Retrieve the latest source code from GitHub.

2. Build
   Compile Java and package the Spring Boot application.

3. Test
   Execute automated tests using Gradle and JUnit.

4. Docker Build
   Build the personalized Docker image.

5. Verify
   Verify the Docker image and Java runtime.

## Docker Image

Image name:
namet-daniyar-student-api

CI image tag:
ci-37056

The CI image is separate from the running application
image v1.1-37056.

## CI and CD

Continuous Integration (CI):
Automatically integrates and verifies source code changes
through builds and automated tests.

Continuous Delivery:
Keeps the application in a tested, releasable state.

Continuous Deployment:
Automatically deploys verified application changes
without requiring manual approval.

This Midterm implements Continuous Integration.

Automatic deployment is not configured.

## Security

Do not commit:
- .env files
- Database passwords
- GitHub access tokens
- Jenkins credentials
- Private keys

Secrets must be managed outside the Git repository.

## Verification

A new commit to the main branch should trigger Jenkins
automatically through Poll SCM.

Expected result:
Finished: SUCCESS

Student: Daniyar Namet
Group: IT2-2312 SE
Student ID: 37056
