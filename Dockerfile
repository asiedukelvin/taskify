# Use OpenJDK base image
FROM eclipse-temurin:24-jdk

# Set working directory
WORKDIR /app

# Copy built jar file into container
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port (optional, for local dev)
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
