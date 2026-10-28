
# Stage 1: Build Spring Boot application
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Gradle Wrapper and build configuration
COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle

# Copy application source code
COPY src ./src

# Build application and execute automated tests
RUN chmod +x ./gradlew && \
    ./gradlew --no-daemon clean test bootJar --console=plain


# Stage 2: Run Spring Boot application
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

LABEL org.opencontainers.image.title="Daniyar Namet Student API"
LABEL org.opencontainers.image.description="DevOps Midterm - IT2-2312 SE - ID 37056"
LABEL org.opencontainers.image.authors="Daniyar Namet"

# Application port
ENV SERVER_PORT=8080
EXPOSE 8080

# Copy only the executable JAR from the build stage
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Start Spring Boot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
