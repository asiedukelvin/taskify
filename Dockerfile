# Stage 1: Build the app with Gradle and JDK 24
FROM gradle:8.14.3-jdk24 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Stage 2: Run the app with JDK 24
FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
