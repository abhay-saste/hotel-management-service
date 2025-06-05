# Start from a JDK 17 base image
FROM openjdk:17-jdk-slim

# Create app directory
WORKDIR /app

# Copy jar file
COPY target/hotel-management-service-*.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
